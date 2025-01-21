package com.puntoxpress.backend.Config;

import com.puntoxpress.backend.websocket.ReservationWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new ReservationWebSocketHandler(), "/reservation-updates")
                .setAllowedOrigins("*"); // Cambiar "*" por dominios específicos en producción
    }
}