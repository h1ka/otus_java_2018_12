package ru.otus.front.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.app.FrontendService;
import ru.otus.front.domain.WSMessage;

@RestController
@RequestMapping("admin")
public class UserController {

    private final FrontendService frontendService;


    @Autowired
    public UserController(FrontendService frontendService) {
        this.frontendService = frontendService;

    }

    @MessageMapping({"/connect","/users"})
    @SendTo("/topic/users")
    public void listUsers() {
        frontendService.listUsers();
    }

    @MessageMapping("/save")
    @SendTo("/topic/users")
    public void saveUser(WSMessage wsMessage){
        System.out.println(wsMessage.getUser());
        frontendService.save(wsMessage.getUser());
    }


}
