package by.application.Twitter.controller.dataTransferObject;

import by.application.Twitter.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    static User toUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        return user;
    }
}
