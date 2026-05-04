package by.darkimpulsepoint.task2.parser;

import by.darkimpulsepoint.task2.composite.Composite;
import by.darkimpulsepoint.task2.composite.ComponentType;
import by.darkimpulsepoint.task2.composite.TextComponent;
import by.darkimpulsepoint.task2.exception.TextProcessingException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ParserChainTest {
    private ParserChain parserChain;

    @BeforeMethod
    public void setUp() {
        parserChain = new ParserChain();
    }

    @Test
    public void testParseSimpleSentence() throws TextProcessingException {
        String text = "Hello world.";

        TextComponent result = parserChain.parse(text);

        assertNotNull(result);
        assertTrue(result instanceof Composite);
        assertEquals(((Composite) result).getType(), ComponentType.TEXT);
    }

    @Test
    public void testParseMultipleSentences() throws TextProcessingException {
        String text = "First sentence. Second sentence.";

        TextComponent result = parserChain.parse(text);

        assertNotNull(result);
        assertTrue(result.getChildCount() > 0);
    }

    @Test
    public void testParseEmptyString() throws TextProcessingException {
        String text = "";

        TextComponent result = parserChain.parse(text);

        assertNotNull(result);
    }

    @Test
    public void testParseMultipleParagraphs() throws TextProcessingException {
        String text = "First paragraph.\n\nSecond paragraph.";

        TextComponent result = parserChain.parse(text);

        assertNotNull(result);
        assertTrue(result.getChildCount() > 0);
    }

    @Test
    public void testParseSentenceWithPunctuation() throws TextProcessingException {
        String text = "Hello, world!";

        TextComponent result = parserChain.parse(text);

        assertNotNull(result);
        assertTrue(result.getContent().contains("Hello"));
    }

    @Test
    public void testParseComplexText() throws TextProcessingException {
        String text = "First sentence with words. Second sentence!\n\nNew paragraph here.";

        TextComponent result = parserChain.parse(text);

        assertNotNull(result);
        assertTrue(result.getChildCount() > 0);
    }

    @Test
    public void testParseTextWithNumbers() throws TextProcessingException {
        String text = "Test 123 numbers.";

        TextComponent result = parserChain.parse(text);

        assertNotNull(result);
        assertTrue(result.getContent().contains("123"));
    }

    @Test
    public void testParseTextWithSpecialCharacters() throws TextProcessingException {
        String text = "Special @#$ characters!";

        TextComponent result = parserChain.parse(text);

        assertNotNull(result);
        assertTrue(result.getContent().contains("@"));
    }
}
