package gdghackathon.monthlychallenge.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private Boolean missionCheck;
    @NonNull
    private String name;

    private String memo;
    private String image;
    private String thumbnailImage;

    @ManyToOne
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

}
