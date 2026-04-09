package com.solvd.delivery.utils;

import com.solvd.delivery.Main;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FileWordReader {
    public static final Logger LOGGER = LogManager.getLogger(Main.class);


    public static void countWordsFromFile(String[] words, String fileName) {
        File inputFile = new File("src/main/resources/filesToRead/" + fileName);

        int outputFileNumber = 1;
        File outputFile = new File("src/main/resources/filesToRead/"
                + "OutputFile_" + outputFileNumber + ".txt");

        while (outputFile.exists()) {
            outputFileNumber++;
            outputFile = new File("src/main/resources/filesToRead/"
                    + "OutputFile_" + outputFileNumber + ".txt");
        }

        try {
            String content = FileUtils.readFileToString(inputFile, "UTF-8");

            for (String word : words) {
                int quantity = StringUtils.countMatches(content, word);
                FileUtils.writeStringToFile(outputFile, "The word \"" + word +
                        "\" appeared " + quantity + " times.\n", StandardCharsets.UTF_8, true);
                LOGGER.info("The word \"" + word +
                        "\" appeared " + quantity + " times.");
            }

        } catch (IOException e) {
            LOGGER.error("Error reading or writing file: " + e.getMessage());
        }
    }
}
