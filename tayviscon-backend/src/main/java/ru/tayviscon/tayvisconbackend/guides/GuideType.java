package ru.tayviscon.tayvisconbackend.guides;

import java.util.Arrays;

public enum GuideType {
    GETTING_STARTED("getting-started", "gs-"),
    TUTORIAL("tutorial","tut-"),
    TOPICAL("topical", "top-"),
    UNKNOWN("unknown", "");

    private final String slug;
    private final String prefix;

    GuideType(String slug, String prefix) {
        this.slug = slug;
        this.prefix = prefix;
    }

    public static GuideType fromSlug(String slug) {
        return Arrays.stream(GuideType.values())
                .filter(guideType -> guideType.getSlug().equals(slug))
                .findFirst().orElse(GuideType.UNKNOWN);
    }

    public static GuideType fromRepositoryName(String repositoryName) {
        return Arrays.stream(GuideType.values())
                .filter(guideType -> repositoryName.startsWith(guideType.prefix))
                .findFirst().orElse(GuideType.UNKNOWN);
    }

    public String stripPrefix(String repositoryName) {
        return repositoryName.replaceFirst(prefix, "");
    }

    public String getSlug() {
        return slug;
    }

    public String getPrefix() {
        return prefix;
    }

    @Override
    public String toString() {
        return slug;
    }
}
