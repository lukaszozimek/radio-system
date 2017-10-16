package io.protone.application.web.api.websocket.scheduler;


import io.protone.core.api.dto.CorActivityDTO;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;

@Controller
public class SchPlaylistEditService implements ApplicationListener<SessionDisconnectEvent> {


    private final SimpMessageSendingOperations messagingTemplate;

    public SchPlaylistEditService(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @SubscribeMapping("/topic/playout/playlist")
    @SendTo("/playout/playlist")
    public CorActivityDTO sendActivity(@Payload CorActivityDTO activityDTO, StompHeaderAccessor stompHeaderAccessor, Principal principal) {

        return null;
    }

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {

    }
}
