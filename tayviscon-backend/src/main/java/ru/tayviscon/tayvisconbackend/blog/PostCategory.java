package ru.tayviscon.tayvisconbackend.blog;

/**
 * Поддерживаемые категрии постов в блоге.
 */
public enum PostCategory {

    ENGINEERING("Инжинириг", "engineering"),
    RELEASES("Релизы", "releases"),
    NEWS_AND_EVENTS("Новости", "news");

    private String displayName;
    private String urlSlug;

    PostCategory(String displayName, String urlSlug) {
        this.displayName = displayName;
        this.urlSlug = urlSlug;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUrlSlug() {
        return urlSlug;
    }

    public String getId() {
        return name();
    }

    @Override
    public String toString() {
        return getDisplayName();
    }
}
