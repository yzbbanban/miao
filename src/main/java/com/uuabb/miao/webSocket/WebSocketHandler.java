package com.uuabb.miao.webSocket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * Created by ban on 2018/6/28.
 */
public interface WebSocketHandler {
    /**
     * 建立连接后触发的回调
     */
    void afterConnectionEstablished(WebSocketSession session) throws Exception;

    /**
     * 收到消息时触发的回调
     */
    void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception;

    /**
     * 传输消息出错时触发的回调
     */
    void handleTransportError(WebSocketSession session, Throwable exception) throws Exception;

    /**
     * 断开连接后触发的回调
     */
    void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception;

    /**
     * 是否处理分片消息
     */
    boolean supportsPartialMessages();


}
