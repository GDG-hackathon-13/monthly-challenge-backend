package gdghackathon.monthlychallenge.repository;

import gdghackathon.monthlychallenge.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionRepository extends JpaRepository<Mission, Long> {
}
