package ru.tayviscon.tayvisconbackend.guides;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;

@Controller
public class GuideIndexController {

    private final GettingStartedGuidesRepository gsGuidesRepository;
    private final TopicalGuidesRepository topicalGuidesRepository;
    private final TutorialGuidesRepository tutorialGuidesRepository;

    public GuideIndexController(GettingStartedGuidesRepository gsGuidesRepository,
                                TopicalGuidesRepository topicalGuidesRepository,
                                TutorialGuidesRepository tutorialGuidesRepository) {
        this.gsGuidesRepository = gsGuidesRepository;
        this.topicalGuidesRepository = topicalGuidesRepository;
        this.tutorialGuidesRepository = tutorialGuidesRepository;
    }

    @GetMapping("/guides")
    public String viewGuideIndex(Model model) {
        model.addAttribute("gsGuides", Arrays.asList(gsGuidesRepository.findAll()));
        model.addAttribute("topicalGuides",Arrays.asList(topicalGuidesRepository.findAll()));
        model.addAttribute("tutorialGuides",Arrays.asList(tutorialGuidesRepository.findAll()));
        return "guides/index";
    }
}
