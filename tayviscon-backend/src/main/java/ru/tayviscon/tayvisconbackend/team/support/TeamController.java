package ru.tayviscon.tayvisconbackend.team.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.tayviscon.tayvisconbackend.team.MemberProfile;
import ru.tayviscon.tayvisconbackend.team.TeamLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.HEAD;

@Controller
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @RequestMapping(method = { GET, HEAD })
    public String showTeam(Model model) {

        List<MemberProfile> profiles = teamService.findActiveMembers();
        model.addAttribute("profiles", profiles);
        model.addAttribute("teamLocations", profiles.stream()
                .filter(profile -> profile.getTeamLocation() != null)
                .map(MemberProfile::getTeamLocation)
                .collect(Collectors.toList()));
        return "team/index";
    }

    @RequestMapping(value = "/{username}", method = { GET, HEAD })
    public String showProfile(@PathVariable String username, Model model) {
        MemberProfile profile = teamService.findMemberProfile(username)
                .orElseThrow(()->new MemberNotFoundException(username));
        if(profile.isHidden()) {
            throw new MemberNotFoundException("Профиль участника для имени пользователя '%s' скрыт", username);
        }
        model.addAttribute("profile", profile);

        List<TeamLocation> teamLocations = new ArrayList<>();
        if(profile.getTeamLocation() != null) {
            teamLocations.add(profile.getTeamLocation());
        }
        model.addAttribute("teamLocations", teamLocations);
        return "team/show";
    }
}
