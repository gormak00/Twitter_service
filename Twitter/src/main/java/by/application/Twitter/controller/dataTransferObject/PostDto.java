package by.application.Twitter.controller.dataTransferObject;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class PostDto {

    @NotNull
    @Size(min = 1, max = 255, message = "Post is too short (min = 1) or too long (max = 255)")
    String data;
}
