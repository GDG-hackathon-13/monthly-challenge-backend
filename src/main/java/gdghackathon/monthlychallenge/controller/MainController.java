package gdghackathon.monthlychallenge.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import gdghackathon.monthlychallenge.dto.CreateChallengeDTO;
import gdghackathon.monthlychallenge.dto.CreatedChallengeDTO;
import gdghackathon.monthlychallenge.dto.MissionResponseDto;
import gdghackathon.monthlychallenge.dto.View;
import gdghackathon.monthlychallenge.dto.ChallengeResponseDto;
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
    private final ChallengeService challengeService;
    private final MissionService missionService;

    @Autowired
    MainController(ChallengeService challengeService, MissionService missionService){
        this.challengeService = challengeService;
        this.missionService = missionService;
    }

    @PostMapping("") //챌린지 생성 requestBody
    public ResponseEntity<CreatedChallengeDTO> createChallenge(@RequestBody CreateChallengeDTO createChallengeDTO){
        Long createdId = challengeService.createChallenge(createChallengeDTO);
        CreatedChallengeDTO createdChallengeDTO = new CreatedChallengeDTO(createdId);
        return ResponseEntity.status(HttpStatus.OK).body(createdChallengeDTO);
    }

    @DeleteMapping("/{id}") //챌린지 삭제
    public ResponseEntity<Void> deleteChallenge(@PathVariable Long id){
        challengeService.deleteChallenge(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/{id}")   //특정 챌린지 조회
    public ResponseEntity<Challenge> getChallenge(@PathVariable Long id){
        Challenge challenge = challengeService.getChallenge(id);
        return ResponseEntity.status(HttpStatus.OK).body(challenge);
    }

    @JsonView(View.Summary.class)
    @GetMapping("")    //챌린지 목록 조회
    public ResponseEntity<List<Challenge>> getChallenges(){
        return ResponseEntity.status(HttpStatus.OK).body(challengeService.getChallenges());
    }

    @GetMapping("/samples")    //샘플 챌린지 조회
    public ResponseEntity<List<ChallengeResponseDto>> getSamples() {
        return ResponseEntity.status(HttpStatus.OK).body(challengeService.getSampleChallenges());
    }

    @GetMapping("/{challengeId}/mission")  //미션 조회
    public ResponseEntity<List<MissionResponseDto>> getMissions(@PathVariable Long challengeId){
        List<MissionResponseDto> missionResponse = missionService.getMissions(challengeId);
        return ResponseEntity.status(HttpStatus.OK).body(missionResponse);
    }

    @PostMapping("/{challengeId}/mission/{missionId}") //미션 인증 requestBody
    public ResponseEntity<Void> completeMission(@PathVariable Long challengeId, @PathVariable Long missionId){//requestBody
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
