package gdghackathon.monthlychallenge.repository;

import gdghackathon.monthlychallenge.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

    // Challenge 샘플 데이터(8개) 조회
    List<Challenge> findTop8ByOrderByIdAsc();

}
