package gdghackathon.monthlychallenge.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class Challenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private Integer mission_count;
    @NonNull
    private String name;
    @NonNull
    private LocalDateTime create_date;

    @OneToMany(mappedBy = "challenge", cascade = CascadeType.REMOVE)
    private List<Challenge_Tag> ownTags;
    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL)
    private List<Mission> ownMissions;
}
