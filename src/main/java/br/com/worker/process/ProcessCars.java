package br.com.worker.process;

import br.com.worker.domain.Car;
import br.com.worker.repository.CarsRepository;
import br.com.worker.service.MongoService;
import br.com.worker.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ProcessCars {

    @Autowired
    private CarsRepository carsRepository;
    @Autowired
    MongoService mongoService;
    @Autowired
    RedisService redisService;

    public void process(List<Car> cars){
        try {
            cars.forEach(car ->{
                boolean exist = carsRepository.findByChassi(car);
                Car currentCar = carsRepository.createOrUpdateCar(car, exist);
                redisService.setCacheCars(currentCar);
                mongoService.setCarMongo(currentCar);
            });
            log.info("All cars is stored");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
