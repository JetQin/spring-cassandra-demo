package com.example.demo.repo;

import com.datastax.driver.core.DataType.Name;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table("STOCK_DAILY")
@JsonIgnoreProperties(ignoreUnknown=true)
public class StockDaily implements Serializable {

    @PrimaryKeyColumn(name="CREATED_DATE", ordinal=0, type = PrimaryKeyType.PARTITIONED)
    private LocalDate createdDate;

    @PrimaryKeyColumn(name="CODE", ordinal=1, type = PrimaryKeyType.CLUSTERED)
    @JsonProperty("CODE")
    private String code;

    @PrimaryKeyColumn(name="SYMBOL", ordinal=2, type = PrimaryKeyType.CLUSTERED)
    @JsonProperty("SYMBOL")
    private String     symbol;

    @JsonProperty("FIVE_MINUTE")
    private BigDecimal five_minute;

    @JsonProperty("HIGH")
    private BigDecimal high;

    @JsonProperty("HS")
    private BigDecimal hs;

    @JsonProperty("LB")
    private BigDecimal lb ;

    @JsonProperty("LOW")
    private BigDecimal low ;

    @JsonProperty("MCAP")
    private BigDecimal mcap ;

    @JsonProperty("MFSUM")
    private BigDecimal mfsum ;

    @JsonProperty("NAME")
    private String     name;

    @JsonProperty("OPEN")
    private BigDecimal open ;

    @JsonProperty("PE")
    private BigDecimal pe ;

    @JsonProperty("PERCENT")
    private BigDecimal percent ;

    @JsonProperty("PRICE")
    private BigDecimal price ;

    @JsonProperty("SNAME")
    private String sname;

    @JsonProperty("TCAP")
    private BigDecimal tcap ;

    @JsonProperty("TURNOVER")
    private BigDecimal turnover ;

    @JsonProperty("UPDOWN")
    private BigDecimal updown ;

    @JsonProperty("VOLUME")
    private BigDecimal volume ;

    @JsonProperty("WB")
    private BigDecimal wb ;

    @JsonProperty("YESTCLOSE")
    private BigDecimal yestclose ;

    @JsonProperty("ZF")
    private BigDecimal zf;

    @JsonProperty("MFRATIO")
    @CassandraType(type=Name.UDT, userTypeName = "ratio")
    private Ratio mfratio;
}
