package ru.tayviscon.tayvisconbackend.blog;

import jakarta.persistence.*;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Post {

    private final static SimpleDateFormat SLUG_DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PostCategory category;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PostFormat format;
    @Column(nullable = false)
    private String rawContent;
    @Column(nullable = false)
    private String renderedContent;
    @Column(nullable = false)
    private Date createdAt = new Date();
    @Column(nullable = false)
    private boolean draft = true;
    @Column(nullable = false)
    private boolean broadcast = false;
    @Column(nullable = true)
    private Date publishAt;
    @Column(nullable = true)
    private String publicSlug;
    @ElementCollection
    private Set<String> publicSlugAliases = new HashSet<>();

    public Post() {
    }

    public Post(String title, String content, PostCategory category, PostFormat format) {
        this.title = title;
        this.rawContent = content;
        this.category = category;
        this.format = format;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getRawContent() {
        return rawContent;
    }

    public void setRawContent(String rawContent) {
        this.rawContent = rawContent;
    }

    public String getRenderedContent() {
        return renderedContent;
    }

    public void setRenderedContent(String renderedContent) {
        this.renderedContent = renderedContent;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isDraft() {
        return draft;
    }

    public void setDraft(boolean draft) {
        this.draft = draft;
    }

    public boolean isBroadcast() {
        return broadcast;
    }

    public void setBroadcast(boolean broadcast) {
        this.broadcast = broadcast;
    }

    public Date getPublishAt() {
        return publishAt;
    }

    public void setPublishAt(Date publishAt) {
        this.publishAt = publishAt;
    }

    public String getPublicSlug() {
        return publicSlug;
    }

    public void addPublicSlug(String alias) {
        if(alias !=null) {
            this.publicSlugAliases.add(alias);
        }
    }

    public boolean isScheduled() {
        return publishAt == null;
    }

    public boolean isLivedOn(Date date) {
        return !(isDraft() || publishAt.after(date));
    }

    public String  getAdminSlug() {
        return String.format("%s-%s", getId(),getSlug());
    }

    private String generatePublishSlug() {
        return String.format("%s/%s", SLUG_DATE_FORMAT.format(getPublishAt()), getSlug());
    }

    private String getSlug() {
        if(title == null) {
            return "";
        }

        String cleanedTitle = title.toLowerCase().replace("\n",  " ").replaceAll("[^a-z\\d\\s]", " ");
        return StringUtils.arrayToDelimitedString(StringUtils.tokenizeToStringArray(cleanedTitle, " "), "-");
    }



}
