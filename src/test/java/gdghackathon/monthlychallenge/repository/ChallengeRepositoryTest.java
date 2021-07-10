package gdghackathon.monthlychallenge.repository;

import gdghackathon.monthlychallenge.entity.Challenge;
import gdghackathon.monthlychallenge.entity.Mission;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChallengeRepositoryTest {

    @Autowired
    ChallengeRepository challengeRepository;

    @Autowired
    MissionRepository missionRepository;

    @Test
    @DisplayName("각각 30개의 미션을 가진 8개의 챌린지 샘플 데이터를 삽입한다.")
    void insert_Challenge_Sample() {
        IntStream.rangeClosed(1, 8).forEach(i -> { // Challenge
            Challenge challenge = Challenge.builder()
                    .mission_count(0)
                    .name("Challenge " + i)
                    .create_date(LocalDateTime.now())
                    .build();
            challengeRepository.save(challenge);

            IntStream.rangeClosed(1, 30).forEach(j -> { // Mission
                Mission mission = Mission.builder()
                        .mission_check(false)
                        .name("Mission " + j)
                        .memo("Memo " + j + "...")
                        .image("image url ... " + j + ".jpg")
                        .thumbnail_image("thumbnail url ... " + j + ".jpg")
                        .challenge(challenge)
                        .build();

                missionRepository.save(mission);
            });
        });
    }


    @Transactional
    @Test
    @DisplayName("각각 30개의 미션을 가진 8개의 챌린지 샘플 데이터를 조회한다.")
    void get_Challenge_Sample() {
        /* given */
        List<Challenge> challengeList = challengeRepository.findTop8ByOrderByIdAsc();

        /* when */
        List<Mission> missionList = challengeList.get(0).getOwnMissions();

        /* then */
        assertThat(challengeList.size()).isEqualTo(8);
        assertThat(missionList.size()).isEqualTo(30);
    }
}