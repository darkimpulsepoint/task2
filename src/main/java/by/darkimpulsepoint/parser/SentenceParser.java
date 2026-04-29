package by.darkimpulsepoint.parser;

import by.darkimpulsepoint.composite.Composite;
import by.darkimpulsepoint.composite.ComponentType;
import by.darkimpulsepoint.composite.TextComponent;
import by.darkimpulsepoint.exception.TextProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SentenceParser extends AbstractParser {
    private static final Logger logger = LogManager.getLogger(SentenceParser.class);
    private static SentenceParser instance;

    private SentenceParser() {
    }

    public static SentenceParser getInstance() {
        if (instance == null) {
            instance = new SentenceParser();
        }
        return instance;
    }

    @Override
    public TextComponent parse(String text) throws TextProcessingException {
        logger.debug("Parsing sentence into lexemes");
        Composite sentence = new Composite(ComponentType.SENTENCE);
        Pattern pattern = Pattern.compile(RegexPatterns.LEXEME_PATTERN);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            String lexeme = matcher.group();
            if (nextParser != null) {
                sentence.add(nextParser.parse(lexeme));
            }
        }
        return sentence;
    }
}
