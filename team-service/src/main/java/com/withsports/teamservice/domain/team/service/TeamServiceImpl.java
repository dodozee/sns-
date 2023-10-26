package com.withsports.teamservice.domain.team.service;

import com.withsports.teamservice.domain.team.dto.CreateTeamDto;
import com.withsports.teamservice.domain.team.dto.SearchTeamCondition;
import com.withsports.teamservice.domain.team.dto.SearchTeamResult;
import com.withsports.teamservice.domain.team.dto.TeamDto;
import com.withsports.teamservice.domain.team.entity.Team;
import com.withsports.teamservice.domain.team.exception.DuplicateTeamException;
import com.withsports.teamservice.domain.team.exception.DuplicateTeamNameException;
import com.withsports.teamservice.domain.team.exception.NotExistTeamException;
import com.withsports.teamservice.domain.team.repository.TeamRepository;
import com.withsports.teamservice.domain.teammember.entity.TeamMember;
import com.withsports.teamservice.domain.teammember.repository.TeamMemberRepository;
import com.withsports.teamservice.global.entity.Sports;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;


    @Override
    public void checkDuplicateTeamName(String teamName) {

        Optional<Team> team = teamRepository.findByTeamName(teamName);
        if(team.isPresent()){
            throw new DuplicateTeamNameException("이미 존재하는 팀 이름입니다.");
        }
    }

    @Transactional
    @Override
    public void createTeam(CreateTeamDto createTeamDto) {

        Long createLeaderId = createTeamDto.getLeaderId();
        String createSports = createTeamDto.getSports();

        Team team = Team.of(createTeamDto.getTeamName(),
                createTeamDto.getLeaderId(),
                createTeamDto.getIntroduction(),
                createTeamDto.getImageUrl(),
                createTeamDto.getLocation(),
                createTeamDto.getSports());

        if(isValidTeamLeader(createLeaderId, createSports)){
            teamRepository.save(team);

        }else {
            throw new DuplicateTeamException("이미 팀을 생성한 리더입니다.");
        }
    }

    @Override
    public TeamDto deleteTeam(Long teamId) {
        teamRepository.delete(teamRepository.findById(teamId)
                .orElseThrow(() -> new NotExistTeamException(teamId + "팀을 찾을 수 없습니다.")));

        return null;
    }

    @Override
    public SliceImpl<SearchTeamResult> findSearchTeamScroll(SearchTeamCondition condition, Pageable pageable) {
        return null;
    }

    @Override
    public List<SearchTeamResult> findFavoriteTeam(SearchTeamCondition condition, Long userId) {
        return null;
    }

    @Override
    public TeamDto findTeamById(Long teamId) {

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new NotExistTeamException(teamId + "팀을 찾을 수 없습니다."));
        return TeamDto.of(team, teamMemberRepository.countByTeamId(teamId));
    }

//    @Override
//    public TeamByUserIdDto findTeamByUserId(Long userId) {
//        return null;
//    }

    @Override
    public List<TeamDto> findTeamAllById(Iterable<Long> teamIds) {
        return null;
    }



    //팀 생성 유효성 체크(팀장이 팀을 생성한 적이 있는지, 팀원인지)
    @Override
    public Boolean isValidTeamLeader(Long createLeaderId,  String sports) {
        return (isNotExistSameSportsTeamLeader(createLeaderId, sports) && isNotExistSameSportsAndTeamMember(createLeaderId, sports));

    }

    private Boolean isNotExistSameSportsTeamLeader(Long createLeaderId,  String sports) {
        List<Team> findTeams = teamRepository.findAllByLeaderId(createLeaderId);

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
     *  -join 해서 확인해야할거같음. 팀을 만드는 userid로 teamMember의 teamId와 Team의 Id를 조인해서 나오는 종목이 만들려는 팀의 종목이랑 겹치는지 확인
     *  -아니면 비정규화를 통해서 teamMember에 종목을 넣어서 확인 (이거로 선택)
     **/
    private  Boolean isNotExistSameSportsAndTeamMember(Long createLeaderId, String sports) {
        List<TeamMember> findTeamMembers = teamMemberRepository.findAllByUserId(createLeaderId);
        if (findTeamMembers.isEmpty()) {
            // 아무 것도 조회되지 않는 경우, 팀을 생성할 수 있음
            return true;
        } else {
            // 팀에 속해 있는 경우 만들려는 스포츠 종목과 일치하면 팀을 생성할 수 없음
            // List에 있는 객체를 꺼내어 sports와 비교
            for (TeamMember teamMember : findTeamMembers) {
                if (teamMember.getTeam().getSports().equals(sports)) {
                    // sports와 같은 팀이 이미 존재하는 경우, 팀을 생성할 수 없음
                    return false;
                }
            }
            // 모든 팀을 확인한 후에도 같은 이름의 팀이 없는 경우, 팀을 생성할 수 있음
            return true;
        }

    }



//    private void isExistTeamLeader(Long createLeaderId) {
//        teamRepository.findByLeaderId(createLeaderId)
//                .ifPresent(leader -> {
//                    throw new DuplicateTeamLeaderException("이미 팀을 생성한 리더입니다.");
//                });
//    }
}
