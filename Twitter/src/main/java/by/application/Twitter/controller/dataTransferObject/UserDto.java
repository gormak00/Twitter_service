package by.application.Twitter.controller.dataTransferObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@AllArgsConstructor
@Getter@Setter
public class UserDto {

    @NotNull(message = "Empty username field")
    @Size(message = "username is too long (max = 25)", max = 25)
    private String username;
    @NotNull(message = "Empty password field")
    @Size(message = "password is too short (min = 8) or too long (max = 25)", min = 8, max = 20)
    private String password;
    @NotNull(message = "Empty firstName field")
    @Size(message = "firstName is too long (max = 25)", max = 25)
    private String firstName;
    @NotNull(message = "Empty lastName field")
    @Size(message = "lastName is too long (max = 25)", max = 25)
    private String lastName;
    @NotNull(message = "Empty email field")
    @Email(message = "it's not email")
    @Size(message = "email is too long (max = 25)", max = 50)
    private String email;
}
