package by.application.Twitter.controller.dataTransferObject;

import by.application.Twitter.model.LoginDetails;
import org.mapstruct.Mapper;

@Mapper
public interface LoginDetailsMapper {
    static LoginDetails toLoginDetails(LoginDetailsDto loginDetailsDto) {
        LoginDetails loginDetails = new LoginDetails();
        loginDetails.setUsername(loginDetailsDto.getUsername());
        loginDetails.setPassword(loginDetailsDto.getPassword());
        return loginDetails;
    }
}
