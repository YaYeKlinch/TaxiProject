package ua.project.controller.dto;


import lombok.Getter;
import lombok.Setter;
import ua.project.controller.dto.utility.EmailValidation;
import ua.project.controller.dto.utility.PasswordMatches;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Getter
@Setter
@PasswordMatches
public class UserDto {
    @NotEmpty
    @Size(min=2, max=30)
    private String firstName;

    @NotBlank
    @Size(min=2, max=30)
    private String lastName;

    @NotBlank
    @Size(min=2, max=30)
    private String password;
    private String matchingPassword;

    @NotBlank
    @EmailValidation
    private String email;

    public UserDto() {
    }

    public UserDto(@NotEmpty @Size(min = 2, max = 30) String firstName, @NotBlank @Size(min = 2, max = 30) String lastName, @NotBlank @Size(min = 2, max = 30) String password, String matchingPassword, @NotBlank String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.matchingPassword = matchingPassword;
        this.email = email;
    }
}