package com.withsport.userservice.domain.user.service;

import com.withsport.userservice.domain.user.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto findUserByUserId(Long userId);
    List<UserDto> findUserByUserIds(List<Long> userIds);
//    StoreOwnerDto findOwnerById(Long userId);
//    void saveStoreOwner(PostOwnerDto toPostOwnerDto, PostStoreDto toPostStoreDto);

    void checkDuplicateUserNickname(String teamName);

    void addUserProfile(Long userId, String nickname, String address);
}
