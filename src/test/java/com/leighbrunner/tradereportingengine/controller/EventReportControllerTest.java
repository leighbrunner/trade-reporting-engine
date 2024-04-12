package com.leighbrunner.tradereportingengine.controller;

import com.leighbrunner.tradereportingengine.entity.EventRecord;
import com.leighbrunner.tradereportingengine.service.EventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class EventReportControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    EventService eventService;

    @Test
    public void testEmuBankAUDAndBisonBankUSD() throws Exception {
        EventRecord event1 = new EventRecord();
        event1.setId(1L);
        event1.setBuyerParty("EMU_BANK");
        event1.setSellerParty("BISON_BANK");
        event1.setPremiumAmount(new BigDecimal(70));
        event1.setPremiumCurrency("AUD");

        // This value shouldn't be returned because the parties are anagrams
        EventRecord event2 = new EventRecord();
        event2.setId(2L);
        event2.setBuyerParty("BISONBANK_");
        event2.setSellerParty("BISON_BANK");
        event2.setPremiumAmount(new BigDecimal(80));
        event2.setPremiumCurrency("USD");

        when(eventService.findBySellerPartyAndPremiumCurrency("EMU_BANK", "AUD")).thenReturn(Arrays.asList(event1));
        when(eventService.findBySellerPartyAndPremiumCurrency("BISON_BANK", "USD")).thenReturn(Arrays.asList(event2));

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].buyer_party", is(event1.getBuyerParty())))
                .andExpect(jsonPath("$[0].seller_party", is(event1.getSellerParty())))
                .andExpect(jsonPath("$[0].premium_amount", is(event1.getPremiumAmount().intValue())))
                .andExpect(jsonPath("$[0].premium_currency", is(event1.getPremiumCurrency())))
                .andExpect(jsonPath("$[0].*", hasSize(4)));

    }

}