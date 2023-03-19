package ru.tayviscon.tayvisconbackend.guides;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

@Controller
@RequestMapping("/guides/gs")
public class GettingStartedGuideController {

    private final GettingStartedGuidesRepository guidesRepository;

    public GettingStartedGuideController(GettingStartedGuidesRepository guidesRepository) {
        this.guidesRepository = guidesRepository;
    }

    @GetMapping("/{guide}")
    public String viewGuide(@PathVariable String guide, Model model) {
        boolean knownGuide = Arrays.stream(guidesRepository.findAll()).anyMatch(header -> header.getName().equals(guide));
        if (knownGuide) {
            GettingStartedGuide gsGuide = guidesRepository.findByName(guide);
            model.addAttribute("guide", gsGuide);
            model.addAttribute("description", "Это описание руководства");
            return "guides/gs/guide";
        }
        throw new RuntimeException("Не удалось найти руководство [" + guide + "]");
    }
}
