package gdghackathon.monthlychallenge.dto;

import gdghackathon.monthlychallenge.entity.Mission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeResponseDto {

    private Long id;
    private Integer missionCount;
    private String name;
    private LocalDateTime createDate;
    private List<Mission> ownMissions;

}
