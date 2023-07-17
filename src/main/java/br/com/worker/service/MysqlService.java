package br.com.worker.service;

import br.com.worker.domain.Car;
import br.com.worker.repository.CarsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MysqlService {

    @Autowired
    private CarsRepository carsRepository;

    public List<Car> findAll(){
        return carsRepository.findAll();
    }

    public Car getForChassi(String chassi){
        return carsRepository.findByChassiObject(chassi);
    }
}
