package com.withsports.teamservice.domain.team.service;

import com.withsports.teamservice.domain.team.dto.*;
import com.withsports.teamservice.domain.team.dto.producer.KafkaProduceUpdateUserProfileDto;
import com.withsports.teamservice.domain.team.entity.Team;
import com.withsports.teamservice.domain.team.exception.DuplicateTeamLeaderException;
import com.withsports.teamservice.domain.team.exception.DuplicateTeamNameException;
import com.withsports.teamservice.domain.team.exception.ExistSameTeamException;
import com.withsports.teamservice.domain.team.exception.NotExistTeamException;
import com.withsports.teamservice.domain.team.repository.TeamRepository;
import com.withsports.teamservice.domain.team.web.response.CreateTeamResponse;
import com.withsports.teamservice.domain.teamuser.dto.TeamUserApplicationDto;
import com.withsports.teamservice.domain.teamuser.entity.Role;
import com.withsports.teamservice.domain.teamuser.entity.TeamUser;
import com.withsports.teamservice.domain.teamuser.entity.TeamUserStatus;
import com.withsports.teamservice.domain.teamuser.exception.NotTeamLeaderException;
import com.withsports.teamservice.domain.teamuser.repository.TeamUserRepository;
import com.withsports.teamservice.global.client.user.GetUserResponse;
import com.withsports.teamservice.global.client.user.UserClient;
import com.withsports.teamservice.global.dto.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final TeamUserRepository teamUserRepository;
    private final UserClient userClient;
    private final S3Uploader s3Uploader;
    private final TeamProducer teamProducer;


    @Override
    public void checkDuplicateTeamName(String teamName) {

        Optional<Team> team = teamRepository.findByTeamName(teamName);
        if(team.isPresent()){
            throw new DuplicateTeamNameException("이미 존재하는 팀 이름입니다.");
        }
    }


    // TODO 팀 생성
    @Transactional
    @Override
    public CreateTeamResponse createTeam(CreateTeamDto createTeamDto, MultipartFile image) throws Exception {

        Long createLeaderId = createTeamDto.getLeaderId();
        String leaderName = userClient.getNicknameUserById(createLeaderId).getData().getNickname();
        Team team = Team.of(createTeamDto.getTeamName(),
                createTeamDto.getLeaderId(),
                leaderName,
                createTeamDto.getIntroduction(),
                createTeamDto.getArea(),
                createTeamDto.getSports());


        teamRepository.save(team);

        System.out.println("팀 service에 필요한 S3Uploader 호출 직전");
        s3Uploader.upload(team.getId(), image, "static");
        System.out.println("팀 service에 필요한 S3Uploader 호출 직후");



        System.out.println("팀 service에 필요한 UserClient 호출 직전");

        Result<GetUserResponse> response = userClient.getNicknameUserById(createLeaderId);
        String nickname = response.getData().getNickname();
        System.out.println("팀 service에 필요한 UserClient 호출 직후");

        TeamUser teamUser = TeamUser.of(createLeaderId, nickname, Role.LEADER, TeamUserStatus.ACCEPTED);
        teamUser.setTeam(team);
        team.addTeamUser(teamUser);
        team.addTeamCount();

        teamUserRepository.save(teamUser);

        teamProducer.teamCreated(team.getId(), team.getTeamName(), team.getSports());

        //TODO 팀 채팅방 생성

        return CreateTeamResponse.builder()
                .teamId(team.getId())
                .build();
    }

    //TODO 팀 해산(삭제)
    @Transactional
    @Override
    public void deleteTeam(Long teamId, Long leaderId) throws Exception {
        isTeamLeader(leaderId, teamId);
//        teamRepository.delete(teamRepository.findById(teamId)
//                .orElseThrow(() -> new NotExistTeamException(teamId + "팀을 찾을 수 없습니다.")));

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new NotExistTeamException(teamId + "팀을 찾을 수 없습니다."));

        List<Long> userIds = findTeamUserIds(teamId);
        teamProducer.teamDeleted(team.getTeamName(),teamId,userIds);

        teamUserRepository.deleteByTeamId(teamId);
        teamRepository.delete(team);


    }

    @Override
    public SliceImpl<SearchTeamResult> findSearchTeamScroll(SearchTeamCondition condition, Pageable pageable) {
        return null;
    }

    @Override
    public List<SearchTeamResult> findFavoriteTeam(SearchTeamCondition condition, Long userId) {
        return null;
    }


    //TODO 팀 조회
    @Override
    public TeamDto findTeamById(Long teamId) {
        System.out.println("팀 프로필 조회 service 호출");

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new NotExistTeamException(teamId + "팀을 찾을 수 없습니다."));

        System.out.println("팀 프로필 조회 명수 호출");

        Long count = teamUserRepository.countByTeamId(teamId);

        return TeamDto.of(team, team.getImageUrl(), count);
    }


    @Override
    public List<TeamDto> findTeamAllById(Iterable<Long> teamIds) {
        return null;
    }



    //팀 생성 유효성 체크(팀장이 팀을 생성한 적이 있는지, 팀원인지)
    @Override
    public void isValidTeamLeader(Long createLeaderId,  String sports) {
        if(!isNotExistSameSportsTeamLeader(createLeaderId, sports) || !isNotExistSameSportsAndTeamUser(createLeaderId, sports)){
            throw new DuplicateTeamLeaderException("이미 같은 종목에 속해있는 사용자입니다.");
        }

    }

    private Boolean isNotExistSameSportsTeamLeader(Long createLeaderId,  String sports) {
        List<Team> findTeams = teamRepository.findByLeaderId(createLeaderId);

        //어디에도 속한 팀이 없는경우 -> 팀을 만들 수 있음
        if (findTeams.isEmpty()) {
            return true;
        }else {
            //소속된 팀장이 있으나 같은 스포츠 종목이 아닌경우 -> 팀을 만들 수 있음
            for (Team team : findTeams) {
                if (!team.getSports().equals(sports)) {
                    return true;
                }
            }
        }

        return Objects.nonNull(teamRepository.findByLeaderId(createLeaderId));
    }

    /**
     * TODO
     *  -join 해서 확인해야할거같음. 팀을 만드는 userid로 teamUser의 teamId와 Team의 Id를 조인해서 나오는 종목이 만들려는 팀의 종목이랑 겹치는지 확인
     *  -아니면 비정규화를 통해서 teamUser에 종목을 넣어서 확인
     **/
    private  Boolean isNotExistSameSportsAndTeamUser(Long createLeaderId, String sports) {
        List<TeamUser> teamUsers = teamUserRepository.findByUserId(createLeaderId);
        if (teamUsers.isEmpty()) {
            // 아무 것도 조회되지 않는 경우, 팀을 생성할 수 있음
            return true;
        } else {
            // 팀에 속해 있는 경우 만들려는 스포츠 종목과 일치하면 팀을 생성할 수 없음
            // List에 있는 객체를 꺼내어 sports와 비교
            for (TeamUser teamUser : teamUsers) {
                if (teamUser.getTeam().getSports().equals(sports)) {
                    return false;
                }
            }
            // 모든 팀을 확인한 후에도 같은 이름의 팀이 없는 경우, 팀을 생성할 수 있음
            return true;
        }

    }

    //TODO 팀장이 맞는지 확인
    @Override
    public void isTeamLeader(Long leaderId, Long teamId) {
        List<Team> teams = teamRepository.findByLeaderId(leaderId);

        for(Team team : teams){
            if(team.getId().equals(teamId)){
                return;
            }
        }
            throw new NotTeamLeaderException("팀장이 아닙니다.");



    }

    //TODO 팀원 제명
    @Transactional
    @Override
    public void deleteTeamUser(Long teamId, Long userId, Long leaderId) {
        isTeamLeader(leaderId, teamId);
        teamUserRepository.deleteTeamUserByTeamIdAndUserId(teamId, userId);
        //TODO 팀원 제명 알림

    }


    //TODO 팀 프로필 수정
    @Transactional
    @Override
    public void updateTeamProfile(UpdateTeamProfileDto profileDto, MultipartFile image) throws IOException {
        Team team = teamRepository.findById(profileDto.getTeamId())
                .orElseThrow(() -> new NotExistTeamException("해당 팀이 없습니다. id=" + profileDto.getTeamId()));

        if(image != null){
            s3Uploader.upload(team.getId(), image, "static");
        }

        team.updateTeamProfile(profileDto.getTeamName(), profileDto.getIntroduction(), profileDto.getArea(), profileDto.getSports());

    }


    @Transactional
    @Override
    public void acceptTeamUser(Long teamId, Long userId, Long leaderId) {
        isTeamLeader(leaderId, teamId);

        TeamUser teamUser = teamUserRepository.findTeamPlacedUserByTeamIdAndUserId(teamId, userId);

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new NotExistTeamException("해당 팀이 없습니다. id=" + teamId));
        teamUser.setTeam(team);
        team.addTeamUser(teamUser);
        teamUser.accept();
        team.addTeamCount();


        teamUserRepository.save(teamUser);

    }

    @Transactional
    @Override
    public void rejectTeamUser(Long teamId, Long userId, Long leaderId) {
        isTeamLeader(leaderId, teamId);
        TeamUser teamUser = teamUserRepository.findTeamUserByTeamIdAndUserId(teamId, userId);
        teamUser.reject();
        //알림 보내고 영구 삭제
//        deleteTeamUser(teamId, userId, leaderId);
    }

    @Override
    public List<TeamUserApplicationDto> getTeamUserApplication(Long teamId, Long leaderId) {
       isTeamLeader(leaderId, teamId);
        return teamUserRepository.findApplicationUserByTeamId(teamId)
                .stream()
                .map(TeamUserApplicationDto::new)
                .collect(Collectors.toList());

    }

    @Override
    public List<TeamsDto> findTeamsByUserId(Long userId) {
        System.out.println("팀 목록 조회 service 호출");
        return teamUserRepository.findByUserId(userId)
                .stream()
                .map(TeamsDto::new)
                .collect(Collectors.toList());

    }

    @Override
    public List<TeamDto> findTeamByName(String teamName) {
        List<Team> users = teamRepository.findUserByTeamNameContaining(teamName);
        return users.stream()
                .map(TeamDto::new)
                .collect(Collectors.toList());

    }

    @Transactional
    @Override
    public void updateUserTeam(KafkaProduceUpdateUserProfileDto kafkaProduceUpdateUserProfileDto) {
        List<TeamUser> teamUser = teamUserRepository.findTeamByUserId(kafkaProduceUpdateUserProfileDto.getUserId());
        if(!teamUser.isEmpty()){
            for(TeamUser teamUser1 : teamUser){
                teamUser1.setNickname(kafkaProduceUpdateUserProfileDto.getNickname());
            }
        }

        List<Team> teams = teamRepository.findTeamsByLeaderId(kafkaProduceUpdateUserProfileDto.getUserId());
        if(!teams.isEmpty()){
            for(Team team : teams){
                team.changeLeaderName(kafkaProduceUpdateUserProfileDto.getNickname());
            }
        }
    }

    @Override
    public Long findTeamByLeaderIdAndSportsId(Long leaderId, String sports) {
       Long teamId = teamRepository.findTeamIdByLeaderIdAndSports(leaderId, sports);
        return teamId;
    }

//    @Override
//    public List<FindTeamByLeaderId> getTeamByLeaderId(Long leaderId) {
//
//        return teamRepository.findByLeaderId(leaderId)
//                .stream()
//                .map(FindTeamByLeaderId::new)
//                .collect(Collectors.toList());
//    }

    private List<Team> findTeamLeader(Long leaderId) {
        return teamRepository.findByLeaderId(leaderId);
    }


    private void validateDuplicateSameTeam(Long teamId, Long userId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new NotExistTeamException("해당 팀이 없습니다. id=" + teamId));
        List<TeamUser> teamUsers = teamUserRepository.findByUserId(userId);
        //하나씩 꺼내서 동일한 팀이 있는지 비교
        for(TeamUser teamUser : teamUsers){
            if(teamRepository.findById(teamUser.getTeam().getId()).equals(team)){
                throw new ExistSameTeamException("이미 해당 팀에 속해있습니다.");
            }
        }

    }

    private void validateDuplicateSportsAndUser(Long teamId, Long userId){
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new NotExistTeamException("해당 팀이 없습니다. id=" + teamId));

        String sports = team.getSports();

        List<TeamUser> teamUsers = teamUserRepository.findByUserId(userId);

        //하나씩 꺼내서 동일한 스포츠 종목이 있는지 비교
        for(TeamUser teamUser : teamUsers){
            if(teamRepository.findById(teamUser.getTeam().getId()).equals(sports)){
                throw new ExistSameTeamException("이미 해당 종목의 팀에 속해있습니다.");
            }
        }
    }
    //팀원의 UserId 전부 조회
    private List<Long> findTeamUserIds(Long teamId){
        return teamUserRepository.findUserIdsByTeamId(teamId);
    }

}
