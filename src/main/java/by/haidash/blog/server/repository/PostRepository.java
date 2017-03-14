package by.haidash.blog.server.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import by.haidash.blog.server.model.entity.Post;

/**
 * Created by haidash on 28.02.17.
 */
@PreAuthorize("isFullyAuthenticated()")
@RepositoryRestResource(path = "posts")
public interface PostRepository extends CrudRepository<Post, Long> {

    @Override
    @PreAuthorize("(isFullyAuthenticated() and #entity.id==null) or (#entity.createdBy !=null and #entity.createdBy.email == authentication.name)")
    Post save(Post entity);

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    void delete(Long id);

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    void deleteAll();

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    void delete(Iterable<? extends Post> entities);

}
