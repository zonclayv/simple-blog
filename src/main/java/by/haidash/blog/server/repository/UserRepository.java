package by.haidash.blog.server.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Optional;

import by.haidash.blog.server.model.entity.User;


/**
 * Created by haidash on 28.02.17.
 */
@RepositoryRestResource(path = "users")
public interface UserRepository extends CrudRepository<User, Long> {

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    Iterable<User> findAll();

    @Override
    @PreAuthorize("(#entity.id==null) or hasRole('ADMIN')")
    User save(User entity);

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    User findOne(Long id);

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    boolean exists(Long id);

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    long count();

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    void delete(Long id);

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    void delete(User entity);

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    void deleteAll();

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    Iterable<User> findAll(Iterable<Long> longs);

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    void delete(Iterable<? extends User> entities);

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    <S extends User> Iterable<S> save(Iterable<S> entities);
}
