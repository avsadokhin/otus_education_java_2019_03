package ru.otus.front;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.entity.User;

@Controller
public class UserPageController {
    private final FrontService frontService;

    public UserPageController(FrontService frontService) {
        this.frontService = frontService;
    }

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @MessageMapping("/user")
    public void createUserRequest(User request) throws InterruptedException {
        Logger logger = LoggerFactory.getLogger(UserPageController.class);
        logger.info("Handling createUserRequest: {}", request);
        frontService.createUserRequest(request);
        getUserListRequest();
    }

    @MessageMapping("/getUsers")
    public  void getUserListRequest(){
        frontService.getUserCollectionRequest();
    }

}
