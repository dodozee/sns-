package com.withsport.userservice.global.entity;

import jakarta.persistence.MappedSuperclass;

import java.time.LocalDateTime;

@MappedSuperclass
public class BaseEntity {

    private Long createdBy;
    private LocalDateTime createdAt;
    private Long lastModifiedBy;
    private LocalDateTime lastModifiedAt;
}
