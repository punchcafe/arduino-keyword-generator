package dev.punchcafe.arduino.keywordgen;

import dev.punchcafe.arduino.keywordgen.model.KeyWordType;
import dev.punchcafe.arduino.keywordgen.model.KeywordRow;

import java.util.List;
import java.util.stream.Collectors;

class ModelClassSerializer {

    static String serialise(List<KeywordRow> modelRows) {
        final var groupedRows = modelRows.stream().collect(Collectors.groupingBy(KeywordRow::getKeywordType));
        return groupedRows.entrySet().stream()
                .map(keywordTypeGroup -> convertListOfNamesWithType(keywordTypeGroup.getValue(), keywordTypeGroup.getKey()))
                .collect(Collectors.joining("\n"));
    }

    private static String convertListOfNamesWithType(final List<KeywordRow> keywordRows, final KeyWordType type) {
        return keywordRows.stream()
                .map(name -> String.format("%s\t%s", name.getName(), convertTypeToToken(type)))
                .collect(Collectors.joining("\n"));
    }

    private static String convertTypeToToken(final KeyWordType type) {
        switch (type) {
            case CLASS:
                return "KEYWORD1";
            case METHOD:
                return "KEYWORD2";
            default:
                return "";
        }
    }
}
