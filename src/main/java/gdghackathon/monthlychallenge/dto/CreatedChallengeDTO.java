package gdghackathon.monthlychallenge.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreatedChallengeDTO {
    @NonNull
    Long id;
}
