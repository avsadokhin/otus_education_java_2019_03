package ru.otus.spring.services.frontend;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ru.otus.front.FrontServiceImpl;
import ru.otus.messaging.MessageSystemContext;
import ru.otus.messaging.core.Address;
import ru.otus.messaging.core.MessageSystem;

@Service
public class FrontEndServiceConfig extends FrontServiceImpl {
    public FrontEndServiceConfig(MessageSystemContext context, Address address, SimpMessagingTemplate messagingTemplate) {
        super(context, new Address("FRONT"), messagingTemplate);
    }
}
