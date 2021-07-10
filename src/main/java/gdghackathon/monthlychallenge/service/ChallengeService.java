package gdghackathon.monthlychallenge.service;

import gdghackathon.monthlychallenge.dto.CreateChallengeDTO;
import gdghackathon.monthlychallenge.entity.Challenge;
import gdghackathon.monthlychallenge.entity.Mission;
import gdghackathon.monthlychallenge.repository.ChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChallengeService {
    @Autowired
    ChallengeRepository challengeRepository;
}
