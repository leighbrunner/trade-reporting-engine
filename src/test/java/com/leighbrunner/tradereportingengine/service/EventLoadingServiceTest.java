package com.leighbrunner.tradereportingengine.service;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventLoadingServiceTest {

    @Test
    void testLoadFilesFromResourceFolder_validDirectoryAndExtension() {
        EventLoadingService eventLoadingService = new EventLoadingService(null);
        String folderName = "input/events";
        String extension = "xml";

        List<File> files = eventLoadingService.loadFilesFromResourceFolder(folderName, extension);

        assertNotNull(files, "Should not be null");
        assertFalse(files.isEmpty(), "Should not be empty");
        assertTrue(files.stream().allMatch(file -> file.getName().endsWith("." + extension)),
                "All files should have the .xml extension");
        assertEquals(8, files.size(), "There should be 8 files found");
    }

    @Test
    void testLoadFilesFromResourceFolder_invalidDirectory() {
        EventLoadingService eventLoadingService = new EventLoadingService(null);
        String folderName = "invalid/directory";
        String extension = "xml";

        List<File> files = eventLoadingService.loadFilesFromResourceFolder(folderName, extension);

        assertTrue(files.isEmpty(), "Should be empty because the directory does not exist");
    }

    @Test
    void testLoadFilesFromResourceFolder_invalidExtension() {
        EventLoadingService eventLoadingService = new EventLoadingService(null);
        String folderName = "input/events";
        String extension = "invalid";

        List<File> files = eventLoadingService.loadFilesFromResourceFolder(folderName, extension);

        assertTrue(files.isEmpty(), "Should be empty because no files match the extension");
    }

}