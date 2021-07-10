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
    void insertChallenge() {
        Challenge challenge = Challenge.builder()
                .mission_count(0)
                .name("테스트용 데이터")
                .create_date(LocalDateTime.now())
                .build();
        challengeRepository.save(challenge);
    }

    @Test
    void insertMission() {
        String[] missions = {
                "아침 먹기", "혼자 카페가서 책읽기", "산책가기", "양치 3분", "낮잠자기",
                "SNS 업로드", "친구한테 전화하기", "아메리카노 마시기", "저녁 차리기", "셀카찍기",
                "넷플릭스 보기", "명상하기", "홈트하기", "연어동 먹기", "아침밥 먹기",
                "쇼핑하기", "자전거 타기", "부모님이랑 사진찍기", "화분사기", "아침 6시 기상",
                "책 40p 읽기", "옷 정리하기", "배달음식 먹기", "아침 스트레칭", "드라이브",
                "아침밥 먹기", "아침밥 먹기", "나무사진 찍기", "영화보기", "12시 전 취침"
        };

        Challenge challenge = Challenge.builder()
                .id(1L)
                .mission_count(0)
                .name("소확도")
                .create_date(LocalDateTime.now())
                .build();

        IntStream.rangeClosed(0, 29).forEach(j -> { // Mission
            Mission mission = Mission.builder()
                    .mission_check(false)
                    .name(missions[j])
                    .challenge(challenge)
                    .build();

            missionRepository.save(mission);
        });
    }

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