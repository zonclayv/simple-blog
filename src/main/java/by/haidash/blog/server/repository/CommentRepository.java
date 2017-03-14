package by.haidash.blog.server.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import by.haidash.blog.server.model.entity.Comment;

/**
 * Created by haidash on 28.02.17.
 */
@RepositoryRestResource(path = "comments")
public interface CommentRepository extends CrudRepository<Comment, Long> {

    @Override
    Iterable<Comment> findAll();

    @Override
    Comment findOne(Long aLong);

    @Override
    Comment save(Comment entity);

    @Override
    void delete(Long id);
}
