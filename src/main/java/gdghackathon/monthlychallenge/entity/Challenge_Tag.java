package gdghackathon.monthlychallenge.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
@IdClass(Challenge_Tag_PK.class)
public class Challenge_Tag {
    @Id
    @ManyToOne
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;
    @Id
    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;
}
