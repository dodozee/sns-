package com.withsports.teamservice.domain.teamuser.entity;


import com.withsports.teamservice.domain.teamuser.exception.TeamUserException;
import com.withsports.teamservice.domain.teamuser.service.TeamUserProducer;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TeamUserListener {

    @Lazy
    @Autowired
    private TeamUserProducer teamUserProducer;


    @PostUpdate
    public void postUpdate(TeamUser teamUser){
        TeamUserStatus teamUserStatus = teamUser.getStatus();
        log.info("[TeamUserListener] {}", TeamUserStatus.PLACED.name());
        if (teamUserStatus == TeamUserStatus.PLACED) {
            try{
                teamUserProducer.applyJoinTeamUser(teamUser);
            }catch (Exception ex){
                throw new TeamUserException(ex.getMessage());
            }

        } else if (teamUserStatus == TeamUserStatus.ACCEPTED) {
            try {
                teamUserProducer.acceptJoinTeamUser(teamUser);
            } catch (Exception ex) {
                throw new TeamUserException(ex.getMessage());
            }
        } else if (teamUserStatus == TeamUserStatus.REJECTED) {
            try {
                teamUserProducer.rejectedJoinTeamUser(teamUser);
            } catch (Exception ex) {
                throw new TeamUserException(ex.getMessage());
            }
        }
    }


}
