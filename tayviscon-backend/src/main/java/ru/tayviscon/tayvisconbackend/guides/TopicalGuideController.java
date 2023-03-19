package ru.tayviscon.tayvisconbackend.guides;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

@Controller
@RequestMapping("/guides/topicals")
public class TopicalGuideController {

    private final TopicalGuidesRepository guidesRepository;

    public TopicalGuideController(TopicalGuidesRepository guidesRepository) {
        this.guidesRepository = guidesRepository;
    }

    @GetMapping("/{topical}")
    public String  viewTopical(@PathVariable String topical, Model model) {
        boolean knownTopical = Arrays.stream(guidesRepository.findAll()).anyMatch(header -> header.getName().equals(topical));
        if(knownTopical) {
            TopicalGuide topicalGuide = guidesRepository.findByName(topical);
            model.addAttribute("guide", topicalGuide);
            model.addAttribute("description", "Это описание руководства типа Topical");
            return "guides/gs/guide";
        }
        throw new RuntimeException("Не удалось найти руководство типа Topical [" + topical + "]");
    }
}
