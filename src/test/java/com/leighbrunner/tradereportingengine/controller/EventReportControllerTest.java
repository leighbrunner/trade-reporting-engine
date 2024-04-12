package com.leighbrunner.tradereportingengine.controller;

import com.leighbrunner.tradereportingengine.entity.EventRecord;
import com.leighbrunner.tradereportingengine.service.EventReportService;
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
    private EventReportService eventReportService;

    @Test
    public void testEmuBankAUDAndBisonBankUSD() throws Exception {
        EventRecord emuBankAUDAndBisonBankUSD = new EventRecord();
        emuBankAUDAndBisonBankUSD.setId(1L);
        emuBankAUDAndBisonBankUSD.setBuyerParty("EMU_BANK");
        emuBankAUDAndBisonBankUSD.setSellerParty("BISON_BANK");
        emuBankAUDAndBisonBankUSD.setPremiumAmount(new BigDecimal(70));
        emuBankAUDAndBisonBankUSD.setPremiumCurrency("USD");

        when(eventReportService.getEmuBankAUDAndBisonBankUSDNonAnagramParties()).thenReturn(Arrays.asList(emuBankAUDAndBisonBankUSD));

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].buyer_party", is(emuBankAUDAndBisonBankUSD.getBuyerParty())))
                .andExpect(jsonPath("$[0].seller_party", is(emuBankAUDAndBisonBankUSD.getSellerParty())))
                .andExpect(jsonPath("$[0].premium_account", is(emuBankAUDAndBisonBankUSD.getPremiumAmount().intValue())))
                .andExpect(jsonPath("$[0].premium_currency", is(emuBankAUDAndBisonBankUSD.getPremiumCurrency())))
                .andExpect(jsonPath("$[0].*", hasSize(4)));

    }

}