package by.darkimpulsepoint.task2.parser;

import by.darkimpulsepoint.task2.composite.Composite;
import by.darkimpulsepoint.task2.composite.ComponentType;
import by.darkimpulsepoint.task2.composite.Leaf;
import by.darkimpulsepoint.task2.composite.TextComponent;
import by.darkimpulsepoint.task2.exception.TextProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WordParser extends AbstractParser {
    private static final Logger logger = LogManager.getLogger(WordParser.class);
    private static WordParser instance;

    private WordParser() {
    }

    public static WordParser getInstance() {
        if (instance == null) {
            instance = new WordParser();
        }
        return instance;
    }

    @Override
    public TextComponent parse(String text) throws TextProcessingException {
        logger.debug("Parsing word/symbol: {}", text);
        if (text.length() == 1) {
            return new Leaf(text.charAt(0));
        }

        Composite word = new Composite(ComponentType.WORD);
        for (char c : text.toCharArray()) {
            word.add(new Leaf(c));
        }
        return word;
    }
}
