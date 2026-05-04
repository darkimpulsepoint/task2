package by.darkimpulsepoint.task2.parser;

import by.darkimpulsepoint.task2.composite.TextComponent;
import by.darkimpulsepoint.task2.exception.TextProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ParserChain {
    private static final Logger logger = LogManager.getLogger(ParserChain.class);
    private final AbstractParser textParser;

    public ParserChain() {
        logger.info("Initializing parser chain");
        textParser = TextParser.getInstance();
        AbstractParser paragraphParser = ParagraphParser.getInstance();
        AbstractParser sentenceParser = SentenceParser.getInstance();
        AbstractParser lexemeParser = LexemeParser.getInstance();
        AbstractParser wordParser = WordParser.getInstance();

        textParser.setNext(paragraphParser);
        paragraphParser.setNext(sentenceParser);
        sentenceParser.setNext(lexemeParser);
        lexemeParser.setNext(wordParser);
        logger.info("Parser chain initialized successfully");
    }

    public TextComponent parse(String text) throws TextProcessingException {
        return textParser.parse(text);
    }
}
