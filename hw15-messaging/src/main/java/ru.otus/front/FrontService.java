package ru.otus.front;


import org.springframework.messaging.simp.SimpMessagingTemplate;
import ru.otus.entity.User;
import ru.otus.messaging.core.Addressee;

import java.util.Collection;
import java.util.List;

public interface FrontService extends Addressee {

    <T extends User> void createUserRequest(T user);
    <T extends User> void getUserCollectionRequest();
    <T extends User> void addUserList(List<T> userList);
    <T extends User> List<T> getUserList();
    SimpMessagingTemplate getMessagingTemplate();

}
