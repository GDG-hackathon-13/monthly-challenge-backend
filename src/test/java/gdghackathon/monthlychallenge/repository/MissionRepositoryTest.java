package gdghackathon.monthlychallenge.repository;

import gdghackathon.monthlychallenge.entity.Mission;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MissionRepositoryTest {

    @Autowired
    private MissionRepository missionRepository;

    @Test
    @DisplayName("챌린지에 존재하는 30개의 미션을 조회한다.")
    void getMissions() {
        /* given */
        Long challengeId = 1L;

        /* when */
        List<Mission> missionList = missionRepository.findAllByChallengeId(challengeId);

        /* then */
        assertThat(missionList.size()).isEqualTo(30);
    }

}