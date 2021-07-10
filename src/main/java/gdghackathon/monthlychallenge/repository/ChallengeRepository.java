package gdghackathon.monthlychallenge.repository;

import gdghackathon.monthlychallenge.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
}
