package gdghackathon.monthlychallenge.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Challenge_Tag_PK implements Serializable {
    private Long challenge;
    private Long tag;
}
