package by.darkimpulsepoint.parser;

import by.darkimpulsepoint.composite.Composite;
import by.darkimpulsepoint.composite.ComponentType;
import by.darkimpulsepoint.composite.TextComponent;
import by.darkimpulsepoint.exception.TextProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexemeParser extends AbstractParser {
    private static final Logger logger = LogManager.getLogger(LexemeParser.class);
    private static LexemeParser instance;

    private LexemeParser() {
    }

    public static LexemeParser getInstance() {
        if (instance == null) {
            instance = new LexemeParser();
        }
        return instance;
    }

    @Override
    public TextComponent parse(String text) throws TextProcessingException {
        logger.debug("Parsing lexeme into words and symbols");
        Composite lexeme = new Composite(ComponentType.LEXEME);
        Pattern wordPattern = Pattern.compile(RegexPatterns.WORD_PATTERN);
        Matcher matcher = wordPattern.matcher(text);

        int lastEnd = 0;
        while (matcher.find()) {
            for (int i = lastEnd; i < matcher.start(); i++) {
                if (nextParser != null) {
                    lexeme.add(nextParser.parse(String.valueOf(text.charAt(i))));
                }
            }
            if (nextParser != null) {
                lexeme.add(nextParser.parse(matcher.group()));
            }
            lastEnd = matcher.end();
        }

        for (int i = lastEnd; i < text.length(); i++) {
            if (nextParser != null) {
                lexeme.add(nextParser.parse(String.valueOf(text.charAt(i))));
            }
        }

        return lexeme;
    }
}
