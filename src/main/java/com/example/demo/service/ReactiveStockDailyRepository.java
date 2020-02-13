package com.example.demo.service;

import com.example.demo.repo.StockDaily;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactiveStockDailyRepository extends ReactiveCassandraRepository<StockDaily, String> {
}
