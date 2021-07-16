package gdghackathon.monthlychallenge.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gdghackathon.monthlychallenge.dto.MissionResponseDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

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
    private LocalDateTime certification_date;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    public Mission(Boolean mission_check, String name, String memo, String image, String thumbnail_image, Challenge challenge) {
        this(mission_check, name, memo, image, thumbnail_image, challenge, null);
    }

    public Mission(Boolean mission_check, String name, String memo, String image, String thumbnail_image,
                   Challenge challenge, LocalDateTime certification_date) {
        this.mission_check = mission_check;
        this.name = name;
        this.memo = memo;
        this.image = image;
        this.thumbnail_image = thumbnail_image;
        this.challenge = challenge;
        this.certification_date = certification_date;
    }

    public static MissionResponseDto entityToDto(Mission entity) {
        return MissionResponseDto.builder()
                .id(entity.getId())
                .mission_check(entity.getMission_check())
                .name(entity.getName())
                .memo(entity.getMemo())
                .image(entity.getImage())
                .thumbnail_image(entity.getThumbnail_image())
                .certification_date(entity.getCertification_date())
                .build();
    }

}
