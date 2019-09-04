package ru.otus.messaging.messages;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import ru.otus.entity.AddressDataSet;
import ru.otus.entity.User;
import ru.otus.front.FrontService;
import ru.otus.messaging.MessageToFrontEnd;
import ru.otus.messaging.core.Address;
import ru.otus.spring.controllers.UserPageController;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

public class MessageGetUserCollectionResponse<T extends User> extends MessageToFrontEnd {
    private final List<T> content;

    public MessageGetUserCollectionResponse(Address from, Address to, List<T> content) {
        super(from, to);
        this.content = content;
        }

    @Override
    public void exec(FrontService frontService) {
        logger.log(Level.INFO, "Get message response (payload=" + content + ")");
        frontService.getMessagingTemplate().convertAndSend("/topic/usersResponse", content);
        //frontService.addUserList(content);
    /*    User user = new User();
        user.setName("Test");
        user.setAge(21);
        AddressDataSet addressDataSet = new AddressDataSet();
        addressDataSet.setStreet("Lomonosov");
        user.setAddress(addressDataSet);
        System.out.println(user);
        messagingTemplate.convertAndSend("/topic/usersResponse", user);*/

    }
}
