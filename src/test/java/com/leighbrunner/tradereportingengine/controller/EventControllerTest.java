package com.leighbrunner.tradereportingengine.controller;

import com.leighbrunner.tradereportingengine.entity.EventRecord;
import com.leighbrunner.tradereportingengine.service.EventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(EventController.class)
public class EventControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EventService eventService;

    @Test
    public void findAllEventsTest() throws Exception {
        EventRecord event1 = new EventRecord();
        event1.setBuyerParty("BuyerA");
        event1.setSellerParty("SellerA");
        event1.setPremiumAmount(new BigDecimal("1000"));
        event1.setPremiumCurrency("USD");

        EventRecord event2 = new EventRecord();
        event2.setBuyerParty("BuyerB");
        event2.setSellerParty("SellerB");
        event2.setPremiumAmount(new BigDecimal("2000"));
        event2.setPremiumCurrency("GBP");

        List<EventRecord> allEvents = Arrays.asList(event1, event2);

        given(eventService.findAllEvents()).willReturn(allEvents);

        mvc.perform(get("/event/all")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].buyer_party", is(event1.getBuyerParty())))
                .andExpect(jsonPath("$[0].seller_party", is(event1.getSellerParty())))
                .andExpect(jsonPath("$[0].premium_amount", is(event1.getPremiumAmount().intValue())))
                .andExpect(jsonPath("$[0].premium_currency", is(event1.getPremiumCurrency())))
                .andExpect(jsonPath("$[0].*", hasSize(4)))
                .andExpect(jsonPath("$[1].buyer_party", is(event2.getBuyerParty())))
                .andExpect(jsonPath("$[1].seller_party", is(event2.getSellerParty())))
                .andExpect(jsonPath("$[1].premium_amount", is(event2.getPremiumAmount().intValue())))
                .andExpect(jsonPath("$[1].premium_currency", is(event2.getPremiumCurrency())))
                .andExpect(jsonPath("$[1].*", hasSize(4)));

        verify(eventService, times(1)).findAllEvents();
        verifyNoMoreInteractions(eventService);
    }

    @Test
    public void findEventsByCurrencyTest_USD() throws Exception {
        EventRecord eventWithUSD = new EventRecord();
        eventWithUSD.setBuyerParty("BuyerUSD");
        eventWithUSD.setSellerParty("SellerUSD");
        eventWithUSD.setPremiumAmount(new BigDecimal("100"));
        eventWithUSD.setPremiumCurrency("USD");

        List<EventRecord> usdEvents = Arrays.asList(eventWithUSD);

        given(eventService.findEventsByPremiumCurrency("USD")).willReturn(usdEvents);

        mvc.perform(get("/event/currency/USD")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].buyer_party", is(eventWithUSD.getBuyerParty())))
                .andExpect(jsonPath("$[0].seller_party", is(eventWithUSD.getSellerParty())))
                .andExpect(jsonPath("$[0].premium_amount", is(eventWithUSD.getPremiumAmount().intValue())))
                .andExpect(jsonPath("$[0].premium_currency", is(eventWithUSD.getPremiumCurrency())))
                .andExpect(jsonPath("$[0].*", hasSize(4)));

        verify(eventService, times(1)).findEventsByPremiumCurrency("USD");
        verifyNoMoreInteractions(eventService);
    }

    @Test
    public void findEventsByCurrencyTest_GBP() throws Exception {
        EventRecord eventWithGBP = new EventRecord();
        eventWithGBP.setBuyerParty("BuyerGBP");
        eventWithGBP.setSellerParty("SellerGBP");
        eventWithGBP.setPremiumAmount(new BigDecimal("200"));
       eventWithGBP.setPremiumCurrency("GBP");

       List<EventRecord> gbpEvents = Arrays.asList(eventWithGBP);

       given(eventService.findEventsByPremiumCurrency("GBP")).willReturn(gbpEvents);

       mvc.perform(get("/event/currency/GBP")
               .contentType(APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
               .andExpect(jsonPath("$", hasSize(1)))
               .andExpect(jsonPath("$[0].buyer_party", is(eventWithGBP.getBuyerParty())))
               .andExpect(jsonPath("$[0].seller_party", is(eventWithGBP.getSellerParty())))
               .andExpect(jsonPath("$[0].premium_amount", is(eventWithGBP.getPremiumAmount().intValue())))
               .andExpect(jsonPath("$[0].premium_currency", is(eventWithGBP.getPremiumCurrency())))
               .andExpect(jsonPath("$[0].*", hasSize(4)));

       verify(eventService, times(1)).findEventsByPremiumCurrency("GBP");
       verifyNoMoreInteractions(eventService);
   }
}
