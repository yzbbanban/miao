package com.uuabb.miao.webSocket;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * Created by ban on 2018/6/28.
 */
public class MyHandler extends TextWebSocketHandler {
    /**
     * Spring 在收到 WebSocket 事件时，会自动调用事件对应的方法。
     *
     * @param session session
     * @param message 信息
     */
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        // ...
    }


}
