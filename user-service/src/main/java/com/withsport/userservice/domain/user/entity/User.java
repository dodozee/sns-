package com.withsport.userservice.domain.user.entity;

import com.withsport.userservice.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String email;
    private String password;
    private String name;
    private String nickname;
    private String Address;

    @Column(insertable = false, updatable = false)
    protected String dtype;
    @Enumerated(EnumType.STRING)
    private AuthType oauthType;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public User(String email, String password, String name, String nickname, String Address, AuthType oauthType) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.Address = Address;
        this.dtype = User.class.getSimpleName();
        this.oauthType = oauthType;
    }
}
