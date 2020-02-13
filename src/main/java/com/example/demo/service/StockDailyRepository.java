package com.example.demo.service;

import com.example.demo.repo.StockDaily;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockDailyRepository extends CassandraRepository<StockDaily, String> {
}
