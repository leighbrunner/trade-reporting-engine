package com.leighbrunner.tradereportingengine.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class EventRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    @JsonIgnore
    private Long id;

    @JsonProperty("buyer_party")
    private String buyerParty;

    @JsonProperty("seller_party")
    private String sellerParty;

    @JsonProperty("premium_amount")
    private BigDecimal premiumAmount;

    @JsonProperty("premium_currency")
    private String premiumCurrency;

}
