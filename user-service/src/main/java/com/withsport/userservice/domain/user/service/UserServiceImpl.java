package com.withsport.userservice.domain.user.service;

import com.withsport.userservice.domain.user.dto.UserDto;
import com.withsport.userservice.domain.user.dto.UserRecordResponse;
import com.withsport.userservice.domain.user.entity.User;
import com.withsport.userservice.domain.user.exception.DuplicateUserNicknameException;
import com.withsport.userservice.domain.user.exception.NotExistUserException;
import com.withsport.userservice.domain.user.repository.UserRepository;
import com.withsport.userservice.global.client.record.AverageUserRecordResponse;
import com.withsport.userservice.global.client.record.RecordClient;
import com.withsport.userservice.global.client.record.TeamRecordResponse;
import com.withsport.userservice.global.client.team.GetTeamIdsResponse;
import com.withsport.userservice.global.client.team.TeamClient;
import com.withsport.userservice.global.dto.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class UserServiceImpl implements UserService,UserDetailsService {

    private final UserRepository userRepository;
    private final RecordClient recordClient;
    private final TeamClient teamClient;
    private final S3Uploader s3Uploader;
    private final UserProducer userProducer;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 사용자 입니다."));

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getDtype()));

        return new org.springframework.security.core.userdetails.User(user.getId().toString(), user.getPassword(), authorities);


    }

    public List<UserDto> findUserByUserIds(List<Long> userIds){
        return userRepository.findAllById(userIds)
                .stream()
                .map(UserDto::new)
                .collect(Collectors.toList());

    }

    public UserDto findUserByUserId(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotExistUserException("존재하지 않는 사용자 입니다."));

        return new UserDto(user);
    }


    //닉네임 중복 체크

    @Override
    public void checkDuplicateUserNickname(String nickname) {

        Optional<User> user = userRepository.findByNickname(nickname);
        if(user.isPresent()){
            throw new DuplicateUserNicknameException("이미 존재하는 팀 이름입니다.");
        }
    }


    @Transactional
    @Override
    public void addUserProfile(Long userId, String nickname, String address) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotExistUserException("존재하지 않는 사용자 입니다."));

        user.addUserProfile(nickname,address);
        userProducer.createUser(user);


//        userRepository.save(user);
    }

    @Transactional
    @Override
    public void updateUserProfile(Long userId, String nickname, String introduction, String area, MultipartFile image) throws Exception {

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NotExistUserException("존재하지 않는 사용자 입니다."));

            //image가 있을 경우만
            if(image != null){
//                GetProfileUrlResponse getProfileUrlResponse = teamClient.uploadProfileUrl(String.valueOf(userId), image);
                s3Uploader.upload(user.getId(), image, "static");
//                user.setProfileImage(getProfileUrlResponse.getProfileUrl());
            }
            user.updateUserProfile(nickname,introduction,area);
            userProducer.updateUser(user);



    }

    //Feign으로 조회
//    @Override
//    public UserRecordResponse findUserRecordByUserId(Long userId) {
//        return null;
//    }

    @Override
    public List<UserDto> findUsersByNickname(String nickname) {
        List<User> users = userRepository.findUserByNicknameContaining(nickname);
        return users.stream()
                .map(UserDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteUser(Long aLong) {
        User user = userRepository.findById(aLong)
                .orElseThrow(() -> new NotExistUserException("존재하지 않는 사용자 입니다."));

        userRepository.delete(user);
    }

    @Override
    public UserRecordResponse findUserRecordByUserId(Long userId) {

        Result<AverageUserRecordResponse> averageRecordByUserId = recordClient.getAverageRecordByUserId(String.valueOf(userId));
        return new UserRecordResponse(averageRecordByUserId);
//        Result<GetTeamIdsResponse> teamByUserIds = teamClient.getTeamByUserId(userId);
//        if(teamByUserIds.getData().getTeamIds().size() == 0){
//            return null;
//        }
//        List<Long> teamIds = teamByUserIds.getData().getTeamIds();
//
//        List<TeamRecordResponse> teamRecordResponses = new ArrayList<>();
//        for(Long teamId : teamIds){
//            Result<TeamRecordResponse> recordByTeamId = recordClient.getRecordByTeamId(String.valueOf(teamId));
//            teamRecordResponses.add(recordByTeamId.getData());
//            }
//        return teamRecordResponses;
    }

    @Override
    public UserDto findUserByNickname(String userNickname) {
        User user = userRepository.findByNickname(userNickname)
                .orElseThrow(() -> new NotExistUserException("존재하지 않는 사용자 입니다."));
        return new UserDto(user);
    }


}
