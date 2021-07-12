package gdghackathon.monthlychallenge.dto;

import lombok.*;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor //requestBody 매핑 위해서
public class CreateChallengeMissionDTO {
    private Boolean mission_check;
    @NonNull
    private String name;

    private String memo;
    private String image;
    private String thumbnail_image;

    private Boolean missionCheck;
    private String imageUrl;
    private String thumbnailImageUrl;
}
