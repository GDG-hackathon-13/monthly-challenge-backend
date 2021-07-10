package gdghackathon.monthlychallenge.repository;

import gdghackathon.monthlychallenge.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    List<Mission> findAllByChallengeId(Long id);

}
