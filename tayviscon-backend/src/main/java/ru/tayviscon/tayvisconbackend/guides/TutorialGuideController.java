package ru.tayviscon.tayvisconbackend.guides;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

@Controller
@RequestMapping("/guides/tutorial")
public class TutorialGuideController {

    private final TutorialGuidesRepository guidesRepository;

    public TutorialGuideController(TutorialGuidesRepository guidesRepository) {
        this.guidesRepository = guidesRepository;
    }

    @GetMapping("/{tutorial}")
    public String viewTutorial(@PathVariable String tutorial, Model model) {
        boolean knownTutorial = Arrays.stream(guidesRepository.findAll()).anyMatch(header -> header.getName().equals(tutorial));
        if(knownTutorial) {
            TutorialGuide  tutorialGuide = guidesRepository.findByName(tutorial);
            model.addAttribute("guide", tutorialGuide);
            model.addAttribute("description", "Это описание туториала");
            return "guides/gs/guide";
        }
        throw new RuntimeException("Не удалось найти туториала [" + tutorial + "]");
    }
}
