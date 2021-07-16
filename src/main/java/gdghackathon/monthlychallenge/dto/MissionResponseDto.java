package gdghackathon.monthlychallenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MissionResponseDto {

    private Long id;
    private Boolean mission_check;
    private String name;
    private String memo;
    private String image;
    private String thumbnail_image;
    private LocalDateTime certification_date;

}
