package gdghackathon.monthlychallenge.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@RequiredArgsConstructor
@IdClass(Challenge_Tag_PK.class)
public class Challenge_Tag {
    @JsonIgnore
    @Id
    @ManyToOne
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;
    @Id
    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;
}
