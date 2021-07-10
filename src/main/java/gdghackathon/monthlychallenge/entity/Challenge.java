package gdghackathon.monthlychallenge.entity;

import gdghackathon.monthlychallenge.dto.ChallengeResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class Challenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private Integer missionCount;
    @NonNull
    private String name;
    @NonNull
    private LocalDateTime createDate;

    @OneToMany(mappedBy = "challenge", cascade = CascadeType.REMOVE)
    private List<Challenge_Tag> ownTags;
    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL)
    private List<Mission> ownMissions;

    public static ChallengeResponseDto entityToDto(Challenge entity) {
        return ChallengeResponseDto.builder()
                .id(entity.getId())
                .missionCount(entity.getMissionCount())
                .name(entity.getName())
                .createDate(entity.getCreateDate())
                .ownMissions(entity.getOwnMissions())
                .build();
    }

}
