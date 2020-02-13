package com.example.demo.service;

import com.example.demo.repo.StockDaily;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class StockDailyService {

    @Autowired
    ReactiveStockDailyRepository repository;

    public Mono<Void> deleteAll(){
        return repository.deleteAll();
    }

    public Mono<StockDaily> save(StockDaily stockDaily){
        return repository.save(stockDaily);
    }

    public Mono<Long> count(){
        return repository.count();
    }
}
