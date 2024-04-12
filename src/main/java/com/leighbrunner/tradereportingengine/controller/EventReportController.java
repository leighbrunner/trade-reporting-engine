package com.leighbrunner.tradereportingengine.controller;

import com.leighbrunner.tradereportingengine.entity.EventRecord;
import com.leighbrunner.tradereportingengine.service.EventReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller class for generating event reports for REST API endpoints.
 */
@RestController
@RequiredArgsConstructor
public class EventReportController {

    private final EventReportService eventReportService;

    @GetMapping("/")
    public List<EventRecord> emuBankAUDAndBisonBankUSD() {
        return eventReportService.getEmuBankAUDAndBisonBankUSDNonAnagramParties();
    }

    // Additional report mappings would go here.
    // The RestController would probably also have a prefix of "/report"

}
