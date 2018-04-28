package com.uuabb.miao.webSocket;

import javax.websocket.*;

/**
 * Created by ban on 2018/4/19.
 */
@ClientEndpoint
public class MyClient {

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Connected to endpoint: " + session.getBasicRemote());
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println(message);
    }

    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
    }

}

