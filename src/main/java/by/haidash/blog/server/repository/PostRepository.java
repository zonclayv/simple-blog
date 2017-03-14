package by.haidash.blog.server.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PostAuthorize;

import by.haidash.blog.server.model.entity.Post;

/**
 * Created by haidash on 28.02.17.
 */
@PostAuthorize("isFullyAuthenticated()")
public interface PostRepository extends CrudRepository<Post, Long> {

    @Override
    Iterable<Post> findAll();

    @Override
    Post findOne(Long id);

    @Override
    Post save(Post entity);

    @Override
    void delete(Long id);

}
