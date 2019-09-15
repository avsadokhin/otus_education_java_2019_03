package ru.otus.hw16.frontend.front;


import org.springframework.messaging.simp.SimpMessagingTemplate;
import ru.otus.hw16.frontend.entity.User;
import ru.otus.hw16.server.messaging.core.Addressee;

import java.util.List;

public interface FrontService extends Addressee {

    <T extends User> void createUserRequest(T user);
    <T extends User> void getUserCollectionRequest();
    <T extends User> void addUserList(List<T> userList);
    <T extends User> List<T> getUserList();
    SimpMessagingTemplate getMessagingTemplate();

}
