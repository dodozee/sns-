package com.withsports.matchingservice.domain.matching.redis;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash("matching")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Matching {

    @Id
    private String matchingRoomId;
    private String sports;
    private Long sumRating;
    private Long capacity;
    private Long teamId;

    public static Matching of(String sports, String matchingRoomId, Long sumRating, Long capacity, Long teamId) {
        Matching matching = new Matching();
        matching.matchingRoomId = matchingRoomId;
        matching.sports = sports;
        matching.sumRating = sumRating;
        matching.capacity = capacity;
        matching.teamId = teamId;

        return matching;
    }

}
