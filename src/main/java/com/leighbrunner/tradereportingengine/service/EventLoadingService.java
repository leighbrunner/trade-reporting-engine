package com.leighbrunner.tradereportingengine.service;

import com.leighbrunner.tradereportingengine.adapter.EventAdapter;
import com.leighbrunner.tradereportingengine.entity.EventRecord;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * This class is responsible for loading sample events from XML files and saving them to a database using the EventService.
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class EventLoadingService {

    private final EventService eventService;

    @Value("${eventXmlDir}")
    private String eventXmlDir;

    @PostConstruct
    public void init() {
        loadSampleEvents();
    }

    /**
     * Loads sample events from XML files and saves them to a database using the EventService.
     */
    private void loadSampleEvents() {
        log.info("Loading sample events");
        List<File> xmlFiles = loadFilesFromResourceFolder(eventXmlDir, "xml");

        log.info("Found {} event XMLs, loading and saving them to the database now", xmlFiles.size());
        EventAdapter eventAdapter = new EventAdapter();
        for (File xmlFile : xmlFiles) {
            try {
                EventRecord eventRecord = eventAdapter.convertXMLToRecord(xmlFile);
                eventService.saveEventRecord(eventRecord);
            } catch (IOException e) {
                log.error("Failed to load file {}, skipping: {}", xmlFile.getName(), e.getMessage(), e);
            } catch (ParserConfigurationException e) {
                log.error("Failed to parse file {}, skipping: {}", xmlFile.getName(), e.getMessage(), e);
            } catch (SAXException e) {
                log.error("Failed to source data from file {}, skipping: {}", xmlFile.getName(), e.getMessage(), e);
            } catch (XPathExpressionException e) {
                log.error("Failed to evaluate XPath for file {}, skipping: {}", xmlFile.getName(), e.getMessage(), e);
            }
        }
        log.info("Finished loading Events");
    }


    /**
     * Loads files from a resource folder with the given folder name and extension.
     *
     * @param folderName The name of the folder containing the files.
     * @param extension  The file extension to filter the files.
     * @return A list of files from the resource folder matching the folder name and extension.
     */
    public List<File> loadFilesFromResourceFolder(String folderName, String extension) {
        List<File> files = new ArrayList<>();
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(folderName);
        if (resource == null) {
            log.error("Folder '{}' does not exist", folderName);
            return files;
        }

        File folder = new File(resource.getFile());

        if (folder.exists() && folder.isDirectory()) {
            File[] fileList = folder.listFiles();
            if (fileList != null) {
                for (File file : fileList) {
                    if (file.isFile() &&
                            file.getName().toLowerCase().endsWith(extension.toLowerCase())) {
                        files.add(file);
                    }
                }
            }
        }

        return files;
    }


}
