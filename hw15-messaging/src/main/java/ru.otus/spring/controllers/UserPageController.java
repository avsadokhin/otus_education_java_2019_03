package ru.otus.spring.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.otus.entity.AddressDataSet;
import ru.otus.entity.User;
import ru.otus.front.FrontService;

import java.util.Arrays;
import java.util.List;

@Controller
public class UserPageController {
    private final FrontService frontService;
    /*   private final SimpMessagingTemplate messagingTemplate;


   public UserPageController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
*/

    public UserPageController(FrontService frontService) {
        this.frontService = frontService;
    }

    @MessageMapping("/user")
    public void createUserRequest(User request) throws InterruptedException {
        Logger logger = LoggerFactory.getLogger(UserPageController.class);
        logger.info("Handling request: {}", request);
        System.out.println(request.toString());
       // List<User> userList = getUserList();
        frontService.getUserCollectionRequest();
       /* final var message = messageSystem.createDatabaseMessage(request);
        messageSystem.sendMessage(message);*/
    }

    public /*List<User>*/ void getUserList(){

      /*  User user = new User();
        user.setName("Test");
        user.setAge(21);
        AddressDataSet addressDataSet = new AddressDataSet();
        addressDataSet.setStreet("Lomonosov");
        user.setAddress(addressDataSet);
        System.out.println(user);
        messagingTemplate.convertAndSend("/topic/usersResponse", user);
        return  Arrays.asList(user);*/
      frontService.getMessagingTemplate().convertAndSend("/topic/usersResponse", frontService.getUserList());
    }

}
