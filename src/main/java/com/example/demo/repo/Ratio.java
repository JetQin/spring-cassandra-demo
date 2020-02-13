package com.example.demo.repo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@UserDefinedType("ratio")
public class Ratio implements Serializable {

    @JsonProperty("MFRATIO2")
    private BigDecimal mfratio2;

    @JsonProperty("MFRATIO10")
    private BigDecimal mfratio10;
}
