package com.leighbrunner.tradereportingengine.respository;

import com.leighbrunner.tradereportingengine.entity.EventRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<EventRecord, Long> {
    List<EventRecord> findBySellerPartyIgnoreCaseAndPremiumCurrencyIgnoreCase(String sellerParty, String premiumCurrency);

    List<EventRecord> findByPremiumCurrencyIgnoreCase(String premiumCurrency);
}
