package com.leighbrunner.tradereportingengine.adapter;

import com.leighbrunner.tradereportingengine.entity.EventRecord;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.util.Strings;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;

/**
 * The EventAdapter class provides methods to convert XML data to EventRecord objects.
 */
@Log4j2
public class EventAdapter {

    /**
     * Converts an XML file to an EventRecord object.
     *
     * @param file The XML file to convert.
     * @return The converted EventRecord object.
     * @throws IOException                   If an I/O error occurs.
     * @throws ParserConfigurationException  If a DocumentBuilder cannot be created.
     * @throws SAXException                  If any parse errors occur.
     * @throws XPathExpressionException      If the XPath expression cannot be evaluated.
     */
    public EventRecord convertXMLToRecord(File file) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        String xmlContents = Files.readString(file.toPath());
        return convertXMLToRecord(xmlContents);
    }

    /**
     * Converts XML contents to an EventRecord object.
     *
     * @param xmlContents The XML contents to convert.
     * @return The converted EventRecord object.
     * @throws IOException                   If an I/O error occurs.
     * @throws SAXException                  If any parse errors occur.
     * @throws ParserConfigurationException  If a DocumentBuilder cannot be created.
     * @throws XPathExpressionException      If the XPath expression cannot be evaluated.
     */
    public EventRecord convertXMLToRecord(String xmlContents) throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource inputSource = new InputSource(new java.io.StringReader(xmlContents));
        Document document = builder.parse(inputSource);
        return convertXMLToRecord(document);
    }

    /**
     * Converts an XML document to an EventRecord object.
     *
     * @param xmlDocument The XML document to convert.
     * @return The converted EventRecord object.
     * @throws XPathExpressionException If the XPath expression cannot be evaluated.
     */
    public EventRecord convertXMLToRecord(Document xmlDocument) throws XPathExpressionException {
        EventRecord eventRecord = new EventRecord();

        XPath xpath = XPathFactory.newInstance().newXPath();

        String buyerParty = getStringFromRegex(xpath, xmlDocument, "//buyerPartyReference/@href");
        String sellerParty = getStringFromRegex(xpath, xmlDocument, "//sellerPartyReference/@href");
        String premiumAmount = getStringFromRegex(xpath, xmlDocument, "//paymentAmount/amount");
        String premiumCurrency = getStringFromRegex(xpath, xmlDocument, "//paymentAmount/currency");

        eventRecord.setBuyerParty(buyerParty);
        eventRecord.setSellerParty(sellerParty);
        if (Strings.isNotBlank(premiumAmount)) {
            eventRecord.setPremiumAmount(new BigDecimal(premiumAmount));
        }
        eventRecord.setPremiumCurrency(premiumCurrency);

        return eventRecord;
    }

    /**
     * Retrieves a string value from an XML document based on a given regular expression pattern.
     *
     * @param xpath        the XPath instance used for evaluating XPath expressions
     * @param xmlDocument  the XML document to evaluate
     * @param regex        the regular expression pattern to match
     * @return the string value that matches the given regex pattern
     * @throws XPathExpressionException if an error occurs while evaluating the XPath expression
     */
    private String getStringFromRegex(XPath xpath, Document xmlDocument, String regex) throws XPathExpressionException {
        XPathExpression expression = xpath.compile(regex);
        return (String) expression.evaluate(xmlDocument, XPathConstants.STRING);
    }

}
