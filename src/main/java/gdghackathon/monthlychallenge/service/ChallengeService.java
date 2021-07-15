package gdghackathon.monthlychallenge.service;

import gdghackathon.monthlychallenge.dto.ChallengeResponseDto;
import gdghackathon.monthlychallenge.dto.CreateChallengeDTO;
import gdghackathon.monthlychallenge.dto.CreateChallengeMissionDTO;
import gdghackathon.monthlychallenge.entity.Challenge;
import gdghackathon.monthlychallenge.entity.Mission;
import gdghackathon.monthlychallenge.repository.ChallengeRepository;
import gdghackathon.monthlychallenge.repository.MissionRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final MissionRepository missionRepository;

    @Transactional
    public Long createChallenge(CreateChallengeDTO dto) {
        Challenge challenge = new Challenge(0, dto.getName(), LocalDateTime.now());
        List<CreateChallengeMissionDTO> missions = dto.getMission();
        if (missions.size() != 30)
            throw new IllegalArgumentException("size of mission array is not 30.");
        else {
            challengeRepository.save(challenge);
            for (CreateChallengeMissionDTO m : missions) {
                Boolean check = m.getMission_check();
                String image = m.getImage();
                String thumbnail = m.getThumbnail_image();
                System.out.println(m.getName() + "\n" + m.getMission_check() + "\n" + m.getMissionCheck() + "\n");
                if (check == null) {
                    check = m.getMissionCheck();
                }
                if (image == null) {
                    image = m.getImageUrl();
                }
                if (thumbnail == null) {
                    thumbnail = m.getThumbnailImageUrl();
                }
                if (check == null || m.getName() == null)
                    throw new IllegalArgumentException("mission_check or name of  Mission is null.");
                Mission mission = new Mission(check, m.getName(), m.getMemo(), image, thumbnail, challenge);
                missionRepository.save(mission);
            }
            return challenge.getId();
        }
    }

    @Transactional
    public void deleteChallenge(Long id) {
        Optional<Challenge> challengeToDelete = challengeRepository.findById(id);
        if (challengeToDelete.isEmpty())
            throw new EntityNotFoundException("not found challenge to delete. check id of challenge.");
        else
            challengeRepository.delete(challengeToDelete.get());
    }

    @Transactional(readOnly = true)
    public Challenge getChallenge(Long id) {
        Optional<Challenge> challengeToGet = challengeRepository.findById(id);
        if (challengeToGet.isEmpty())
            throw new EntityNotFoundException("not found challenge to get. check id of challenge.");
        else
            return challengeToGet.get();
    }

    @Transactional(readOnly = true)
    public List<Challenge> getChallenges() {
        return challengeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<ChallengeResponseDto> getSampleChallenges() {
        final List<Challenge> challengeList = challengeRepository.findTop8ByOrderByIdAsc();
        return challengeList.stream()
                .map(Challenge::entityToDto)
                .collect(Collectors.toList());

    }
}

