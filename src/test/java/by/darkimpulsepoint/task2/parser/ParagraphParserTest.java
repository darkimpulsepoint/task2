package by.darkimpulsepoint.task2.parser;

import by.darkimpulsepoint.task2.composite.Composite;
import by.darkimpulsepoint.task2.composite.ComponentType;
import by.darkimpulsepoint.task2.composite.TextComponent;
import by.darkimpulsepoint.task2.exception.TextProcessingException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ParagraphParserTest {
    private ParagraphParser paragraphParser;
    private SentenceParser sentenceParser;

    @BeforeMethod
    public void setUp() {
        paragraphParser = ParagraphParser.getInstance();
        sentenceParser = SentenceParser.getInstance();
        LexemeParser lexemeParser = LexemeParser.getInstance();
        WordParser wordParser = WordParser.getInstance();

        paragraphParser.setNext(sentenceParser);
        sentenceParser.setNext(lexemeParser);
        lexemeParser.setNext(wordParser);
    }

    @Test
    public void testParseSingleSentence() throws TextProcessingException {
        String text = "Single sentence.";

        TextComponent result = paragraphParser.parse(text);

        assertNotNull(result);
        assertTrue(result instanceof Composite);
        assertEquals(((Composite) result).getType(), ComponentType.PARAGRAPH);
        assertEquals(result.getChildCount(), 1);
    }

    @Test
    public void testParseMultipleSentences() throws TextProcessingException {
        String text = "First sentence. Second sentence!";

        TextComponent result = paragraphParser.parse(text);

        assertNotNull(result);
        assertTrue(result instanceof Composite);
        assertEquals(((Composite) result).getType(), ComponentType.PARAGRAPH);
        assertEquals(result.getChildCount(), 2);
    }

    @Test
    public void testParseEmptyParagraph() throws TextProcessingException {
        String text = "";

        TextComponent result = paragraphParser.parse(text);

        assertNotNull(result);
        assertEquals(result.getChildCount(), 0);
    }

    @Test
    public void testGetInstanceReturnsSameInstance() {
        ParagraphParser instance1 = ParagraphParser.getInstance();
        ParagraphParser instance2 = ParagraphParser.getInstance();

        assertSame(instance1, instance2);
    }
}
