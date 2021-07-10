package gdghackathon.monthlychallenge.entity;

import gdghackathon.monthlychallenge.dto.MissionResponseDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "challenge")
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private Boolean mission_check;
    @NonNull
    private String name;

    private String memo;
    private String image;
    private String thumbnail_image;

    @ManyToOne
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    public static MissionResponseDto entityToDto(Mission entity) {
        return MissionResponseDto.builder()
                .id(entity.getId())
                .mission_check(entity.getMission_check())
                .name(entity.getName())
                .memo(entity.getMemo())
                .image(entity.getImage())
                .thumbnail_image(entity.getThumbnail_image())
                .build();
    }

}
