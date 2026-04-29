package by.darkimpulsepoint.reader;

import by.darkimpulsepoint.exception.TextProcessingException;

public interface Reader {
    String read(String source) throws TextProcessingException;
}
