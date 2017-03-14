package by.haidash.blog.server.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;

import by.haidash.blog.server.model.entity.User;


/**
 * Created by haidash on 28.02.17.
 */
@RepositoryRestResource(path = "users")
public interface UserRepository extends CrudRepository<User, Long> {

    @Override
    Iterable<User> findAll();

    @Override
    User save(User entity);

    @RestResource(exported = false)
    Optional<User> findOneByEmail(@Param("email") String email);

    @RestResource(exported = false)
    Optional<User> findOneByUsername(@Param("username") String username);

}
