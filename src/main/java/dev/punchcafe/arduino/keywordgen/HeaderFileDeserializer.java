package dev.punchcafe.arduino.keywordgen;

import dev.punchcafe.arduino.keywordgen.model.KeyWordType;
import dev.punchcafe.arduino.keywordgen.model.KeywordRow;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class HeaderFileDeserializer {
    //TODO: make this not terrible in future when have an opportunity to do it all more cleverly
    //TODO: unless we don't need complexity in the keywords file, in which case we can simply use obvious matchers

    // private static Pattern CLASS_DEFINITION_REGEX = Pattern.compile("class +([^ ])+ ?\\{?\\}?");
    // private static Pattern PUBLIC_MODIFIER_REGEX = Pattern.compile("public\\:");
    // private static Pattern PRIVATE_MODIFIER_REGEX = Pattern.compile(" .private\\:");
    private static Pattern METHOD_IDENTIFIER_REGEX = Pattern.compile(" *[^ ]+ +([^ ]+) *\\(\\);");

    private enum Scope {GLOBAL, CLASS_PRIVATE, CLASS_PUBLIC}

    public static List<KeywordRow> deserializeHeaderFiles(final List<File> headerFiles) {
        return headerFiles.stream()
                .map(HeaderFileDeserializer::deserializeHeaderFile)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public static List<KeywordRow> deserializeHeaderFile(final File headerFile) {
        final var result = new ArrayList<KeywordRow>();
        var scope = Scope.GLOBAL;
        final List<String> lines;
        try {
            lines = Files.readAllLines(headerFile.toPath());
        } catch (IOException ex) {
            return List.of();
        }
        for (String line : lines) {
            var trimmedLine = line.trim();
            if (trimmedLine.startsWith("class ")) {
                System.out.println("first block!");
                // breaks up name
                final var className = Optional.of(trimmedLine.split(" ")[1])
                        .map(clazzName -> clazzName.endsWith("{") ? clazzName.substring(0, clazzName.length() - 1) : clazzName)
                        .get();
                result.add(new KeywordRow(KeyWordType.CLASS, className));
                if (!trimmedLine.endsWith("}")) {
                    scope = Scope.CLASS_PRIVATE;
                }
            } else if (trimmedLine.startsWith("public:")) {
                scope = Scope.CLASS_PUBLIC;
            } else if (trimmedLine.startsWith("private:")) {
                scope = Scope.CLASS_PRIVATE;
            } else if (scope == Scope.CLASS_PRIVATE) {
                continue;
            } else if (trimmedLine.endsWith("}") && (scope == Scope.CLASS_PRIVATE || scope == Scope.CLASS_PUBLIC)) {
                scope = Scope.GLOBAL;
            } else {
                System.out.println("try to match!");
                System.out.println(trimmedLine);
                final var matcher = METHOD_IDENTIFIER_REGEX.matcher(trimmedLine);
                if (!matcher.find()) {
                    System.out.println("didn't find!");
                    continue;
                }
                result.add(new KeywordRow(KeyWordType.METHOD, matcher.group(1)));
            }
        }
        return result;
    }
}
