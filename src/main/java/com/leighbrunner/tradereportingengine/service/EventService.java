package com.leighbrunner.tradereportingengine.service;

import com.leighbrunner.tradereportingengine.entity.EventRecord;
import com.leighbrunner.tradereportingengine.respository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The EventService class is responsible for handling basic event-related operations.
 */
@Service
@RequiredArgsConstructor
public class EventService {

    public final EventRepository eventRepository;

    /**
     * Saves an event record.
     *
     * @param eventRecord the event record to be saved
     * @return the saved event record
     */
    public EventRecord saveEventRecord(EventRecord eventRecord) {
        return eventRepository.save(eventRecord);
    }

    /**
     * Finds a list of events by the seller party name and premium currency.
     *
     * @param sellerParty the name of the seller party
     * @param premiumCurrency the premium currency
     * @return a list of EventRecord objects that match the specified seller party and premium currency
     */
    public List<EventRecord> findBySellerPartyAndPremiumCurrency(String sellerParty, String premiumCurrency) {
        return eventRepository.findBySellerPartyIgnoreCaseAndPremiumCurrencyIgnoreCase(sellerParty, premiumCurrency);
    }

    /**
     * Retrieves a list of all events.
     *
     * @return a list of EventRecord objects representing all events
     */
    public List<EventRecord> findAllEvents() {
        return eventRepository.findAll();
    }

    /**
     * Finds a list of events by premium currency, ignoring case.
     *
     * @param premiumCurrency the premium currency to search for
     * @return a list of EventRecords that have the specified premium currency
     */
    public List<EventRecord> findEventsByPremiumCurrency(String premiumCurrency) {
        return eventRepository.findByPremiumCurrencyIgnoreCase(premiumCurrency);
    }
}
