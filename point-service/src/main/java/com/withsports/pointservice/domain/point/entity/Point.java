package com.withsports.pointservice.domain.point.entity;

import com.withsports.pointservice.domain.point.exception.NotEnoughBalanceException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "point")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Point {

    @Id @GeneratedValue
    @Column(name = "point_id")
    private Long id;

    private Long userId;

    private String nickname;

    //0포인트 아래로 떨어질 수 없음

    private Long balance;

    @OneToMany(mappedBy = "point", cascade = CascadeType.ALL)
    private List<PointTransaction> pointTransactions = new ArrayList<>();

    public void addPointTransaction(PointTransaction pointTransaction){
        this.pointTransactions.add(pointTransaction);
        pointTransaction.setPoint(this);
    }

    public Point(Long userId, Long balance) {
        this.userId = userId;
        this.balance = balance;
    }

    public static Point init(Long userId, String nickname, Long balance) {
        Point point = new Point();
        point.userId = userId;
        point.nickname = nickname;
        point.balance = balance;
        point.addPointTransaction(PointTransaction.init(userId, 0, "첫 가입 1000 포인트", balance, balance));
        return point;
    }


    public void addBalance(Long amount) {
        this.balance += amount;

    }


    //TODO SAGA 패턴을 위한 메소드 만들어야함
    public void subtractBalance(Long amount) {
        if(this.balance - amount < 0) throw new NotEnoughBalanceException("포인트가 0원 이하 될수 없습니다." );
        this.balance -= amount;
    }

    public void increasePoint(Long amount, String description) {
        addBalance(amount);
        this.addPointTransaction(PointTransaction.of(userId, 0, description, amount, this.balance));
    }

    public void decreasePoint(Long amount, String description) {
        System.out.println("==============Point.decreasePoint==================");
        subtractBalance(amount);
        this.addPointTransaction(PointTransaction.of(userId, 1, description, amount, this.balance));
    }

    //TODO : 레이팅에 쓰이면 될 로직
    public void decreasePointByLoseGame(Long amount, String description) {
        if(this.balance - amount < 0) {
            this.balance = 0L;
            this.addPointTransaction(PointTransaction.of(userId, 1, description, amount, this.balance));
        }
        else {
            subtractBalance(amount);
        }
    }


    public void updateUserProfile(String nickname) {
        this.nickname = nickname;
    }
}
