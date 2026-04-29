package by.darkimpulsepoint.parser;

import by.darkimpulsepoint.composite.Composite;
import by.darkimpulsepoint.composite.ComponentType;
import by.darkimpulsepoint.composite.TextComponent;
import by.darkimpulsepoint.exception.TextProcessingException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class TextParserTest {
    private TextParser textParser;

    @BeforeMethod
    public void setUp() {
        textParser = TextParser.getInstance();
        ParagraphParser paragraphParser = ParagraphParser.getInstance();
        SentenceParser sentenceParser = SentenceParser.getInstance();
        LexemeParser lexemeParser = LexemeParser.getInstance();
        WordParser wordParser = WordParser.getInstance();

        textParser.setNext(paragraphParser);
        paragraphParser.setNext(sentenceParser);
        sentenceParser.setNext(lexemeParser);
        lexemeParser.setNext(wordParser);
    }

    @Test
    public void testParseSingleParagraph() throws TextProcessingException {
        String text = "Single paragraph text.";

        TextComponent result = textParser.parse(text);

        assertNotNull(result);
        assertTrue(result instanceof Composite);
        assertEquals(((Composite) result).getType(), ComponentType.TEXT);
        assertEquals(result.getChildCount(), 1);
    }

    @Test
    public void testParseMultipleParagraphs() throws TextProcessingException {
        String text = "First paragraph.\n\nSecond paragraph.";

        TextComponent result = textParser.parse(text);

        assertNotNull(result);
        assertEquals(result.getChildCount(), 2);
    }

    @Test
    public void testParseEmptyText() throws TextProcessingException {
        String text = "";

        TextComponent result = textParser.parse(text);

        assertNotNull(result);
        assertEquals(result.getChildCount(), 0);
    }

    @Test
    public void testParseParagraphsWithMultipleNewlines() throws TextProcessingException {
        String text = "First.\n\n\nSecond.";

        TextComponent result = textParser.parse(text);

        assertNotNull(result);
        assertEquals(result.getChildCount(), 2);
    }

    @Test
    public void testGetInstanceReturnsSameInstance() {
        TextParser instance1 = TextParser.getInstance();
        TextParser instance2 = TextParser.getInstance();

        assertSame(instance1, instance2);
    }
}
