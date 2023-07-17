package br.com.worker.controller;

import br.com.worker.domain.Car;
import br.com.worker.service.MysqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WorkerController {

    @Autowired
    private MysqlService mysqlService;

    @GetMapping(value="/getCarForChassi/{chassi}")
    public @ResponseBody ResponseEntity<?> getCarForChassi(@PathVariable String chassi){
        try {
            Car car = mysqlService.getForChassi(chassi);
            return ResponseEntity.ok(car);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value="/getCars")
    public @ResponseBody ResponseEntity<?> getCars(){
        try {
            List<Car> cars = mysqlService.findAll();
            return ResponseEntity.ok(cars);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
