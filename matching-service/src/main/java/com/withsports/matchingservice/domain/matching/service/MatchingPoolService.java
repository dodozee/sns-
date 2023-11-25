package com.withsports.matchingservice.domain.matching.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.withsports.matchingservice.domain.matching.dto.MatchingDto;
import com.withsports.matchingservice.domain.matching.redis.Matching;
import com.withsports.matchingservice.domain.matching.repository.MatchingRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MatchingPoolService {
    private final MatchingRedisRepository matchingRedisRepository;
    private final MatchingProducer matchingProducer;
    private final RedisTemplate<String, Object> redisTemplate;
    /**TODO Kafka를 통해 매칭풀에 들어온 팀의 레이팅을 레디스에 저장(동기화)->완료
     * 레디스에 저장된 레이팅을 내림차순으로 불러오기
     * 맨 위의 레이팅을 가진 팀을 꺼내 순서대로 +- 100점 범위로 매칭시키기
     * 매칭이 안되면 매칭풀에 다시 넣기
     * 매칭된 팀은 매칭풀에서 삭제 -> 매칭 성공 로직 실행 (알림, 채팅 생성, 매칭룸 생성 등)
     * 스포츠 별로 매칭해야함
     * 승리시 몇 점 오르고, 패배시 몇 점 내려가는지 계산해야함
     */
//
//    public void addTeam(String teamId, String teamName, double teamRating) {
//        stringRedisTemplate.opsForZSet().add(teamId, teamName, teamRating);
//    }
//
//    public Set<String> getTeams(String teamId) {
//        return stringRedisTemplate.opsForZSet().range(teamId, 0, -1);

//    @Autowired
//    private TeamRepository teamRepository;
//
//    public void addTeam(TeamDto team) {
//        teamRepository.save(team);
//    }
//
//    public TeamDto getTeam(Long teamId) {
//        return teamRepository.findById(teamId).orElse(null);
//    }
    @Transactional
    public void addMatchingRating(MatchingDto matchingDto) {
        Matching matching = Matching.of(matchingDto.getSports(), matchingDto.getMatchingRoomId(), matchingDto.getSumRating(), matchingDto.getCapacity(), matchingDto.getTeamId());
        System.out.println("redis 저장소에 저장 직전");
        System.out.println("matching.getMatchingRoomId() = " + matching.getMatchingRoomId());
        Matching save = matchingRedisRepository.save(matching);
        System.out.println("save.getMatchingRoomId() : "+save.getMatchingRoomId());
        System.out.println("save.getSports() : "+save.getSports());
        System.out.println("save.getSumRating() : "+save.getSumRating());
        System.out.println("save.getCapacity() : "+save.getCapacity());
        System.out.println(save);
        System.out.println("redis 저장소에 저장 후");



//        System.out.println("=====================================");
//        List<Matching> matching2 = getMatchingRating("축구");
//        System.out.println("matching2.isEmpty() = " + matching2.isEmpty());
//        System.out.println("matching2.get(0).getMatchingRoomId() = " + matching2.get(0).getMatchingRoomId());
//        System.out.println(matching2);

//        redisTemplate.opsForZSet().add("teams", teamId, Double.valueOf(roomRating));
    }
//    }


    @Scheduled(fixedRate = 23000L)
    @Transactional
    //매칭 로직 - List 중 첫번째꺼 빼서 +-100점 범위로 매칭시키기 -> 매칭 성공시 매칭풀에서 삭제
    public void matchingSports() throws InterruptedException, JsonProcessingException {
        String[] sports= {"축구", "풋살", "농구"};
        for(int i=0; i<3; i++){
            matchingPoolSports(sports[i]);
        }
        System.out.println("무한 루프인가000000000000");


        Thread.sleep(15000L);
//        return matchingPoolSports();
//        return List.of();
    }


    @Transactional
    public void matchingPoolSports(String sport) throws  JsonProcessingException {
        boolean success = false;
        int count =0;
        System.out.println("무한 루프인가111111111");
        System.out.println("matchingPoolSports의 sport = " + sport);

        while(!success) {
            List<Matching> sports = getMatchingRating(sport);
            int sportsSize = sports.size();

            if(sports.isEmpty()){
                System.out.println("sports.isEmpty() = " + sports.isEmpty());
                count++;
                if(count>=sportsSize){
                    success=true;

                }
                continue;
            }
            System.out.println("sports.get(0).getMatchingRoomId() = " + sports.get(0).getMatchingRoomId());
            System.out.println("sportsSize = " + sportsSize);


            for (int i = 0; i < sportsSize; i++) {
                System.out.println("matchingPoolSports 에서 sports.get(0)" +sports.get(0));
                if(sportsSize==1){
                    continue;
                }
                int matchingFlag = 0;
                Matching standardMatching = sports.remove(0);
                System.out.println("=========40초마다 매칭풀 도는 중======i = "+ i + " : " + standardMatching.getMatchingRoomId() + "=========매칭풀 도는 중======");
                Long standardSumRating = standardMatching.getSumRating();
                Long standardCapacity = standardMatching.getCapacity();

                for (int j = 0; j < sportsSize-1; j++) {
                    if (standardSumRating <= sports.get(j).getSumRating() + 100 && standardSumRating >= sports.get(j).getSumRating() - 100 && Objects.equals(standardCapacity, sports.get(j).getCapacity()) && !Objects.equals(standardMatching.getTeamId(), sports.get(j).getTeamId())) {
                        System.out.println("=========조건에 맞습니다.====== " + standardMatching.getMatchingRoomId() + "=========조건에 맞습니다.======");
                        System.out.println("=========조건에 맞습니다.====== " + standardMatching.getCapacity() + "=========조건에 맞습니다.======");
                        System.out.println("=========조건에 맞습니다.====== " + standardMatching.getSumRating() + "=========조건에 맞습니다.======");
                        System.out.println("=========조건에 맞습니다.====== " + sports.get(j).getSumRating() + "=========조건에 맞습니다.======");
                        Matching matching = sports.remove(j);
                        List<Matching> matchingList = new ArrayList<>();
                        matchingList.add(standardMatching);
                        matchingList.add(matching);
                        System.out.println("====================== 토픽 발행 직전 ===============================");
                        matchingProducer.findMatchingTeam(matchingList);
                        System.out.println("====================== 토픽 발행 직후 ===============================");
                        //매칭 성공시 매칭풀(redis)에서 삭제
                        deleteMatching(Long.valueOf(standardMatching.getMatchingRoomId()));
                        deleteMatching(Long.valueOf(matching.getMatchingRoomId()));
                        //성공 알림 보내기-> 완료
                        //매칭된 팀들들 방은 매칭 성공했다는 테이블 상태 변경
                        sportsSize--;
                        matchingFlag = 1;
                    } else {
                        System.out.println("=========조건에 안 맞습니다.====== " + standardMatching.getMatchingRoomId() + "=========조건에 안맞습니다.======");
                        System.out.println("=========조건에 안 맞습니다.====== " + standardMatching.getCapacity() + "=========조건에 안맞습니다.======");
                        System.out.println("=========조건에 안 맞습니다.====== " + standardMatching.getSumRating() + "=========조건에 안맞습니다.======");
                        System.out.println("=========조건에 안 맞습니다.====== " + sports.get(j).getSumRating() + "=========조건에 안맞습니다.======");

                    }
                }
                if(matchingFlag ==0) {
                    sports.add(standardMatching);
                }
                //10초 마다 매칭 로직 실행
//                Thread.sleep(10000);

                }
            count++;
            //리스트 크기만큼 돈다.
            if(count>=sportsSize){
                success=true;

            }
        }
    }

//    @Scheduled(fixedRate = 10000L)
//
//    @Transactional
//    public List<Matching> matchFootBall() throws InterruptedException {
//        boolean isMatchFound = false;
//        int t = 0;
//        while(!isMatchFound) {
//            List<Matching> footBall = getMatchingRating("풋살");
//
//            for (int i = 0; i < footBall.size(); i++) {
//
//                System.out.println(footBall.get(i));
//                Matching standardMatching = footBall.remove(i);
//                Long standardSumRating = standardMatching.getSumRating();
//                Long standardCapacity = standardMatching.getCapacity();
//
//
//                for (int j = 0; j < footBall.size(); j++) {
//                    if (standardSumRating <= footBall.get(j).getSumRating() + 100 && standardSumRating >= footBall.get(j).getSumRating() - 100 && Objects.equals(standardCapacity, footBall.get(j).getCapacity())) {
//                        Matching matching = footBall.remove(j);
//                        List<Matching> matchingList = new ArrayList<>();
//                        matchingList.add(standardMatching);
//                        matchingList.add(matching);
//                        return matchingList;
//                    }
//                }
//                footBall.add(standardMatching);
//                //10초 마다 매칭 로직 실행
//                Thread.sleep(500);
//                t++;
//                //10분동안 매칭이 안되면 매칭 실패
//                if(t==60){
//                    isMatchFound=true;
//                }
//
//            }
//        }
//        return List.of();
//    }
//    @Scheduled(fixedRate = 10000L)
//
//    @Transactional
//    public List<Matching> matchBasketBall() throws InterruptedException {
//        boolean isMatchFound = false;
//        int t = 0;
//        while(!isMatchFound) {
//            List<Matching> basketBall = getMatchingRating("농구");
//
//            for (int i = 0; i < basketBall.size(); i++) {
//
//                System.out.println(basketBall.get(i));
//                Matching standardMatching = basketBall.remove(i);
//                Long standardSumRating = standardMatching.getSumRating();
//                Long standardCapacity = standardMatching.getCapacity();
//
//
//                for (int j = 0; j < basketBall.size(); j++) {
//                    if (standardSumRating <= basketBall.get(j).getSumRating() + 100 && standardSumRating >= basketBall.get(j).getSumRating() - 100 && Objects.equals(standardCapacity, basketBall.get(j).getCapacity())) {
//                        Matching matching = basketBall.remove(j);
//                        List<Matching> matchingList = new ArrayList<>();
//                        matchingList.add(standardMatching);
//                        matchingList.add(matching);
//                        return matchingList;
//                    }
//                }
//                basketBall.add(standardMatching);
//                //10초 마다 매칭 로직 실행
//                Thread.sleep(10000);
//                t++;
//                //10분동안 매칭이 안되면 매칭 실패
//                if(t==60){
//                    isMatchFound=true;
//                }
//
//            }
//        }
//        return List.of();
//    }
public List<Matching> getMatchingRating(String sports) {

    List<Matching> allMatchingRoom = (List<Matching>) matchingRedisRepository.findAll();
    if (allMatchingRoom.isEmpty()) {
        System.out.println("key가 널이다");
        return Collections.emptyList();
    }
    System.out.println("allMatchingRoom = " + allMatchingRoom);
    System.out.println("allMatchingRoom.get(0).getMatchingRoomId() = " + allMatchingRoom.get(0).getMatchingRoomId());
    System.out.println("allMatchingRoom.isEmpty() = " + allMatchingRoom.isEmpty());


    return allMatchingRoom.stream()
            .filter(matching -> sports.equals(matching.getSports()))
            .collect(Collectors.toList());
//    HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
//    System.out.println("=====================sports = " + sports);
//
//    // 키 패턴을 sports로 시작하는 것으로 수정
//    Set<String> keys = redisTemplate.keys("*");
//    if (keys == null) {
//        System.out.println("key가 널이다...시발");
//        return Collections.emptyList();
//    }
//
//    System.out.println("여기까지는 통과한건가??????????");
//    System.out.println("==================keys.size() ========= " + keys.size());
//    System.out.println("==================keys========= " + keys);
//
//    List<Matching> result = new ArrayList<>();
//    for (String key : keys) {
//        // 키의 타입 확인
//        DataType type = redisTemplate.type(key);
//        // 키의 타입이 Hash인 경우에만 Hash 연산 수행
//        if (type == DataType.HASH) {
//            ObjectMapper objectMapper = new ObjectMapper();
//            result.addAll(hashOperations.entries(key).values().stream()
//                    .map(value -> {
//                        try {
//                            return objectMapper.readValue((String) value, Matching.class);
//                        } catch (JsonProcessingException e) {
//                            throw new RuntimeException("Error parsing JSON to Matching object.", e);
//                        }
//                    })
//                    .filter(matching -> sports.equals(matching.getSports()))
//                    .collect(Collectors.toList()));
//        }
//    }

//    return result;
}
            // 각 키에 해당하는 Matching 객체를 가져와서, sports 필드가 일치하는 객체만 필터링합니다.
//            return keys.stream()
//                    .map(key -> (Matching) hashOperations.get("*", key))
//                    .filter(matching -> sports.equals(matching.getSports()))
//                    .collect(Collectors.toList());
//        List<Matching> matching = matchingRedisRepository.findAllBySports(sports);
//        System.out.println("matching.isEmpty() = " + matching.isEmpty());
//        System.out.println("matching.get(0).getMatchingRoomId() = " + matching.get(0).getMatchingRoomId());
//

    public void deleteMatching(Long matchingRoomId) {
        matchingRedisRepository.deleteById(String.valueOf(matchingRoomId));
    }

    public Long getMatchingPoolCount() {
        return matchingRedisRepository.count();
    }


//
//    @Transactional
//    public void addSoccerRating(Long teamId, Long roomRating) {
//        ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();
//        zSetOperations.add("soccerRooms", String.valueOf(teamId), Double.valueOf(roomRating));
//
//    }
//
//    public Long getSoccerRating(Long teamId) {
//        return Long.valueOf(String.valueOf(redisTemplate.opsForZSet().score("soccerRooms", String.valueOf(teamId))));
//    }
//
//    public List<MatchingDto>
//    Set<ZSetOperations.TypedTuple<String>> typedTuples = stringStringZSetOperations.reverseRangeWithScores(key, 0, -1);
//
//    @Transactional
//    public void addFootBallRating(Long teamId, Long roomRating) {
//        ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();
//        zSetOperations.add("footBallRooms", String.valueOf(teamId), Double.valueOf(roomRating));
//
//    }
//
//    public Long getFootBallRating(Long teamId) {
//        return Long.valueOf(String.valueOf(redisTemplate.opsForZSet().score("footBallRooms", String.valueOf(teamId))));
//    }
//
//    @Transactional
//    public void addBasketBallBallRating(Long teamId, Long roomRating) {
//        ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();
//        zSetOperations.add("basketBallRooms", String.valueOf(teamId), Double.valueOf(roomRating));
//
//    }
//
//    public Long getBasketBallRating(Long teamId) {
//        return Long.valueOf(String.valueOf(redisTemplate.opsForZSet().score("basketBallRooms", String.valueOf(teamId))));
//    }


    //point는 점수를 저장
//    @Transactional
//    public void create(Long teamId, Long sumRating) {
//        ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();
//        zSetOperations.add("Key", "Value", sumRating);
//        if (member.getRole() != Role.ADMIN) {
//            redisTemplate.opsForZSet().add("ranking", member.getNickname(), member.getPoint());
//        }
//        pointRepository.save(point);
//    }
//    public List<MatchingPoolDto> getSoccerRatingList() {
//        String key = "soccerRating";
//        ZSetOperations<String, String> stringStringZSetOperations = redisTemplate.opsForZSet();
//        //레이팅 높은 순으로 전부 들고오기
//        Set<ZSetOperations.TypedTuple<String>> typedTuples = stringStringZSetOperations.reverseRangeWithScores(key, 0, -1);
//        assert typedTuples != null;
//        return typedTuples.stream().map(MatchingPoolDto::new).collect(Collectors.toList());
//    }

//    public List<MatchingPoolDto> getFootBallRatingList() {
//        String key = "footBallRating";
//        ZSetOperations<String, String> stringStringZSetOperations = redisTemplate.opsForZSet();
//        //레이팅 높은 순으로 전부 들고오기
//        Set<ZSetOperations.TypedTuple<String>> typedTuples = stringStringZSetOperations.reverseRangeWithScores(key, 0, -1);
//        assert typedTuples != null;
//        return typedTuples.stream().map(MatchingPoolDto::new).collect(Collectors.toList());
//    }
//
//    public List<MatchingPoolDto> getBasketballRatingList() {
//        String key = "basketBallRating";
//        ZSetOperations<String, String> stringStringZSetOperations = redisTemplate.opsForZSet();
//        //레이팅 높은 순으로 전부 들고오기
//        Set<ZSetOperations.TypedTuple<String>> typedTuples = stringStringZSetOperations.reverseRangeWithScores(key, 0, -1);
//        assert typedTuples != null;
//        return typedTuples.stream().map(MatchingPoolDto::new).collect(Collectors.toList());
//    }
//    public Long getMyRank(String token){
//        Long ranking=0L;
//        Double ranking1 = redisTemplate.opsForZSet().score("ranking", jwtService.findMemberByToken(token).getNickname());
//        Set<String> ranking2 = redisTemplate.opsForZSet().reverseRangeByScore("ranking", ranking1, ranking1, 0, 1);
//        for (String s : ranking2) {
//            ranking = redisTemplate.opsForZSet().reverseRank("ranking", s);
//        }
//        return ranking+1;//index가 0부터 시작되어서 1 더해준다
//    }
}


/**
 *
 * //        boolean isMatchFound = false;
 * //        int t = 0;
 *  while(true) {
 *             List<Matching> soccer = getMatchingRating("축구");
 *
 *             for (int i = 0; i < soccer.size(); i++) {
 *
 *                 System.out.println(soccer.get(i));
 *                 Matching standardMatching = soccer.remove(i);
 *                 Long standardSumRating = standardMatching.getSumRating();
 *                 Long standardCapacity = standardMatching.getCapacity();
 *
 *                 for (int j = 0; j < soccer.size(); j++) {
 *                     if (standardSumRating <= soccer.get(j).getSumRating() + 100 && standardSumRating >= soccer.get(j).getSumRating() - 100 && Objects.equals(standardCapacity, soccer.get(j).getCapacity())) {
 *                         Matching matching = soccer.remove(j);
 *                         List<Matching> matchingList = new ArrayList<>();
 *                         matchingList.add(standardMatching);
 *                         matchingList.add(matching);
 *                         return matchingList;
 *                     }
 *                 }
 *                 soccer.add(standardMatching);
 *                 //10초 마다 매칭 로직 실행
 *                 Thread.sleep(10000);
 * //                t++;
 * //                if(t==60){
 * //                    isMatchFound=true;
 * //                }
 *
 *             }
 *         }
 */