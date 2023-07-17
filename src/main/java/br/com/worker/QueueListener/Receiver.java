package br.com.worker.QueueListener;

import br.com.worker.domain.Car;
import br.com.worker.process.ProcessCars;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Component
public class Receiver {

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private ProcessCars processCars;

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void receive(Message payload, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {

        String strings = new String(payload.getBody(), StandardCharsets.UTF_8);
        List<Car> cars = null;

        try{
            cars = new ObjectMapper().readValue(strings.toString(),
                    new TypeReference<List<Car>>() {
                    });

            if(cars != null && !cars.isEmpty()){
                processCars.process(cars);
                channel.basicAck(tag, false);
            }

        }catch (Exception e){
            log.error("Erro ao consumir msg ", e);
            channel.basicAck(tag, false);
        }



    }
}
