package com.withsports.gifticonservice.domain.category.entity;

import com.withsports.gifticonservice.domain.gifticon.entity.Gifticon;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Table(name = "category")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "categroy_id")
    private Long id;

    private String name; // 음식, 스포츠 용품, 건강관리식품 등

//    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
//    private List<Gifticon> gifticons = new ArrayList<>();

//    public void addGifticon(Gifticon gifticon){
//        this.gifticons.add(gifticon);
//        gifticon.setCategory(this);
//
//    }

    public static Category of(String name , Long order){
        Category category = new Category();
        category.name = name;
        category.id = order;
        return category;
    }





}
