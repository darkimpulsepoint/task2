package by.darkimpulsepoint.task2.parser;

import by.darkimpulsepoint.task2.composite.Composite;
import by.darkimpulsepoint.task2.composite.ComponentType;
import by.darkimpulsepoint.task2.composite.TextComponent;
import by.darkimpulsepoint.task2.exception.TextProcessingException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class SentenceParserTest {
    private SentenceParser sentenceParser;

    @BeforeMethod
    public void setUp() {
        sentenceParser = SentenceParser.getInstance();
        LexemeParser lexemeParser = LexemeParser.getInstance();
        WordParser wordParser = WordParser.getInstance();

        sentenceParser.setNext(lexemeParser);
        lexemeParser.setNext(wordParser);
    }

    @Test
    public void testParseSingleWord() throws TextProcessingException {
        String text = "Word";

        TextComponent result = sentenceParser.parse(text);

        assertNotNull(result);
        assertTrue(result instanceof Composite);
        assertEquals(((Composite) result).getType(), ComponentType.SENTENCE);
        assertTrue(result.getChildCount() >= 1);
    }

    @Test
    public void testParseMultipleWords() throws TextProcessingException {
        String text = "Multiple words here";

        TextComponent result = sentenceParser.parse(text);

        assertNotNull(result);
        assertTrue(result.getChildCount() >= 3);
    }

    @Test
    public void testParseEmptySentence() throws TextProcessingException {
        String text = "";

        TextComponent result = sentenceParser.parse(text);

        assertNotNull(result);
        assertEquals(result.getChildCount(), 0);
    }

    @Test
    public void testGetInstanceReturnsSameInstance() {
        SentenceParser instance1 = SentenceParser.getInstance();
        SentenceParser instance2 = SentenceParser.getInstance();

        assertSame(instance1, instance2);
    }
}
