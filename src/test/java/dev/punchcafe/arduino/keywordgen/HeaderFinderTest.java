package dev.punchcafe.arduino.keywordgen;

import org.junit.jupiter.api.Test;

import static dev.punchcafe.arduino.keywordgen.TestConstants.ALL_HEADER_FILE_PATHS;
import static dev.punchcafe.arduino.keywordgen.TestConstants.ROOT_PACKAGE_PATH;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class HeaderFinderTest {

    @Test
    void headerFinder_discoversAllHeaderFilesOnPath() {
        final var result = HeaderFinder.findHeaderFiles(Path.of(ROOT_PACKAGE_PATH).toFile()).stream()
                .map(File::getPath)
                .collect(Collectors.toList());
        assertEquals(ALL_HEADER_FILE_PATHS, result);
    }
}
