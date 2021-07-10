package gdghackathon.monthlychallenge.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@RequiredArgsConstructor
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


    @ManyToOne
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

}
