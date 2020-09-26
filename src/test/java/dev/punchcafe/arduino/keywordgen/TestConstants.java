package dev.punchcafe.arduino.keywordgen;

import java.util.List;

public interface TestConstants {

    String ROOT_PACKAGE_HEADER_FILE_PATH =  "./src/test/resources/example-dir/root-package.h";
    String AUX_PACKAGE_HEADER_FILE_PATH =  "./src/test/resources/example-dir/aux-package/aux-pachage.h";

    List<String> ALL_HEADER_FILE_PATHS = List.of(ROOT_PACKAGE_HEADER_FILE_PATH, AUX_PACKAGE_HEADER_FILE_PATH);

    String ROOT_PACKAGE_PATH = "./src/test/resources";
}
