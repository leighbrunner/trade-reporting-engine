package com.leighbrunner.tradereportingengine.adapter;

import com.leighbrunner.tradereportingengine.entity.EventRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventAdapterTest {

    private final EventAdapter eventAdapter = new EventAdapter();
    private final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    @Test
    public void testConvertXMLToRecord() throws Exception {
        String xmlContent = "<?xml version=\"1.0\" encoding=\"utf-8\"?><requestConfirmation><trade><buyerPartyReference href=\"buyer\"/><sellerPartyReference href=\"seller\"/><paymentAmount><amount>10.40</amount><currency>USD</currency></paymentAmount></trade></requestConfirmation>";

        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource inputSource = new InputSource(new java.io.StringReader(xmlContent));
        Document document = builder.parse(inputSource);

        EventRecord result = eventAdapter.convertXMLToRecord(document);

        assertEquals("buyer", result.getBuyerParty());
        assertEquals("seller", result.getSellerParty());
        assertEquals(new BigDecimal("10.40"), result.getPremiumAmount());
        assertEquals("USD", result.getPremiumCurrency());
    }
    
    @Test
    public void testConvertXMLToRecordWithoutPremiumAmount() throws Exception {
        String xmlContent = "<?xml version=\"1.0\" encoding=\"utf-8\"?><requestConfirmation><trade><buyerPartyReference href=\"buyer\"/><sellerPartyReference href=\"seller\"/><paymentAmount><currency>USD</currency></paymentAmount></trade></requestConfirmation>";

        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource inputSource = new InputSource(new java.io.StringReader(xmlContent));
        Document document = builder.parse(inputSource);

        EventRecord result = eventAdapter.convertXMLToRecord(document);

        assertEquals("buyer", result.getBuyerParty());
        assertEquals("seller", result.getSellerParty());
        Assertions.assertNull(result.getPremiumAmount());
        assertEquals("USD", result.getPremiumCurrency());
    }

}