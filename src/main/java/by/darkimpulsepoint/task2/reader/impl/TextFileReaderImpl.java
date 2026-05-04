package by.darkimpulsepoint.task2.reader.impl;

import by.darkimpulsepoint.task2.exception.TextProcessingException;
import by.darkimpulsepoint.task2.reader.Reader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TextFileReaderImpl implements Reader {
    private static final Logger logger = LogManager.getLogger(TextFileReaderImpl.class);

    @Override
    public String read(String filePath) throws TextProcessingException {
        try {
            logger.info("Reading file: {}", filePath);
            String content = Files.readString(Path.of(filePath));
            logger.info("File read successfully, {} characters", content.length());
            return content;
        } catch (IOException e) {
            logger.error("Failed to read file: {}", filePath, e);
            throw new TextProcessingException("Failed to read file: " + filePath, e);
        }
    }
}
