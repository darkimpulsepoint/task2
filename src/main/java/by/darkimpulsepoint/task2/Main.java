package by.darkimpulsepoint.task2;

import by.darkimpulsepoint.task2.composite.TextComponent;
import by.darkimpulsepoint.task2.exception.TextProcessingException;
import by.darkimpulsepoint.task2.parser.ParserChain;
import by.darkimpulsepoint.task2.reader.impl.TextFileTextReaderImpl;
import by.darkimpulsepoint.task2.service.*;
import by.darkimpulsepoint.task2.service.impl.SentenceAnalysisServiceImpl;
import by.darkimpulsepoint.task2.service.impl.SentenceModificationServiceImpl;
import by.darkimpulsepoint.task2.service.impl.TextAnalysisServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            logger.info("Starting text processing application");

            TextFileTextReaderImpl reader = new TextFileTextReaderImpl();
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
