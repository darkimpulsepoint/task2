package by.darkimpulsepoint.service;

import by.darkimpulsepoint.composite.TextComponent;
import by.darkimpulsepoint.exception.TextProcessingException;
import by.darkimpulsepoint.parser.ParserChain;
import by.darkimpulsepoint.service.impl.SentenceAnalysisServiceImpl;
import by.darkimpulsepoint.service.impl.SentenceModificationServiceImpl;
import by.darkimpulsepoint.service.impl.TextAnalysisServiceImpl;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class TextServiceTest {
    private ParserChain parserChain;

    @BeforeMethod
    public void setUp() {
        parserChain = new ParserChain();
    }

    @Test
    public void testCountLettersAndSymbols() throws TextProcessingException {
        String text = "Hello world!";
        TextComponent parsed = parserChain.parse(text);

        TextAnalysisService service = new TextAnalysisServiceImpl();
        int letterCount = service.countLetters(parsed);
        int symbolCount = service.countSymbols(parsed);

        assertEquals(letterCount, 10);
        assertEquals(symbolCount, 11);
    }

    @Test
    public void testSortSentencesByLetter() throws TextProcessingException {
        String text = "Apple is red. Banana is yellow.";
        TextComponent parsed = parserChain.parse(text);

        SentenceAnalysisService service = new SentenceAnalysisServiceImpl();
        service.sortSentencesByLetter(parsed, 'a');
    }

    @Test
    public void testSwapFirstLastLexeme() throws TextProcessingException {
        String text = "First second third.";
        TextComponent parsed = parserChain.parse(text);

        SentenceModificationService service = new SentenceModificationServiceImpl();
        service.swapFirstLastLexeme(parsed);
    }

    @Test
    public void testFindSentencesWithSameWords() throws TextProcessingException {
        String text = "The cat is here. The dog is there.";
        TextComponent parsed = parserChain.parse(text);

        SentenceAnalysisService service = new SentenceAnalysisServiceImpl();
        service.findSentencesWithSameWords(parsed);
    }

    @Test
    public void testCountLettersEmpty() throws TextProcessingException {
        String text = "";
        TextComponent parsed = parserChain.parse(text);

        TextAnalysisService service = new TextAnalysisServiceImpl();
        int letterCount = service.countLetters(parsed);

        assertEquals(letterCount, 0);
    }

    @Test
    public void testCountSymbolsWithPunctuation() throws TextProcessingException {
        String text = "Hi!";
        TextComponent parsed = parserChain.parse(text);

        TextAnalysisService service = new TextAnalysisServiceImpl();
        int symbolCount = service.countSymbols(parsed);

        assertEquals(symbolCount, 3);
    }
}
