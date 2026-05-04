package by.darkimpulsepoint.task2.parser;

import by.darkimpulsepoint.task2.composite.Composite;
import by.darkimpulsepoint.task2.composite.ComponentType;
import by.darkimpulsepoint.task2.composite.TextComponent;
import by.darkimpulsepoint.task2.exception.TextProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextParser extends AbstractParser {
    private static final Logger logger = LogManager.getLogger(TextParser.class);
    private static TextParser instance;

    private TextParser() {
    }

    public static TextParser getInstance() {
        if (instance == null) {
            instance = new TextParser();
        }
        return instance;
    }

    @Override
    public TextComponent parse(String text) throws TextProcessingException {
        logger.info("Parsing text into paragraphs");
        Composite textComponent = new Composite(ComponentType.TEXT);
        Pattern pattern = Pattern.compile(RegexPatterns.PARAGRAPH_PATTERN);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            String paragraph = matcher.group();
            if (nextParser != null) {
                textComponent.add(nextParser.parse(paragraph));
            }
        }
        logger.info("Text parsed successfully, {} paragraphs found", textComponent.getChildCount());
        return textComponent;
    }
}
