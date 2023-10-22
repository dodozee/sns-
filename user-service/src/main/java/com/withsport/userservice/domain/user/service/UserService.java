package com.withsport.userservice.domain.user.service;

import com.withsport.userservice.domain.user.dto.UserDto;
import com.withsport.userservice.domain.user.entity.User;
import com.withsport.userservice.domain.user.exception.NotExistUserException;
import com.withsport.userservice.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found in the database"));

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
    public boolean checkUserNickname(String nickname){
        if(Objects.isNull(userRepository.findByNickname(nickname))){
            return true;
        }
            return false;

    }
}
