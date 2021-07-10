package gdghackathon.monthlychallenge.entity;

import com.fasterxml.jackson.annotation.JsonView;
import gdghackathon.monthlychallenge.dto.View;
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
    @JsonView(View.Summary.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonView(View.Summary.class)
    @NonNull
    private Integer mission_count;
    @JsonView(View.Summary.class)
    @NonNull
    private String name;
    @JsonView(View.Summary.class)
    @NonNull
    private LocalDateTime create_date;

    @OneToMany(mappedBy = "challenge", cascade = CascadeType.REMOVE)
    private List<Challenge_Tag> ownTags;
    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL)
    private List<Mission> ownMissions;
}