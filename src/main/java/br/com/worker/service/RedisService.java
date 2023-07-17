package br.com.worker.service;

import br.com.worker.domain.Car;
import br.com.worker.repository.RedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Scope("singleton")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class RedisService {

    private final RedisRepository redisRepository;

    @Autowired
    public RedisService(RedisRepository redisRepository) {
        this.redisRepository = redisRepository;
    }

    public String mountCacheKey(Car car){
        return "cacheCars:chassi:" + car.getChassi();
    }

    public void setCacheCars(Car car){
        try {
            String key = mountCacheKey(car);
            redisRepository.setCache(key, car);
            log.info("Car stored => " + " key => " + key);
        }catch (Exception e) {
            log.error("Error setCacheCars " + e.getMessage());
        }
    }

    public Car getCacheCars(Car car) {
        try {
            String key = mountCacheKey(car);
            Car carCache = (Car) redisRepository.getCache(key);

            if (carCache != null) {
                return carCache;
            } else return null;
        } catch (Exception e) {
            log.error("Error getCacheCars " + e.getMessage());
            return null;
        }
    }

}
