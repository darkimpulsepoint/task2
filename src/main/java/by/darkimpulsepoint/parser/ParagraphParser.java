package by.darkimpulsepoint.parser;

import by.darkimpulsepoint.composite.Composite;
import by.darkimpulsepoint.composite.ComponentType;
import by.darkimpulsepoint.composite.TextComponent;
import by.darkimpulsepoint.exception.TextProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParagraphParser extends AbstractParser {
    private static final Logger logger = LogManager.getLogger(ParagraphParser.class);
    private static ParagraphParser instance;

    private ParagraphParser() {
    }

    public static ParagraphParser getInstance() {
        if (instance == null) {
            instance = new ParagraphParser();
        }
        return instance;
    }

    @Override
    public TextComponent parse(String text) throws TextProcessingException {
        logger.debug("Parsing paragraph into sentences");
        Composite paragraph = new Composite(ComponentType.PARAGRAPH);
        Pattern pattern = Pattern.compile(RegexPatterns.SENTENCE_PATTERN);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            String sentence = matcher.group().trim();
            if (nextParser != null) {
                paragraph.add(nextParser.parse(sentence));
            }
        }
        return paragraph;
    }
}
