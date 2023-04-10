package ru.tayviscon.tayvisconbackend.blog;

import org.springframework.stereotype.Service;
import ru.tayviscon.tayvisconbackend.support.DateFactory;

import java.util.Date;

@Service
public class PostFormAdapter {
    private static final int SUMMARY_LENGTH = 500;
    private final PostContentRenderer renderer;
    private final DateFactory dateFactory;
    private final PostSummary postSummary;

    public PostFormAdapter(PostContentRenderer renderer, DateFactory dateFactory, PostSummary postSummary) {
        this.renderer = renderer;
        this.dateFactory = dateFactory;
        this.postSummary = postSummary;
    }

    public Post createPostFromPostForm(PostForm  postForm) {
        String content = postForm.getContent();
        Post post = new Post(postForm.getTitle(), content, postForm.getCategory(), postForm.getFormat());
        post.setCreatedAt(createdDate(postForm, dateFactory.now()));
        setPostProperties(postForm, content, post);
        return post;
    }

    public void updatePostFromPostForm(Post post, PostForm postForm) {
        String content = postForm.getContent();
        post.setTitle(postForm.getTitle());
        post.setRawContent(content);
        post.setCategory(postForm.getCategory());
        post.setFormat(postForm.getFormat());
        post.setCreatedAt(createdDate(postForm, post.getCreatedAt()));
        setPostProperties(postForm, content, post);
    }

    private Date createdDate(PostForm postForm, Date defaultDate) {
        Date createdAt = postForm.getCreatedAt();
        if(createdAt == null) {
            createdAt = defaultDate;
        }
        return createdAt;
    }

    private void setPostProperties(PostForm postForm, String content, Post post) {
        String rendered = null;
        rendered = renderer.render(content, post.getFormat());
        post.setRenderedContent(rendered);
        summarize(post);
        post.setBroadcast(postForm.isBroadcast());
        post.setDraft(post.isScheduled());
        post.setPublishAt(publishDate(postForm));
    }

    private Date publishDate(PostForm postForm) {
        if(! postForm.isDraft() && postForm.getPublishAt() == null) {
            return dateFactory.now();
        } else {
            return postForm.getPublishAt();
        }
    }

    public void summarize(Post post) {
        String summary = postSummary.forContent(post.getRenderedContent(), SUMMARY_LENGTH);
        post.setRenderedSummary(summary);
    }

    public void refreshPost(Post post) {
        post.setRenderedContent(renderer.render(post.getRawContent(), post.getFormat()));
        summarize(post);
    }

}
