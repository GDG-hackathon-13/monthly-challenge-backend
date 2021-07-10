package gdghackathon.monthlychallenge.service;

import gdghackathon.monthlychallenge.entity.Challenge;
import gdghackathon.monthlychallenge.entity.Mission;
import gdghackathon.monthlychallenge.repository.ChallengeRepository;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChallengeService {
    private final ChallengeRepository challengeRepository;

    public ChallengeService(ChallengeRepository challengeRepository){
        this.challengeRepository = challengeRepository;
    }

    public List<ChallengeResponseDto> getSampleChallenges() {
        final List<Challenge> challengeList = challengeRepository.findTop8ByOrderByIdAsc();

        return challengeList.stream()
                .map(Challenge::entityToDto)
                .collect(Collectors.toList());
    }
}

