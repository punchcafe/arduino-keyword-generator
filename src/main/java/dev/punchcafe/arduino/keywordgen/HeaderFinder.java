package dev.punchcafe.arduino.keywordgen;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

public class HeaderFinder {

    private static String HEADER_FILE_EXTENSION = ".h";

    public static List<File> findHeaderFiles(File file){
        if(isHeaderFile(file)){
            return List.of(file);
        } else if(!file.isDirectory()){
            return List.of();
        } else {
            return Arrays.stream(ofNullable(file.listFiles()).orElse(new File[]{}))
                    .map(HeaderFinder::findHeaderFiles)
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
        }
    }

    private static boolean isHeaderFile(final File file){
        return file.getName().endsWith(HEADER_FILE_EXTENSION) && !file.isDirectory();
    }
}
