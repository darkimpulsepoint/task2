package by.darkimpulsepoint.task2.reader;

import by.darkimpulsepoint.task2.exception.TextProcessingException;

public interface TextReader {
    String read(String source) throws TextProcessingException;
}
