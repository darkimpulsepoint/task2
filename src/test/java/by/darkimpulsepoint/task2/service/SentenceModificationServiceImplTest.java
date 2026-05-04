package by.darkimpulsepoint.task2.service;

import by.darkimpulsepoint.task2.composite.Composite;
import by.darkimpulsepoint.task2.composite.ComponentType;
import by.darkimpulsepoint.task2.composite.Leaf;
import by.darkimpulsepoint.task2.composite.TextComponent;
import by.darkimpulsepoint.task2.service.impl.SentenceModificationServiceImpl;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class SentenceModificationServiceImplTest {

    private SentenceModificationService service;

    @BeforeMethod
    public void setUp() {
        service = new SentenceModificationServiceImpl();
    }

    private Composite createWord(String text) {
        Composite word = new Composite(ComponentType.WORD);
        for (char c : text.toCharArray()) {
            word.add(new Leaf(c));
        }
        return word;
    }

    private Composite createLexeme(String text) {
        Composite lexeme = new Composite(ComponentType.LEXEME);
        lexeme.add(createWord(text));
        return lexeme;
    }

    private Composite createSentence(Composite... lexemes) {
        Composite sentence = new Composite(ComponentType.SENTENCE);
        for (Composite lexeme : lexemes) {
            sentence.add(lexeme);
        }
        return sentence;
    }

    private Composite createText(Composite... sentences) {
        Composite text = new Composite(ComponentType.TEXT);
        Composite paragraph = new Composite(ComponentType.PARAGRAPH);
        for (Composite sentence : sentences) {
            paragraph.add(sentence);
        }
        text.add(paragraph);
        return text;
    }

    @Test
    public void testSwapFirstLastLexemeSimpleSentence() {
        Composite lexeme1 = createLexeme("Hello");
        Composite lexeme2 = createLexeme("world");
        Composite sentence = createSentence(lexeme1, lexeme2);
        Composite text = createText(sentence);

        TextComponent result = service.swapFirstLastLexeme(text);

        assertTrue(result.getChildCount() > 0);
        Composite composite = (Composite) result;
        Composite paragraph = (Composite) composite.getChild(0);
        assertEquals(paragraph.getChildCount(), 1);
        assertEquals(paragraph.getChild(0).getContent(), "world Hello");
    }

    @Test
    public void testSwapFirstLastLexemeThreeLexemes() {
        Composite lexeme1 = createLexeme("First");
        Composite lexeme2 = createLexeme("middle");
        Composite lexeme3 = createLexeme("last");
        Composite sentence = createSentence(lexeme1, lexeme2, lexeme3);
        Composite text = createText(sentence);

        TextComponent result = service.swapFirstLastLexeme(text);

        assertTrue(result.getChildCount() > 0);
        Composite composite = (Composite) result;
        Composite paragraph = (Composite) composite.getChild(0);
        assertEquals(paragraph.getChildCount(), 1);
        assertEquals(paragraph.getChild(0).getContent(), "last middle First");
    }

    @Test
    public void testSwapFirstLastLexemeSingleLexeme() {
        Composite lexeme = createLexeme("Only");
        Composite sentence = createSentence(lexeme);
        Composite text = createText(sentence);

        TextComponent result = service.swapFirstLastLexeme(text);

        assertTrue(result.getChildCount() > 0);
        Composite composite = (Composite) result;
        Composite paragraph = (Composite) composite.getChild(0);
        assertEquals(paragraph.getChildCount(), 1);
        assertEquals(paragraph.getChild(0).getContent(), "Only");
    }

    @Test
    public void testSwapFirstLastLexemeEmptyText() {
        Composite text = new Composite(ComponentType.TEXT);

        TextComponent result = service.swapFirstLastLexeme(text);

        assertEquals(result.getChildCount(), 1);
        Composite composite = (Composite) result;
        Composite paragraph = (Composite) composite.getChild(0);
        assertEquals(paragraph.getChildCount(), 0);
    }

    @Test
    public void testSwapFirstLastLexemeMultipleSentences() {
        Composite sentence1 = createSentence(createLexeme("Hello"), createLexeme("world"));
        Composite sentence2 = createSentence(createLexeme("Good"), createLexeme("bye"));
        Composite text = createText(sentence1, sentence2);

        TextComponent result = service.swapFirstLastLexeme(text);

        assertTrue(result.getChildCount() > 0);
        Composite composite = (Composite) result;
        Composite paragraph = (Composite) composite.getChild(0);
        assertEquals(paragraph.getChildCount(), 2);
        assertEquals(paragraph.getChild(0).getContent(), "world Hello");
        assertEquals(paragraph.getChild(1).getContent(), "bye Good");
    }
}
