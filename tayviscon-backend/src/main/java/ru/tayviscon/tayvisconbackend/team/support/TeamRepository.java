package ru.tayviscon.tayvisconbackend.team.support;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tayviscon.tayvisconbackend.team.MemberProfile;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<MemberProfile, Long> {


    Optional<MemberProfile> findByGithubId(Long githubId);

    Optional<MemberProfile> findByUsername(String username);

    List<MemberProfile> findByHiddenOrderByNameAsc(boolean hidden);
}
