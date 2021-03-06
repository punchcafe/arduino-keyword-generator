package dev.punchcafe.arduino.keywordgen;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static dev.punchcafe.arduino.keywordgen.TestConstants.ROOT_PACKAGE_HEADER_FILE_PATH;
import static org.junit.jupiter.api.Assertions.*;

public class HeaderFileDeserializerTest {

    @Test
    void headerDeserializer_deserializesSimpleFile() throws IOException {
        final var result = HeaderFileDeserializer.deserializeHeaderFile(Path.of(ROOT_PACKAGE_HEADER_FILE_PATH).toFile())
                .stream()
                .map(Object::toString)
                .collect(Collectors.toList());
        final var expected = List.of("Name: SomeClass, Keyword Type: CLASS", "Name: helloWorld, Keyword Type: METHOD", "Name: add, Keyword Type: METHOD");
        assertEquals(expected, result);
    }
}
