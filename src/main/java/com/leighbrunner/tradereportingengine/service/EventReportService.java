package com.leighbrunner.tradereportingengine.service;

import com.leighbrunner.tradereportingengine.entity.EventRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Service class for generating event reports.
 */
@Service
@RequiredArgsConstructor
public class EventReportService {

    private final EventService eventService;

    /**
     * Retrieves a list of EventRecord objects where the seller party is either "EMU_BANK" and the premium currency is "AUD", or the seller party is "BISON_BANK" and the premium currency
     *  is "USD", and the buyer party and seller party are not anagrams of each other.
     *
     * @return a List of EventRecord objects that meet the specified criteria
     */
    public List<EventRecord> getEmuBankAUDAndBisonBankUSDNonAnagramParties() {
        List<EventRecord> emuBankAndAUD = eventService.findBySellerPartyAndPremiumCurrency("EMU_BANK", "AUD");
        List<EventRecord> bisonBankAndUSD = eventService.findBySellerPartyAndPremiumCurrency("BISON_BANK", "USD");
        return Stream.concat(emuBankAndAUD.stream(), bisonBankAndUSD.stream())
                .filter(event -> !isAnagram(event.getBuyerParty(), event.getSellerParty()))
                .toList();
    }

    /**
     * Checks if two strings are anagrams of each other, ignoring case.
     *
     * @param s1 the first string to compare
     * @param s2 the second string to compare
     * @return true if the strings are anagrams, false otherwise
     */
    private boolean isAnagram(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return false;
        }
        char[] s1chars = s1.toLowerCase().toCharArray();
        char[] s2chars = s2.toLowerCase().toCharArray();
        Arrays.sort(s1chars);
        Arrays.sort(s2chars);
        return Arrays.equals(s1chars, s2chars);
    }
}
