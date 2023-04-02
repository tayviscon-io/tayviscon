package ru.tayviscon.tayvisconbackend.blog;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.tayviscon.tayvisconbackend.support.DateFactory;

@Controller
@RequestMapping("/blog")
public class BlogController {

    private final BlogService blogService;
    private final DateFactory dateFactory;

    public BlogController(BlogService blogService, DateFactory dateFactory) {
        this.blogService = blogService;
        this.dateFactory = dateFactory;
    }

    @GetMapping("/{year:\\d+}/{month:\\d+}/{day:\\d+}/{slug}")
    public String showPost(@PathVariable String year, @PathVariable String month, @PathVariable String day,
                           @PathVariable String slug, Model model) {
        String publicSlug = String .format("%s/%s/%s/%s", year, month, day, slug);
        Post post = blogService.getPublishedPost(publicSlug);
        model.addAttribute("post", PostView.of(post, dateFactory));
        model.addAttribute("categories", PostCategory.values());
        model.addAttribute("activeCategory", post.getCategory().getDisplayName());
        return "blog/show";
    }

}
