package com.joyin.fyzg.constant;

public class WebsocketConstant {

    public interface RabbitConstant {
        /**
         *
         */
        String EXCHANGE_NAME = "websocket.direct";
        /**
         *
         */
        String QUEUE_WS_USER_NAME = "websocket.user";

        String QUEUE_WS_TOPIC_NAME = "websocket.topic";

        String QUEUE_NAME_SIGN = "websocket.queue.sign";

    }
}
