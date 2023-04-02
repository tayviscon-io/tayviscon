package ru.tayviscon.tayvisconbackend.blog;

public class PostMovedException extends RuntimeException {

    private String publicSlug;

    public PostMovedException(String publicSlug) {
        this.publicSlug = publicSlug;
    }

    public String getPublicSlug() {
        return publicSlug;
    }
}


