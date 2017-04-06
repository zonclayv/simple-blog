package by.haidash.blog.server.model.dto;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by haidash on 06.04.17.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewUserForm {

    private String firstname;

    private String lastname;

    private String username;

    @Email
    @NotNull
    private String email;

    @NotNull
    private String password;
}
