package gdghackathon.monthlychallenge.service;

import gdghackathon.monthlychallenge.dto.MissionResponseDto;
import gdghackathon.monthlychallenge.entity.Mission;
import gdghackathon.monthlychallenge.repository.MissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MissionService {
    MissionRepository missionRepository;

    MissionService(MissionRepository missionRepository){
        this.missionRepository = missionRepository;
    }

    public List<MissionResponseDto> getMissions(Long challengeId) {
        Assert.notNull(challengeId, "challengeId must not be null");

        final List<Mission> missionList = missionRepository.findAllByChallengeId(challengeId);
        return missionList.stream()
                .map(Mission::entityToDto)
                .collect(Collectors.toList());
    }
}
