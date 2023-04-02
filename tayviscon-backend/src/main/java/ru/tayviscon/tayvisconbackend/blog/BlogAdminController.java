package ru.tayviscon.tayvisconbackend.blog;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.tayviscon.tayvisconbackend.support.DateFactory;

@Controller
@RequestMapping("/admin/blog")
public class BlogAdminController {

    private final BlogService blogService;
    private final DateFactory dateFactory;

    public BlogAdminController(BlogService blogService, DateFactory dateFactory) {
        this.blogService = blogService;
        this.dateFactory = dateFactory;
    }


    @GetMapping("/new")
    public String newPost(Model model) {
        model.addAttribute("postForm", new PostForm());
        model.addAttribute("categories", PostCategory.values());
        model.addAttribute("format", PostFormat.values());
        return "admin/blog/new";
    }

}
