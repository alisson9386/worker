package br.com.worker.service;

import br.com.worker.repository.MongoRepository;
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
public class MongoService {

    private final MongoRepository mongoRepository;

    @Autowired
    public MongoService(MongoRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }
}
