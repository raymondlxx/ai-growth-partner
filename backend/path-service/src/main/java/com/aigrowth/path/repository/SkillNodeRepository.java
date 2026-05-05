package com.aigrowth.path.repository;

import com.aigrowth.path.entity.SkillNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SkillNodeRepository extends JpaRepository<SkillNode, Long> {
    List<SkillNode> findByCareerPathIdOrderByStageOrderAsc(Long careerPathId);
}
