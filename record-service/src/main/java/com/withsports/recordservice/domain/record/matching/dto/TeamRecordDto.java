package com.withsports.recordservice.domain.record.matching.dto;

import com.withsports.recordservice.domain.record.team.entity.TeamRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamRecordDto {
    private Long id;
    private Long teamId;
    private String teamName;
    private String sports;
    private Long win;
    private Long lose;
    private Long draw;
    private Double winRate;

    public static TeamRecordDto of(TeamRecord teamRecord) {
        return TeamRecordDto.builder()
                .id(teamRecord.getId())
                .teamId(teamRecord.getTeamId())
                .teamName(teamRecord.getTeamName())
                .sports(teamRecord.getSports())
                .win(teamRecord.getWin())
                .lose(teamRecord.getLose())
                .draw(teamRecord.getDraw())
                .winRate((double) teamRecord.getWin() / (teamRecord.getWin() + teamRecord.getLose() + teamRecord.getDraw()))
                .build();
    }


//    public Double getWinRate() {
//        return (double) (win) / (win + lose + draw);
//    }
}
