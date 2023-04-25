package ru.tayviscon.tayvisconbackend.team.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.tayviscon.tayvisconbackend.team.MemberProfile;

import java.security.Principal;

@Controller
public class TeamAdminController {

    private final TeamService teamService;

    @Autowired
    public TeamAdminController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/admin/team")
    public String getTeamAdminPage(Model model) {
        model.addAttribute("activeMembers", teamService.findActiveMembers());
        model.addAttribute("hiddenMembers", teamService.findHiddenMembers());
        return "admin/team/index";
    }

    @GetMapping("/admin/profile")
    public String editProfileForm(Principal principal, Model model) {
        MemberProfile profile = this.teamService.findMemberProfile(Long.parseLong(principal.getName()))
                .orElseThrow(()-> new MemberNotFoundException(principal.getName()));
        model.addAttribute("profile", profile);
        model.addAttribute("formAction", "/admin/profile");
        return "admin/team/edit";
    }

    @GetMapping("/admin/team/{username}")
    public String editTeamMemberForm(@PathVariable("username") String username, Model model) {
        MemberProfile profile = this.teamService.findMemberProfile(username)
                .orElseThrow(()-> new MemberNotFoundException(username));
        model.addAttribute("profile", profile);
        model.addAttribute("formAction", "/admin/team/" + username);
        return "admin/team/edit";
    }

    @PostMapping("/admin/profile")
    public String saveProfile(Principal principal, MemberProfile profile) {
        teamService.updateMemberProfile(Long.parseLong(principal.getName()), profile);
        return "redirect:/admin/profile";
    }

    @PostMapping("/admin/team/{username}")
    public String saveTeamMember(@PathVariable("username") String username, MemberProfile profile) {
        teamService.updateMemberProfile(username, profile);
        return "redirect:/admin/team/" +  username;
    }

}
