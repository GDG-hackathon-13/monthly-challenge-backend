package gdghackathon.monthlychallenge.service;

import gdghackathon.monthlychallenge.aws.AttachedFile;
import gdghackathon.monthlychallenge.aws.S3Client;
import gdghackathon.monthlychallenge.dto.CreateMissionDTO;
import gdghackathon.monthlychallenge.dto.MissionResponseDto;
import gdghackathon.monthlychallenge.entity.Mission;
import gdghackathon.monthlychallenge.repository.MissionRepository;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class MissionService {
    MissionRepository missionRepository;

    MissionService(MissionRepository missionRepository) {
        this.missionRepository = missionRepository;
    }

    public List<MissionResponseDto> getMissions(Long challengeId) {
        Assert.notNull(challengeId, "challengeId must not be null");

        final List<Mission> missionList = missionRepository.findAllByChallengeId(challengeId);
        return missionList.stream()
                .map(Mission::entityToDto)
                .collect(Collectors.toList());
    }

    public void completeMission(CreateMissionDTO createMissionDTO,
                                Long challengeId,
                                Long missionId) throws IOException {
        Assert.notNull(challengeId, "challengeId must not be null");
        Assert.notNull(missionId, "missionId must not be null");

        Mission mission = missionRepository.findByChallengeIdAndId(challengeId, missionId);

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

            // 이미지 경로(https://bucket/GDG13 ... )
            imagePath = s3Client.upload(file.inputStream(), file.length(), key, file.getContentType(), null);

            // 썸네일 이미지 경로(https://bucket/thumbnail_ ... )
            thumbnailPath = s3Client.upload(file.inputStream(), file.length(), thumbnailKey, file.getContentType(), null);

            log.info("contentType: " + file.getContentType());
            log.info("imagePath: " + imagePath);
            log.info("thumbnailPath: " + thumbnailPath);

            BufferedImage bufferedImage = Thumbnails.of(new URL(imagePath)).scale(0.4).asBufferedImage();
            s3Client.upload(bufferedImage, thumbnailKey, file.getContentType());

            mission.setImage(imagePath);
            mission.setThumbnail_image(thumbnailPath);
        }

        missionRepository.save(mission);
    }

}
