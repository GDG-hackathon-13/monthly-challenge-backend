package gdghackathon.monthlychallenge.controller;

import gdghackathon.monthlychallenge.entity.Challenge;
import gdghackathon.monthlychallenge.entity.Mission;
import gdghackathon.monthlychallenge.repository.ChallengeRepository;
import gdghackathon.monthlychallenge.service.ChallengeService;
import gdghackathon.monthlychallenge.service.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/challenge")
public class MainController {
    private ChallengeService challengeService;
    private MissionService missionService;

    @Autowired
    MainController(ChallengeService challengeService, MissionService missionService){
        this.challengeService = challengeService;
        this.missionService = missionService;
    }

    @PostMapping("") //챌린지 생성 requestBody
    public ResponseEntity<Long> createChallenge(){

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @DeleteMapping("/{id}") //챌린지 삭제
    public ResponseEntity<Void> deleteChallenge(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/{id}")   //특정 챌린지 조회
    public ResponseEntity<Challenge> getChallenge(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("")    //챌린지 목록 조회
    public ResponseEntity<List<Challenge>> getChallenges(){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/samples")    //샘플 챌린지 조회
    public ResponseEntity<List<Challenge>> getSamples(){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/{challengeId}/mission")  //미션 조회
    public ResponseEntity<List<Mission>> getMissions(@PathVariable Long challengeId){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/{challengeId}/mission/{missionId}") //미션 인증 requestBody
    public ResponseEntity<Void> completeMission(@PathVariable Long challengeId, @PathVariable Long missionId){//requestBody
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
