package by.haidash.blog.server.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import by.haidash.blog.server.model.entity.Comment;

/**
 * Created by haidash on 28.02.17.
 */
@PreAuthorize("isFullyAuthenticated()")
@RepositoryRestResource(path = "comments")
public interface CommentRepository extends CrudRepository<Comment, Long> {

    @Override
    @PreAuthorize("(isFullyAuthenticated() and #entity.id==null) or (#entity.createdBy !=null and #entity.createdBy.email == authentication.name)")
    Comment save(Comment entity);

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    void delete(Long id);

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    void deleteAll();

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    void delete(Iterable<? extends Comment> entities);
}
