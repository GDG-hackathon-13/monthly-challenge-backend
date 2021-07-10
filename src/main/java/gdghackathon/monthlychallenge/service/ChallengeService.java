package gdghackathon.monthlychallenge.service;

import gdghackathon.monthlychallenge.entity.Challenge;
import gdghackathon.monthlychallenge.entity.Mission;
import gdghackathon.monthlychallenge.repository.ChallengeRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChallengeService {
    private ChallengeRepository challengeRepository;

    public ChallengeService(ChallengeRepository challengeRepository){
        this.challengeRepository = challengeRepository;
    }

}
