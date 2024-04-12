package com.leighbrunner.tradereportingengine.service;

import com.leighbrunner.tradereportingengine.entity.EventRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventReportServiceTest {

    @InjectMocks
    EventReportService eventReportService;

    @Mock
    EventService eventService;

    @Test
    public void getEmuBankAUDAndBisonBankUSDNonAnagramPartiesTest() {
        EventRecord event1 = new EventRecord();
        event1.setBuyerParty("ABCDE1");
        event1.setSellerParty("EMU_BANK");
        event1.setPremiumAmount(BigDecimal.valueOf(100));
        event1.setPremiumCurrency("AUD");

        // This event should be filtered out because the parties are anagrams
        EventRecord event2 = new EventRecord();
        event2.setBuyerParty("EMUBANK_");
        event2.setSellerParty("EMU_BANK");
        event2.setPremiumAmount(BigDecimal.valueOf(150));
        event2.setPremiumCurrency("AUD");
        
        EventRecord event3 = new EventRecord();
        event3.setBuyerParty("ABCDE3");
        event3.setSellerParty("BISON_BANK");
        event3.setPremiumAmount(BigDecimal.valueOf(200));
        event3.setPremiumCurrency("USD");

        EventRecord event4 = new EventRecord();
        event4.setBuyerParty("ABCDE4");
        event4.setSellerParty("BISON_BANK");
        event4.setPremiumAmount(BigDecimal.valueOf(250));
        event4.setPremiumCurrency("USD");

        when(eventService.findBySellerPartyAndPremiumCurrency("EMU_BANK", "AUD"))
                .thenReturn(List.of(event1, event2));
        when(eventService.findBySellerPartyAndPremiumCurrency("BISON_BANK", "USD"))
                .thenReturn(List.of(event3, event4));

        List<EventRecord> result = eventReportService.getEmuBankAUDAndBisonBankUSDNonAnagramParties();

        assertEquals(3, result.size(), "Expect three events, because event2 should be filtered out");
        assertEquals("ABCDE1", result.get(0).getBuyerParty(), "Confirm the first event is correct");
        assertEquals("ABCDE3", result.get(1).getBuyerParty(), "Confirm the second event is correct");
        assertEquals("ABCDE4", result.get(2).getBuyerParty(), "Confirm the third event is correct");
    }
}