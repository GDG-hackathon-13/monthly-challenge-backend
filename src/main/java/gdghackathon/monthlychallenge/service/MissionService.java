package gdghackathon.monthlychallenge.service;

import gdghackathon.monthlychallenge.repository.MissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MissionService {
    MissionRepository missionRepository;

    MissionService(MissionRepository missionRepository){
        this.missionRepository = missionRepository;
    }
}
