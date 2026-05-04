package com.aigrowth.path.repository;

import com.aigrowth.path.entity.UserSkillProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserSkillProgressRepository extends JpaRepository<UserSkillProgress, Long> {
    List<UserSkillProgress> findByUserIdAndPathId(Long userId, Long pathId);
    Optional<UserSkillProgress> findByUserIdAndSkillId(Long userId, Long skillId);
}
