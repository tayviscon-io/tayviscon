package ru.tayviscon.tayvisconbackend.blog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.tayviscon.tayvisconbackend.support.DateFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public final class PostView {
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMMM dd, yyyy");

    private final Post post;

    private final DateFactory dateFactory;

    private PostView(Post post, DateFactory dateFactory) {
        this.post = post;
        this.dateFactory = dateFactory;
    }

    public static PostView of(Post post, DateFactory dateFactory) {
        return new PostView(post, dateFactory);
    }

    public static Page<PostView> pageOf(Page<Post> posts, DateFactory dateFactory) {
        List<PostView> postViews = posts.getContent().stream()
                .map(post -> of(post, dateFactory))
                .collect(Collectors.toList());
        PageRequest pageRequest = PageRequest.of(posts.getNumber(), posts.getSize(), posts.getSort());
        return new PageImpl<>(postViews, pageRequest, posts.getTotalPages());
    }

    public String getFormattedPublishDate() {
        return post.isScheduled() ? "Unscheduled" : DATE_FORMAT.format(post.getPublishAt());
    }

    public String getPath() {
        String path;
        if (post.isLivedOn(dateFactory.now())) {
            path = "/blog/" + post.getPublicSlug();
        } else {
            path = "/admin/blog/" + post.getAdminSlug();
        }
        return path;
    }

    public String getTitle() {
        return post.getTitle();
    }

    public boolean isScheduled() {
        return post.isScheduled();
    }

    public boolean isDraft() {
        return post.isDraft();
    }

    public PostCategory getCategory() {
        return post.getCategory();
    }

    public boolean isBroadcast() {
        return post.isBroadcast();
    }

    public String getRenderedContent() {
        return post.getRenderedContent();
    }

    public Date getPublishedAt() {
        return post.getPublishAt();
    }

    public Date getCreatedAt() {
        return post.getCreatedAt();
    }

    public Long getId() {
        return post.getId();
    }

    public String getEditPath() {
        return getUpdatePath() + "/edit";
    }
    public String getUpdatePath() {
        return "admin/blog/" + post.getAdminSlug();
    }

}
