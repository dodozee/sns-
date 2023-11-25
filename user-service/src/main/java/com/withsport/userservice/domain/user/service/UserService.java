package com.withsport.userservice.domain.user.service;

import com.withsport.userservice.domain.user.dto.UserDto;
import com.withsport.userservice.domain.user.dto.UserRecordResponse;
import com.withsport.userservice.global.client.record.TeamRecordResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

    UserDto findUserByUserId(Long userId);
    List<UserDto> findUserByUserIds(List<Long> userIds);
//    StoreOwnerDto findOwnerById(Long userId);
//    void saveStoreOwner(PostOwnerDto toPostOwnerDto, PostStoreDto toPostStoreDto);

    void checkDuplicateUserNickname(String teamName);

    void addUserProfile(Long userId, String nickname, String address) throws Exception;

    void updateUserProfile(Long userId, String nickname, String introduction, String area, MultipartFile image) throws Exception;

//    UserRecordResponse findUserRecordByUserId(Long userId);

    List<UserDto> findUsersByNickname(String nickname);

    void deleteUser(Long aLong);

    UserRecordResponse findUserRecordByUserId(Long aLong);

    UserDto findUserByNickname(String userNickname);
}
