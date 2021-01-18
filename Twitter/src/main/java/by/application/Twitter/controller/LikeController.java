package by.application.Twitter.controller;

import by.application.Twitter.controller.dataTransferObject.PostDto;
import by.application.Twitter.controller.dataTransferObject.PostMapper;
import by.application.Twitter.model.Post;
import by.application.Twitter.model.User;
import by.application.Twitter.service.LikeService;
import by.application.Twitter.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class LikeController extends BaseController{
    @Autowired
    private LikeService likeService;
}
