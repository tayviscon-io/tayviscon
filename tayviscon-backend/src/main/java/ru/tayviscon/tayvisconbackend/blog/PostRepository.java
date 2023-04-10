package ru.tayviscon.tayvisconbackend.blog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByDraftTrue(Pageable pageRequest);

    Page<Post> findByDraftFalseAndPublishAtBeforeOrderByPublishAtDesc(Date publishedBefore, Pageable pageRequest);

    List<Post> findByDraftFalseAndPublishAtBeforeOrderByPublishAtDesc(Date publishedBefore);

    Page<Post> findByDraftFalseAndPublishAtAfter(Date now, Pageable pageRequest);

    Post findByPublicSlugAndDraftFalseAndPublishAtBefore(String publicSLug, Date now);

    Post findByPublicSlugAliasesInAndDraftFalseAndPublishAtBefore(Set<String> publicSlugAlias, Date now);

}
