package by.darkimpulsepoint.parser;

import by.darkimpulsepoint.composite.TextComponent;
import by.darkimpulsepoint.exception.TextProcessingException;

public abstract class AbstractParser {
    protected AbstractParser nextParser;

    public void setNext(AbstractParser parser) {
        this.nextParser = parser;
    }

    public abstract TextComponent parse(String text) throws TextProcessingException;
}
