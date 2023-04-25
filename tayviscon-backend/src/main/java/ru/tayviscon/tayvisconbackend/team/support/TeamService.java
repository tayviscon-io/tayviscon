package ru.tayviscon.tayvisconbackend.team.support;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import ru.tayviscon.tayvisconbackend.team.MemberProfile;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Optional<MemberProfile> findMemberProfile(Long id) {
        return this.teamRepository.findByGithubId(id);
    }

    public Optional<MemberProfile> findMemberProfile(String username) {
        return this.teamRepository.findByUsername(username);
    }

    public Optional<MemberProfile> updateMemberProfile(Long id, MemberProfile newProfile) {
        return findMemberProfile(id).map(profile -> updateMemberProfile(newProfile, profile));
    }

    public  Optional<MemberProfile> updateMemberProfile(String username, MemberProfile updatedMember) {
        return findMemberProfile(username).map(profile -> updateMemberProfile(updatedMember, profile));
    }

    private MemberProfile updateMemberProfile(MemberProfile profile, MemberProfile existingProfile) {
        existingProfile.setBio(profile.getBio());
        existingProfile.setName(profile.getName());
        existingProfile.setJobTitle(profile.getJobTitle());
        existingProfile.setLocation(profile.getLocation());
        existingProfile.setGeoLocation(profile.getGeoLocation());
        existingProfile.setHidden(profile.isHidden());

        return this.teamRepository.save(existingProfile);
    }

    public List<MemberProfile> findActiveMembers() {
        return this.teamRepository.findByHiddenOrderByNameAsc(false);
    }

    public List<MemberProfile> findHiddenMembers() {
        return this.teamRepository.findByHiddenOrderByNameAsc(true);
    }

    public MemberProfile createOrUpdateMemberProfile(Long githubId, OAuth2User oAuth2User) {
        MemberProfile profile = teamRepository.findByGithubId(githubId).orElseGet(()-> {
            MemberProfile newProfile = new MemberProfile();
            newProfile.setGithubId(githubId);
            newProfile.setHidden(true);
            return newProfile;
        });
        profile.setUsername(oAuth2User.getAttribute("login"));
        profile.setGithubUsername(oAuth2User.getAttribute("login"));
        profile.setName(oAuth2User.getAttribute("name"));
        profile.setAvatarUrl(oAuth2User.getAttribute("avatar_url"));
        return this.teamRepository.save(profile);
    }



}
