package com.aigrowth.path.service;

import com.aigrowth.path.dto.CareerPathDTO;
import com.aigrowth.path.dto.SkillNodeDTO;
import com.aigrowth.path.dto.UserPathProgressDTO;
import com.aigrowth.path.entity.CareerPath;
import com.aigrowth.path.entity.SkillNode;
import com.aigrowth.path.entity.UserPathProgress;
import com.aigrowth.path.entity.UserSkillProgress;
import com.aigrowth.path.repository.*;
import com.aigrowth.common.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PathService {

    private final CareerPathRepository careerPathRepository;
    private final SkillNodeRepository skillNodeRepository;
    private final UserPathProgressRepository userPathProgressRepository;
    private final UserSkillProgressRepository userSkillProgressRepository;

    public PathService(
            CareerPathRepository careerPathRepository,
            SkillNodeRepository skillNodeRepository,
            UserPathProgressRepository userPathProgressRepository,
            UserSkillProgressRepository userSkillProgressRepository) {
        this.careerPathRepository = careerPathRepository;
        this.skillNodeRepository = skillNodeRepository;
        this.userPathProgressRepository = userPathProgressRepository;
        this.userSkillProgressRepository = userSkillProgressRepository;
    }

    public List<CareerPathDTO> getAllActivePaths() {
        return careerPathRepository.findByIsActiveTrue()
                .stream()
                .map(this::toCareerPathDTO)
                .collect(Collectors.toList());
    }

    public CareerPathDTO getPathById(Long id) {
        CareerPath path = careerPathRepository.findById(id)
                .orElseThrow(() -> new BusinessException(404, "Career path not found"));
        return toCareerPathDTO(path);
    }

    public CareerPathDTO getPathByIdWithUserProgress(Long pathId, Long userId) {
        CareerPath path = careerPathRepository.findById(pathId)
                .orElseThrow(() -> new BusinessException(404, "Career path not found"));
        CareerPathDTO dto = toCareerPathDTO(path);
        List<SkillNode> nodes = skillNodeRepository.findByPathIdOrderByStageOrderAsc(pathId);
        List<SkillNodeDTO> nodeDTOs = nodes.stream().map(node -> {
            SkillNodeDTO nodeDTO = toSkillNodeDTO(node);
            userSkillProgressRepository.findByUserIdAndSkillId(userId, node.getId())
                    .ifExists(userProg -> nodeDTO.setUserCompleted(userProg.getCompleted()));
            return nodeDTO;
        }).collect(Collectors.toList());
        dto.setSkillNodes(nodeDTOs);
        return dto;
    }

    public CareerPathDTO createPath(CareerPathDTO dto) {
        CareerPath path = new CareerPath();
        path.setTitle(dto.getTitle());
        path.setDescription(dto.getDescription());
        path.setCategory(dto.getCategory());
        path.setDifficulty(dto.getDifficulty());
        path.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
        path = careerPathRepository.save(path);
        return toCareerPathDTO(path);
    }

    public UserPathProgressDTO startPath(Long userId, Long pathId) {
        CareerPath path = careerPathRepository.findById(pathId)
                .orElseThrow(() -> new BusinessException(404, "Career path not found"));

        UserPathProgress progress = userPathProgressRepository.findByUserIdAndPathId(userId, pathId)
                .orElseGet(() -> {
                    UserPathProgress p = new UserPathProgress();
                    p.setUserId(userId);
                    p.setPathId(pathId);
                    p.setStatus("in_progress");
                    p.setProgressPercent(0);
                    return userPathProgressRepository.save(p);
                });

        if ("not_started".equals(progress.getStatus())) {
            progress.setStatus("in_progress");
            userPathProgressRepository.save(progress);
        }

        return toProgressDTO(progress);
    }

    public UserPathProgressDTO completeSkill(Long userId, Long pathId, Long skillId) {
        SkillNode node = skillNodeRepository.findById(skillId)
                .orElseThrow(() -> new BusinessException(404, "Skill node not found"));

        UserSkillProgress userSkill = userSkillProgressRepository.findByUserIdAndSkillId(userId, skillId)
                .orElseGet(() -> {
                    UserSkillProgress usp = new UserSkillProgress();
                    usp.setUserId(userId);
                    usp.setSkillId(skillId);
                    usp.setPathId(pathId);
                    usp.setCompleted(false);
                    return usp;
                });

        userSkill.setCompleted(true);
        userSkillProgressRepository.save(userSkill);

        List<SkillNode> allNodes = skillNodeRepository.findByPathIdOrderByStageOrderAsc(pathId);
        List<UserSkillProgress> userProgress = userSkillProgressRepository.findByUserIdAndPathId(userId, pathId);
        long completedCount = userProgress.stream().filter(UserSkillProgress::getCompleted).count();
        int percent = (int) ((completedCount * 100) / allNodes.size());

        UserPathProgress pathProgress = userPathProgressRepository.findByUserIdAndPathId(userId, pathId)
                .orElseGet(() -> {
                    UserPathProgress p = new UserPathProgress();
                    p.setUserId(userId);
                    p.setPathId(pathId);
                    p.setStatus("in_progress");
                    p.setProgressPercent(0);
                    return p;
                });

        pathProgress.setProgressPercent(percent);
        if (percent >= 100) {
            pathProgress.setStatus("completed");
        }
        userPathProgressRepository.save(pathProgress);

        return toProgressDTO(pathProgress);
    }

    private CareerPathDTO toCareerPathDTO(CareerPath path) {
        CareerPathDTO dto = new CareerPathDTO(
                path.getId(),
                path.getTitle(),
                path.getDescription(),
                path.getCategory(),
                path.getDifficulty(),
                path.getIsActive()
        );
        List<SkillNodeDTO> nodes = path.getSkillNodes().stream()
                .map(this::toSkillNodeDTO)
                .collect(Collectors.toList());
        dto.setSkillNodes(nodes);
        return dto;
    }

    private SkillNodeDTO toSkillNodeDTO(SkillNode node) {
        return new SkillNodeDTO(
                node.getId(),
                node.getTitle(),
                node.getDescription(),
                node.getStageOrder(),
                node.getSkillType(),
                node.getEstimatedDays(),
                node.getIsRequired()
        );
    }

    private UserPathProgressDTO toProgressDTO(UserPathProgress progress) {
        return new UserPathProgressDTO(
                progress.getId(),
                progress.getUserId(),
                progress.getPathId(),
                progress.getStatus(),
                progress.getProgressPercent()
        );
    }
}
