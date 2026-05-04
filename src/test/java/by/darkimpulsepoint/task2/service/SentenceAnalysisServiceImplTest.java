package by.darkimpulsepoint.task2.service;

import by.darkimpulsepoint.task2.composite.Composite;
import by.darkimpulsepoint.task2.composite.ComponentType;
import by.darkimpulsepoint.task2.composite.Leaf;
import by.darkimpulsepoint.task2.composite.TextComponent;
import by.darkimpulsepoint.task2.service.impl.SentenceAnalysisServiceImpl;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class SentenceAnalysisServiceImplTest {

    private SentenceAnalysisService service;

    @BeforeMethod
    public void setUp() {
        service = new SentenceAnalysisServiceImpl();
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

    private Composite createSentence(String... words) {
        Composite sentence = new Composite(ComponentType.SENTENCE);
        for (String word : words) {
            sentence.add(createLexeme(word));
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
    public void testFindSentencesWithSameWordsSimple() {
        Composite sentence1 = createSentence("Hello", "world");
        Composite sentence2 = createSentence("Hello", "there");
        Composite text = createText(sentence1, sentence2);

        TextComponent result = service.findSentencesWithSameWords(text);

        assertTrue(result.getChildCount() > 0);
        String content = result.getContent();
        assertTrue(content.contains("Hello"));
    }

    @Test
    public void testFindSentencesWithSameWordsMultiple() {
        Composite sentence1 = createSentence("The", "cat", "sat");
        Composite sentence2 = createSentence("The", "dog", "ran");
        Composite sentence3 = createSentence("The", "bird", "flew");
        Composite text = createText(sentence1, sentence2, sentence3);

        TextComponent result = service.findSentencesWithSameWords(text);

        assertTrue(result.getChildCount() > 0);
        String content = result.getContent();
        assertTrue(content.contains("The"));
    }

    @Test
    public void testFindSentencesWithSameWordsCaseInsensitive() {
        Composite sentence1 = createSentence("Hello", "World");
        Composite sentence2 = createSentence("hello", "there");
        Composite text = createText(sentence1, sentence2);

        TextComponent result = service.findSentencesWithSameWords(text);

        assertTrue(result.getChildCount() > 0);
    }

    @Test
    public void testFindSentencesWithSameWordsNoCommonWords() {
        Composite sentence1 = createSentence("Apple", "banana");
        Composite sentence2 = createSentence("Orange", "grape");
        Composite text = createText(sentence1, sentence2);

        TextComponent result = service.findSentencesWithSameWords(text);

        assertEquals(result.getContent(), text.getContent());
    }

    @Test
    public void testFindSentencesWithSameWordsEmptyText() {
        Composite text = new Composite(ComponentType.TEXT);

        TextComponent result = service.findSentencesWithSameWords(text);

        assertEquals(result.getChildCount(), 0);
    }

    @Test
    public void testFindSentencesWithSameWordsSingleSentence() {
        Composite sentence = createSentence("Hello", "world");
        Composite text = createText(sentence);

        TextComponent result = service.findSentencesWithSameWords(text);

        assertEquals(result.getContent(), text.getContent());
    }

    @Test
    public void testSortSentencesByLetterSimple() {
        Composite sentence1 = createSentence("Hello");
        Composite sentence2 = createSentence("World");
        Composite text = createText(sentence1, sentence2);

        TextComponent result = service.sortSentencesByLetter(text, 'l');

        assertTrue(result.getChildCount() > 0);
        Composite composite = (Composite) result;
        Composite paragraph = (Composite) composite.getChild(0);
        assertEquals(paragraph.getChildCount(), 2);
        assertEquals(paragraph.getChild(0).getContent(), "World");
        assertEquals(paragraph.getChild(1).getContent(), "Hello");
    }

    @Test
    public void testSortSentencesByLetterMultipleSentences() {
        Composite sentence1 = createSentence("Apple", "pie");
        Composite sentence2 = createSentence("Banana", "split");
        Composite sentence3 = createSentence("Cherry", "tart");
        Composite text = createText(sentence1, sentence2, sentence3);

        TextComponent result = service.sortSentencesByLetter(text, 'a');

        assertTrue(result.getChildCount() > 0);
        Composite composite = (Composite) result;
        Composite paragraph = (Composite) composite.getChild(0);
        assertEquals(paragraph.getChildCount(), 3);
        assertEquals(paragraph.getChild(0).getContent(), "Apple pie");
        assertEquals(paragraph.getChild(1).getContent(), "Cherry tart");
        assertEquals(paragraph.getChild(2).getContent(), "Banana split");
    }

    @Test
    public void testSortSentencesByLetterCaseInsensitive() {
        Composite sentence1 = createSentence("HELLO");
        Composite sentence2 = createSentence("hello");
        Composite text = createText(sentence1, sentence2);

        TextComponent result = service.sortSentencesByLetter(text, 'h');

        assertTrue(result.getChildCount() > 0);
    }

    @Test
    public void testSortSentencesByLetterNoOccurrences() {
        Composite sentence1 = createSentence("Hello");
        Composite sentence2 = createSentence("World");
        Composite text = createText(sentence1, sentence2);

        TextComponent result = service.sortSentencesByLetter(text, 'z');

        assertTrue(result.getChildCount() > 0);
    }

    @Test
    public void testSortSentencesByLetterEmptyText() {
        Composite text = new Composite(ComponentType.TEXT);

        TextComponent result = service.sortSentencesByLetter(text, 'a');

        assertEquals(result.getChildCount(), 1);
        Composite composite = (Composite) result;
        Composite paragraph = (Composite) composite.getChild(0);
        assertEquals(paragraph.getChildCount(), 0);
    }

    @Test
    public void testSortSentencesByLetterSingleSentence() {
        Composite sentence = createSentence("Hello", "world");
        Composite text = createText(sentence);

        TextComponent result = service.sortSentencesByLetter(text, 'o');

        assertTrue(result.getChildCount() > 0);
        Composite composite = (Composite) result;
        Composite paragraph = (Composite) composite.getChild(0);
        assertEquals(paragraph.getChildCount(), 1);
        assertEquals(paragraph.getChild(0).getContent(), "Hello world");
    }
}
