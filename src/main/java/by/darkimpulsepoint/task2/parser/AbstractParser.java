package by.darkimpulsepoint.task2.parser;

import by.darkimpulsepoint.task2.composite.TextComponent;
import by.darkimpulsepoint.task2.exception.TextProcessingException;

public abstract class AbstractParser {
    protected AbstractParser nextParser;

    public void setNext(AbstractParser parser) {
        this.nextParser = parser;
    }

    public abstract TextComponent parse(String text) throws TextProcessingException;
}
