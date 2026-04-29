package by.darkimpulsepoint;

import by.darkimpulsepoint.composite.TextComponent;
import by.darkimpulsepoint.exception.TextProcessingException;
import by.darkimpulsepoint.parser.ParserChain;
import by.darkimpulsepoint.reader.impl.TextFileReaderImpl;
import by.darkimpulsepoint.service.*;
import by.darkimpulsepoint.service.impl.SentenceAnalysisServiceImpl;
import by.darkimpulsepoint.service.impl.SentenceModificationServiceImpl;
import by.darkimpulsepoint.service.impl.TextAnalysisServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            logger.info("Starting text processing application");

            TextFileReaderImpl reader = new TextFileReaderImpl();
            String text = reader.read("data/text.txt");

            ParserChain parserChain = new ParserChain();
            TextComponent parsedText = parserChain.parse(text);

            logger.info("Original text: {}", parsedText.getContent());

            TextAnalysisService textAnalysis = new TextAnalysisServiceImpl();
            textAnalysis.displayLetterAndSymbolCount(parsedText);

            SentenceAnalysisService sentenceAnalysis = new SentenceAnalysisServiceImpl();
            sentenceAnalysis.findSentencesWithSameWords(parsedText);

            sentenceAnalysis.sortSentencesByLetter(parsedText, 'a');

            SentenceModificationService sentenceModification = new SentenceModificationServiceImpl();
            sentenceModification.swapFirstLastLexeme(parsedText);

            logger.info("Text processing completed successfully");
        } catch (TextProcessingException e) {
            logger.error("Error processing text", e);
        }
    }
}
