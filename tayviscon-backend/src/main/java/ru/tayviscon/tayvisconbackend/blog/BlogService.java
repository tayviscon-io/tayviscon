package ru.tayviscon.tayvisconbackend.blog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tayviscon.tayvisconbackend.support.DateFactory;

import javax.swing.*;
import java.util.Collections;
import java.util.Date;

@Slf4j
@Service
public class BlogService {
    private final PostRepository postRepository;
    private final DateFactory dateFactory;

    public BlogService(PostRepository postRepository, DateFactory dateFactory) {
        this.postRepository = postRepository;
        this.dateFactory = dateFactory;
    }

    public Post getPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));
    }

    public Post getPublishedPost(String publicSLug) {
        Date now = dateFactory.now();
        Post post = postRepository.findByPublicSlugAndDraftFalseAndPublishAtBefore(publicSLug, now);
        if(post == null) {
            post = postRepository.findByPublicSlugAliasesInAndDraftFalseAndPublishAtBefore(
                    Collections.singleton(publicSLug), now);
            if(post != null) {
                throw new PostMovedException(post.getPublicSlug());
            }
            throw new PostNotFoundException(publicSLug);
        }
        return post;

    }

}
