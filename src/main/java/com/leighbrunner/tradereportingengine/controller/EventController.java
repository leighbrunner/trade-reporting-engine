package com.leighbrunner.tradereportingengine.controller;

import com.leighbrunner.tradereportingengine.entity.EventRecord;
import com.leighbrunner.tradereportingengine.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/event")
public class EventController {

    private final EventService eventService;

    @GetMapping("/all")
    public List<EventRecord> findAllEvents() {
        return eventService.findAllEvents();
    }

    @GetMapping("/currency/{currency}")
    public List<EventRecord> findEventsByCurrency(@PathVariable("currency") String currency) {
        return eventService.findEventsByPremiumCurrency(currency);
    }

}
