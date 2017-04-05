package by.haidash.blog.server.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import by.haidash.blog.server.model.entity.User;

/**
 * Created by haidash on 04.04.17.
 */
@Repository
public interface InnerUserRepository extends CrudRepository<User, Long> {
    Optional<User> findOneByEmail(@Param("email") String email);
    Optional<User> findOneByUsername(@Param("username") String username);
}
