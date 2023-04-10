package ru.tayviscon.tayvisconbackend.blog;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(Long id) {
        super("Не удалось найти пост с id:  [" + id + "]");
    }

    public PostNotFoundException(String slug) {
        super("Не удалось найти пост с заданным slug: [" + slug + "]");
    }
}
