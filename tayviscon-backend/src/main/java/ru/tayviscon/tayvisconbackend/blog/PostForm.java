package ru.tayviscon.tayvisconbackend.blog;

import java.util.Date;

public class PostForm {

    private String title;
    private String content;
    private PostCategory category;
    private PostFormat format;
    private boolean broadcast;
    private boolean draft;
    private Date publishAt;
    private Date createdAt;

    public PostForm() {
    }

    public PostForm(Post post) {
        this.title = post.getTitle();
        this.content = post.getRawContent();
        this.category = post.getCategory();
        this.broadcast = post.isBroadcast();
        this.draft = post.isDraft();
        this.publishAt = post.getPublishAt();
        this.format = post.getFormat();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public PostCategory getCategory() {
        return category;
    }

    public void setCategory(PostCategory category) {
        this.category = category;
    }

    public PostFormat getFormat() {
        return format;
    }

    public void setFormat(PostFormat format) {
        this.format = format;
    }

    public boolean isBroadcast() {
        return broadcast;
    }

    public void setBroadcast(boolean broadcast) {
        this.broadcast = broadcast;
    }

    public boolean isDraft() {
        return draft;
    }

    public void setDraft(boolean draft) {
        this.draft = draft;
    }

    public Date getPublishAt() {
        return publishAt;
    }

    public void setPublishAt(Date publishAt) {
        this.publishAt = publishAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
