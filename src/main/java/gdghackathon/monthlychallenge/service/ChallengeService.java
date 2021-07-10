package gdghackathon.monthlychallenge.service;

import gdghackathon.monthlychallenge.dto.CreateChallengeDTO;
import gdghackathon.monthlychallenge.entity.Challenge;
import gdghackathon.monthlychallenge.entity.Mission;
import gdghackathon.monthlychallenge.repository.ChallengeRepository;
import gdghackathon.monthlychallenge.repository.MissionRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ChallengeService {
    private ChallengeRepository challengeRepository;
    private MissionRepository missionRepository;

    @Autowired
    public ChallengeService(ChallengeRepository challengeRepository, MissionRepository missionRepository){
        this.challengeRepository = challengeRepository;
        this.missionRepository = missionRepository;
    }

    public Long createChallenge(CreateChallengeDTO dto){
        Challenge challenge = new Challenge(0, dto.getName(), LocalDateTime.now());
        List<Mission> missions = dto.getMission();
        if (missions.size()!=30)
            throw new IllegalArgumentException("size of mission array is not 30.");
        else {
            challengeRepository.save(challenge);
            for (Mission m : missions) {
                m.setChallenge(challenge);
                missionRepository.save(m);
            }
            return challenge.getId();
        }
    }

    public void deleteChallenge(Long id){
        Optional<Challenge> challengeToDelete = challengeRepository.findById(id);
        if (challengeToDelete.isEmpty())
            throw new EntityNotFoundException("not found challenge to delete. check id of challenge.");
        else
            challengeRepository.delete(challengeToDelete.get());
    }

    public Challenge getChallenge(Long id){
        Optional<Challenge> challengeToGet = challengeRepository.findById(id);
        if (challengeToGet.isEmpty())
            throw new EntityNotFoundException("not found challenge to get. check id of challenge.");
        else
            return challengeToGet.get();
    }

    public List<Challenge> getChallenges(){
        return challengeRepository.findAll();
    }
}
