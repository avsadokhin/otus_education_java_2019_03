package ru.otus.hw15.config;


import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ru.otus.hw15.front.FrontServiceImpl;
import ru.otus.hw15.messaging.MessageSystemContext;
import ru.otus.hw15.messaging.core.Address;

@Service
public class FrontEndServiceConfig extends FrontServiceImpl {
    public FrontEndServiceConfig(MessageSystemContext context, Address address, SimpMessagingTemplate messagingTemplate) {
        super(context, new Address("FRONT"), messagingTemplate);
    }


}
