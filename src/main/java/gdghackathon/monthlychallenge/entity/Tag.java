package gdghackathon.monthlychallenge.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String text;

    @OneToMany(mappedBy = "tag")
    private List<Challenge_Tag> ownChallenges;
}
