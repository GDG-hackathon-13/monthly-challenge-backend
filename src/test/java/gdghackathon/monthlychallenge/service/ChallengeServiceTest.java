package gdghackathon.monthlychallenge.service;

import gdghackathon.monthlychallenge.dto.CreateChallengeDTO;
import gdghackathon.monthlychallenge.entity.Mission;
import gdghackathon.monthlychallenge.repository.ChallengeRepository;
import gdghackathon.monthlychallenge.repository.MissionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChallengeServiceTest {
    private ChallengeService challengeService;
    private ChallengeRepository challengeRepository;
    private MissionRepository missionRepository;

    @Autowired
    public ChallengeServiceTest(ChallengeService challengeService, ChallengeRepository challengeRepository, MissionRepository missionRepository){
        this.challengeService = challengeService;
        this.challengeRepository = challengeRepository;
        this.missionRepository = missionRepository;
    }

    @AfterEach
    void deleteAll(){
        challengeRepository.deleteAll();
        missionRepository.deleteAll();
    }

    @Test
    void createChallenge() {
        List<Mission> missions = new ArrayList<>();
        CreateChallengeDTO createChallengeDTO = new CreateChallengeDTO("내 챌린지", missions, Collections.EMPTY_LIST);

        Assertions.assertThrows(IllegalArgumentException.class, ()->challengeService.createChallenge(createChallengeDTO));

        for (int i = 0; i<30; i++){
            missions.add(new Mission(false, Integer.toString(i)+"일차"));
        }
        Long createId = challengeService.createChallenge(new CreateChallengeDTO("내 챌린지", missions, Collections.EMPTY_LIST));
        Assertions.assertEquals(true, challengeRepository.findById(createId).isPresent());
    }

    @Test
    void deleteAndGetChallenge() {
        List<Mission> missions = new ArrayList<>();
        for (int i = 0; i<30; i++){
            missions.add(new Mission(false, Integer.toString(i)+"일차"));
        }
        Long createId = challengeService.createChallenge(new CreateChallengeDTO("내 챌린지", missions, Collections.EMPTY_LIST));
        Assertions.assertEquals("내 챌린지", challengeService.getChallenge(createId).getName());

        challengeService.deleteChallenge(createId);
        Assertions.assertThrows(EntityNotFoundException.class, () -> challengeService.getChallenge(createId));
    }

    @Test
    void getChallenges() {
        List<Mission> missions = new ArrayList<>();
        for (int i = 0; i<30; i++){
            missions.add(new Mission(false, Integer.toString(i)+"일차"));
        }

        Long createdId = challengeService.createChallenge(new CreateChallengeDTO("내 챌린지", missions, Collections.EMPTY_LIST));
        Long createId2 = challengeService.createChallenge(new CreateChallengeDTO("아무것도 없는 챌린지", missions, Collections.EMPTY_LIST));

        Assertions.assertEquals(2,challengeService.getChallenges().size());
    }
}