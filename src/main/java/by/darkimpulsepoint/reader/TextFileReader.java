package by.darkimpulsepoint.reader;

import by.darkimpulsepoint.exception.TextProcessingException;

public interface TextFileReader {
    String read(String source) throws TextProcessingException;
}
