package by.application.Twitter.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter@Getter
public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
}
