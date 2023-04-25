package ru.tayviscon.tayvisconbackend.team;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Entity
public class MemberProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = true)
    private String name;
    @Column(nullable = true)
    private String jobTitle;
    @Column(nullable = true)
    private String location;
    @Column(nullable = true)
    private String bio;
    @Column(nullable = true)
    private String avatarUrl;
    @Column(nullable = true)
    private String githubUsername;
    @Column(nullable = false)
    private String username;
    @Column(nullable = true)
    private Long githubId;
    @Column
    private GeoLocation geoLocation;
    @Column
    private boolean hidden;

    public MemberProfile() {
    }

    public MemberProfile(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return name == null ? getUsername() : name;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getGithubUsername() {
        return githubUsername;
    }

    public void setGithubUsername(String githubUsername) {
        this.githubUsername = githubUsername;
    }

    public boolean hasGithubUsername() {
        return StringUtils.hasText(githubUsername);
    }

    public Long getGithubId() {
        return githubId;
    }

    public void setGithubId(Long githubId) {
        this.githubId = githubId;
    }


    public GeoLocation getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }

    @JsonIgnore
    public TeamLocation getTeamLocation() {
        if(geoLocation == null) {
            return null;
        }
        return new TeamLocation(name, geoLocation.getLatitude(), geoLocation.getLongitude(), getId());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberProfile profile = (MemberProfile) o;
        return hidden == profile.hidden && Objects.equals(id, profile.id) && Objects.equals(name, profile.name) && Objects.equals(jobTitle, profile.jobTitle) && Objects.equals(location, profile.location) && Objects.equals(bio, profile.bio) && Objects.equals(avatarUrl, profile.avatarUrl) && Objects.equals(githubUsername, profile.githubUsername) && Objects.equals(username, profile.username) && Objects.equals(githubId, profile.githubId) && Objects.equals(geoLocation, profile.geoLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, jobTitle, location, bio, avatarUrl, githubUsername, username, githubId, geoLocation, hidden);
    }
}
