package gdghackathon.monthlychallenge.service;

import gdghackathon.monthlychallenge.aws.AttachedFile;
import gdghackathon.monthlychallenge.aws.S3Client;
import gdghackathon.monthlychallenge.dto.CreateMissionDTO;
import gdghackathon.monthlychallenge.dto.MissionResponseDto;
import gdghackathon.monthlychallenge.entity.Challenge;
import gdghackathon.monthlychallenge.entity.Mission;
import gdghackathon.monthlychallenge.repository.ChallengeRepository;
import gdghackathon.monthlychallenge.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityNotFoundException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class MissionService {

    private final MissionRepository missionRepository;
    private final ChallengeRepository challengeRepository;

    @Transactional(readOnly = true)
    public List<MissionResponseDto> getMissions(Long challengeId) {
        Assert.notNull(challengeId, "challengeId must not be null");

        final List<Mission> missionList = missionRepository.findAllByChallengeId(challengeId);
        return missionList.stream()
                .map(Mission::entityToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void completeMission(CreateMissionDTO createMissionDTO,
                                Long challengeId,
                                Long missionId) throws IOException {
        Assert.notNull(challengeId, "challengeId must not be null");
        Assert.notNull(missionId, "missionId must not be null");

        Mission mission = missionRepository.findByChallengeIdAndId(challengeId, missionId);
        // ?????? ????????? ?????? ?????? ?????? ??????
        Assert.state(!mission.getMission_check(), "first mission check must be false");

        mission.setMission_check(true);
        updateChallengeMissionCount(challengeId);

        if (createMissionDTO.getMemo() != null) {
            mission.setMemo(createMissionDTO.getMemo());
        }
        String imagePath = null;
        String thumbnailPath = null;
        AttachedFile file = AttachedFile.of(createMissionDTO.getFile());

        if (createMissionDTO.getMemo() == null && file == null) {
            return;
        }
        if (file != null) {
            S3Client s3Client = S3Client.getInstance();

            // original: "GDG1382e56c ~ .jpeg"
            String key = "GDG13" + file.randomName("jpeg");
            // thumbnail: "thumbnail_82e56c ~ .jpeg"
            String thumbnailKey = "thumbnail_" + key.replaceFirst("GDG13", "");

            // ????????? ??????(https://bucket/GDG13 ... )
            imagePath = s3Client.upload(file.inputStream(), file.length(), key, file.getContentType(), null);
            // ????????? ????????? ??????(https://bucket/thumbnail_ ... )
            //thumbnailPath = s3Client.upload(file.inputStream(), file.length(), thumbnailKey, file.getContentType(), null);

            log.info("contentType: " + file.getContentType());
            log.info("imagePath: " + imagePath);

            BufferedImage bufferedImage = Thumbnails.of(new URL(imagePath)).scale(0.4).asBufferedImage(); // ????????? ????????? ??????
            String thumbnailPath2 = s3Client.upload(bufferedImage, thumbnailKey, file.getContentType()); // AWS S3 Upload
            log.info("thumbnailKey: " + thumbnailKey);
            log.info("thumbnailPath2: " + thumbnailPath2);
            mission.setImage(imagePath);
            mission.setThumbnail_image(thumbnailPath2);
        }

        mission.setCertification_date(LocalDateTime.now()); // ?????? ????????? ?????? ??????
        missionRepository.save(mission);
    }

    /**
     * ?????? ?????? ??? Challenge ???????????? missionCount + 1
     * @param challengeId ????????? ????????? Challenge ID
     */
    private void updateChallengeMissionCount(Long challengeId) {
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new EntityNotFoundException("not found challenge to delete. check id of challenge."));

        if (challenge.getMission_count() > 30) {
            throw new IllegalArgumentException("size of complete mission count's max size is 30.");
        }
        challenge.increaseMissionCount();
        challengeRepository.save(challenge);
    }

}
