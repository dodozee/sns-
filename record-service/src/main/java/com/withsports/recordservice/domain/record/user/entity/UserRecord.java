package com.withsports.recordservice.domain.record.user.entity;

import com.withsports.recordservice.domain.record.matching.entity.MatchingRecord;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "user_record")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRecord {

    @Id @GeneratedValue
    @Column(name = "user_record_id")
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    private String nickname;
    private Long win;
    private Long draw;
    private Long lose;
    private Long rating;
    private String sports;

    @Enumerated(EnumType.STRING)
    private Tier tier;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matching_record_id")
    private MatchingRecord matchingRecord;


    public void setMatchingRecord(MatchingRecord matchingRecord) {
        this.matchingRecord = matchingRecord;
    }

    @Builder
    public static UserRecord of(Long userId, String nickname, String sports , Long win, Long draw, Long lose, Long rating, Tier tier) {
        UserRecord userRecord = new UserRecord();
        userRecord.userId = userId;
        userRecord.nickname = nickname;
        userRecord.sports = sports;
        userRecord.win = win;
        userRecord.draw = draw;
        userRecord.lose = lose;
        userRecord.rating = rating;
        userRecord.tier = staticChangeTier(rating);
        return userRecord;
    }

    public void win() {
        this.win++;
        this.rating += 100;
        changeTier(this.rating);
    }
    public void lose() {
        this.lose++;
        this.rating -= 100;
        changeTier(this.rating);
    }

    public void draw() {
        this.draw++;
        changeTier(this.rating);
    }


    public void changeTier(Long rating) {
        if(rating >= 3000){
            this.tier = Tier.신;
        } else if(rating >= 2600) {
            this.tier = Tier.프로;
        } else if(rating >= 2200) {
            this.tier = Tier.준프로;
        } else if(rating >= 2000) {
            this.tier = Tier.전국대표;
        } else if(rating >= 1600) {
            this.tier = Tier.지역대표;
        } else if(rating >= 1400) {
            this.tier = Tier.동네대표;
        } else if(rating >= 1200) {
            this.tier = Tier.아마추어;
        } else if(rating >= 1000) {
            this.tier = Tier.초보자;
        } else {
            this.tier = Tier.왕초보자;
        }

    }

    public static Tier staticChangeTier(Long rating) {
        if(rating >= 3000){
            return Tier.신;
        } else if(rating >= 2600) {
            return Tier.프로;
        } else if(rating >= 2200) {
            return Tier.준프로;
        } else if(rating >= 2000) {
            return Tier.전국대표;
        } else if(rating >= 1600) {
            return Tier.지역대표;
        } else if(rating >= 1400) {
            return Tier.동네대표;
        } else if(rating >= 1200) {
            return Tier.아마추어;
        } else if(rating >= 1000) {
            return Tier.초보자;
        } else {
            return Tier.왕초보자;
        }

    }


    public void updateUserProfile(String nickname) {
        this.nickname = nickname;
    }
}
