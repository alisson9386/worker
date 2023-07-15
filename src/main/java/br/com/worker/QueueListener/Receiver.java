package br.com.worker.QueueListener;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class Receiver {

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void receive(Message payload, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {

        String strings = new String(payload.getBody(), StandardCharsets.UTF_8);

        System.out.printf(strings);

        channel.basicAck(tag, false);

    }
}
