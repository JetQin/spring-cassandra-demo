package com.example.demo.repo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageStock implements Serializable {

    @JsonProperty("page")
    private int page;

    @JsonProperty("count")
    private int count;

    @JsonProperty("list")
    private List<StockDaily> list;

    @JsonProperty("order")
    private String order;

    @JsonProperty("pagecount")
    private int pagecount;

    @JsonProperty("time")
    @JsonFormat(pattern="YYYY-MM-DD HH:mm:ss")
    private Date time;

    @JsonProperty("total")
    private int total;
}
