package com.withsport.userservice.domain.user.entity;

import com.withsport.userservice.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String email;
    private String password;
    private String name;
    private String nickname;
    private String area;
    private String profileImage;
    private String introduction;

    @Column(insertable = false, updatable = false)
    protected String dtype;
    @Enumerated(EnumType.STRING)
    private AuthType oauthType;


    public User(String email, String password, String name, String nickname, String area, AuthType oauthType) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.area = area;
        this.dtype = User.class.getSimpleName();
        this.oauthType = oauthType;
    }

    public void addUserProfile(String nickname, String address) {
        this.nickname = nickname;
        this.area = address;
        this.profileImage = "https://with-sports-s3.s3.ap-northeast-2.amazonaws.com/static/bae53540-5cd1-4457-a42d-03449ca7a0a0basic.jpeg";
        this.introduction = "안녕하세요^ㅇ^! 새로운 가천인 입니다.^^";
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void updateUserProfile(String nickname, String introduction, String area) {
        this.nickname = nickname;
        this.area = area;
        this.introduction = introduction;

    }
}
