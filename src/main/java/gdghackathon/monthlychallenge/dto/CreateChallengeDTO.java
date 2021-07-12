package gdghackathon.monthlychallenge.dto;

import gdghackathon.monthlychallenge.entity.Mission;
import gdghackathon.monthlychallenge.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class CreateChallengeDTO {
    @NonNull
    private String name;
    private List<CreateChallengeMissionDTO> mission;
    private List<Tag> tag;
}
