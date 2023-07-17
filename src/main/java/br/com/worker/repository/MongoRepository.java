package br.com.worker.repository;

import br.com.worker.domain.Car;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.Repository;


@Slf4j
@Repository
public class MongoRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public MongoRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void setMongoDatabase(Car car){
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("chassi").is(car.getChassi()));

            Update update = new Update();
            update.set("idCar", car.getId());
            update.set("model", car.getModel());
            update.set("mark", car.getMark());
            update.set("color", car.getColor());
            update.set("yearModel", car.getYearModel());
            update.set("odometerKm", car.getOdometerKm());
            update.set("passengerNumbers", car.getPassengerNumbers());

            UpdateResult result = mongoTemplate.upsert(query, update, Car.class);
            if (result.wasAcknowledged()) {
                if (result.getModifiedCount() > 0) {
                    log.info("Car updated");
                } else {
                    log.info("Car inserted");
                }
            } else {
                log.error("Failed to perform operation");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
