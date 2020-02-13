package com.example.demo;

import com.example.demo.repo.PageStock;
import com.example.demo.repo.StockDaily;
import com.example.demo.service.ReactiveStockDailyRepository;
import com.example.demo.service.StockDailyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;


@Slf4j
@Service
public class ReactiveStockFetchService {

    private final static int PAGE_SIZE = 100;

    private final static String STOCK_DATA_URL = "http://quotes.money.163.com/hs/service/diyrank.php";


    @Autowired
    ReactiveStockDailyRepository dailyRepository;


    public Flux<List<StockDaily>> asyncFetchStockDaily(){

        Flux<List<StockDaily>> result = Flux.empty();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        WebClient webClient =  WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE)
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(clientCodecConfigurer -> {
                            clientCodecConfigurer.customCodecs().encoder(new Jackson2JsonEncoder());
                            clientCodecConfigurer.customCodecs().decoder(new Jackson2JsonDecoder(mapper, MimeType.valueOf("text/plain;charset=utf-8")));
                        }).build())
                .build();

        Mono<Integer> total = webClient.get()
                .uri(getRequestUrl(0))
                .accept(MediaType.TEXT_PLAIN)
                .retrieve()
                .bodyToMono(PageStock.class)
                .map(item -> item.getCount());

        return total.thenMany(
                Flux.range(0, total.block())
                .flatMap(page -> webClient.get()
                        .uri(getRequestUrl(page))
                        .accept(MediaType.TEXT_PLAIN)
                        .retrieve()
                        .bodyToMono(PageStock.class)
                        .map(item -> item.getList())
                        .flux()));

    }


    private String getRequestUrl(int page){
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(STOCK_DATA_URL)
                .queryParam("page", page)
                .queryParam("query","STYPE:EQA")
                .queryParam("fields","NO,SYMBOL,NAME,PRICE,PERCENT,UPDOWN,FIVE_MINUTE,OPEN,YESTCLOSE,HIGH,LOW,VOLUME,TURNOVER,HS,LB,WB,ZF,PE,MCAP,TCAP,MFSUM,MFRATIO.MFRATIO2,MFRATIO.MFRATIO10,SNAME,CODE,ANNOUNMT,UVSNEWS")
                .queryParam("sort","PERCENT")
                .queryParam("order","desc")
                .queryParam("count", PAGE_SIZE)
                .queryParam("type", "query");
        return builder.toUriString();

    }


    @Scheduled(cron = "0 */2 0-23 ? * MON-FRI")
    public void scheduleFetchStock() {
        asyncFetchStockDaily()
            .subscribe(
                item -> item.forEach(data -> {
                    data.setCreatedDate(LocalDate.now());
                    log.info("retrieve stock {}, name {} ", data.getCode(), data.getName());
                    dailyRepository.save(data);
                }),
                error -> log.error("subscribe daily message error: {}", error)
        );
    }


}
