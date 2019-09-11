package ru.otus.hw16.frontend.config;


import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ru.otus.hw16.frontend.front.FrontServiceImpl;
import ru.otus.hw16.frontend.messaging.MessageSystemContext;
import ru.otus.hw16.frontend.messaging.core.Address;

@Service
public class FrontEndServiceConfig extends FrontServiceImpl {
    public FrontEndServiceConfig(MessageSystemContext context, Address address, SimpMessagingTemplate messagingTemplate) {
        super(context, new Address("FRONT"), messagingTemplate);
    }


}
