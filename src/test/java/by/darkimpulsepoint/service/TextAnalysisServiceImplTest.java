package by.darkimpulsepoint.service;

import by.darkimpulsepoint.composite.Composite;
import by.darkimpulsepoint.composite.ComponentType;
import by.darkimpulsepoint.composite.Leaf;
import by.darkimpulsepoint.composite.TextComponent;
import by.darkimpulsepoint.service.impl.TextAnalysisServiceImpl;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class TextAnalysisServiceImplTest {

    private TextAnalysisService service;

    @BeforeMethod
    public void setUp() {
        service = new TextAnalysisServiceImpl();
    }

    @Test
    public void testCountLettersInSimpleWord() {
        Composite word = new Composite(ComponentType.WORD);
        word.add(new Leaf('H'));
        word.add(new Leaf('e'));
        word.add(new Leaf('l'));
        word.add(new Leaf('l'));
        word.add(new Leaf('o'));

        int count = service.countLetters(word);

        assertEquals(count, 5);
    }

    @Test
    public void testCountLettersWithNumbers() {
        Composite word = new Composite(ComponentType.WORD);
        word.add(new Leaf('A'));
        word.add(new Leaf('1'));
        word.add(new Leaf('B'));
        word.add(new Leaf('2'));
        word.add(new Leaf('C'));

        int count = service.countLetters(word);

        assertEquals(count, 3);
    }

    @Test
    public void testCountLettersWithSpecialCharacters() {
        Composite word = new Composite(ComponentType.WORD);
        word.add(new Leaf('H'));
        word.add(new Leaf('!'));
        word.add(new Leaf('i'));
        word.add(new Leaf('@'));

        int count = service.countLetters(word);

        assertEquals(count, 2);
    }

    @Test
    public void testCountLettersInSentence() {
        Composite sentence = new Composite(ComponentType.SENTENCE);

        Composite word1 = new Composite(ComponentType.WORD);
        word1.add(new Leaf('H'));
        word1.add(new Leaf('i'));

        Composite lexeme1 = new Composite(ComponentType.LEXEME);
        lexeme1.add(word1);

        Composite word2 = new Composite(ComponentType.WORD);
        word2.add(new Leaf('t'));
        word2.add(new Leaf('h'));
        word2.add(new Leaf('e'));
        word2.add(new Leaf('r'));
        word2.add(new Leaf('e'));

        Composite lexeme2 = new Composite(ComponentType.LEXEME);
        lexeme2.add(word2);

        sentence.add(lexeme1);
        sentence.add(lexeme2);

        int count = service.countLetters(sentence);

        assertEquals(count, 7);
    }

    @Test
    public void testCountLettersEmptyComponent() {
        Composite empty = new Composite(ComponentType.WORD);

        int count = service.countLetters(empty);

        assertEquals(count, 0);
    }

    @Test
    public void testCountSymbolsInSimpleWord() {
        Composite word = new Composite(ComponentType.WORD);
        word.add(new Leaf('H'));
        word.add(new Leaf('e'));
        word.add(new Leaf('l'));
        word.add(new Leaf('l'));
        word.add(new Leaf('o'));

        int count = service.countSymbols(word);

        assertEquals(count, 5);
    }

    @Test
    public void testCountSymbolsWithSpecialCharacters() {
        Composite word = new Composite(ComponentType.WORD);
        word.add(new Leaf('H'));
        word.add(new Leaf('!'));
        word.add(new Leaf('i'));
        word.add(new Leaf('@'));
        word.add(new Leaf('1'));

        int count = service.countSymbols(word);

        assertEquals(count, 5);
    }

    @Test
    public void testCountSymbolsInSentence() {
        Composite sentence = new Composite(ComponentType.SENTENCE);

        Composite word1 = new Composite(ComponentType.WORD);
        word1.add(new Leaf('H'));
        word1.add(new Leaf('i'));
        word1.add(new Leaf('!'));

        Composite lexeme1 = new Composite(ComponentType.LEXEME);
        lexeme1.add(word1);

        sentence.add(lexeme1);

        int count = service.countSymbols(sentence);

        assertEquals(count, 3);
    }

    @Test
    public void testCountSymbolsEmptyComponent() {
        Composite empty = new Composite(ComponentType.WORD);

        int count = service.countSymbols(empty);

        assertEquals(count, 0);
    }

    @Test
    public void testCountLettersWithMixedCase() {
        Composite word = new Composite(ComponentType.WORD);
        word.add(new Leaf('H'));
        word.add(new Leaf('e'));
        word.add(new Leaf('L'));
        word.add(new Leaf('l'));
        word.add(new Leaf('O'));

        int count = service.countLetters(word);

        assertEquals(count, 5);
    }

    @Test
    public void testCountLettersInComplexText() {
        Composite text = new Composite(ComponentType.TEXT);
        Composite paragraph = new Composite(ComponentType.PARAGRAPH);
        Composite sentence = new Composite(ComponentType.SENTENCE);

        Composite word = new Composite(ComponentType.WORD);
        word.add(new Leaf('T'));
        word.add(new Leaf('e'));
        word.add(new Leaf('s'));
        word.add(new Leaf('t'));

        Composite lexeme = new Composite(ComponentType.LEXEME);
        lexeme.add(word);
        lexeme.add(new Leaf('!'));

        sentence.add(lexeme);
        paragraph.add(sentence);
        text.add(paragraph);

        int letterCount = service.countLetters(text);
        int symbolCount = service.countSymbols(text);

        assertEquals(letterCount, 4);
        assertEquals(symbolCount, 5);
    }

    @Test
    public void testDisplayLetterAndSymbolCount() {
        Composite word = new Composite(ComponentType.WORD);
        word.add(new Leaf('H'));
        word.add(new Leaf('i'));
        word.add(new Leaf('!'));

        service.displayLetterAndSymbolCount(word);
    }

    @Test
    public void testCountLettersWithUnicodeCharacters() {
        Composite word = new Composite(ComponentType.WORD);
        word.add(new Leaf('П'));
        word.add(new Leaf('р'));
        word.add(new Leaf('и'));
        word.add(new Leaf('в'));
        word.add(new Leaf('е'));
        word.add(new Leaf('т'));

        int count = service.countLetters(word);

        assertEquals(count, 6);
    }

    @Test
    public void testCountSymbolsWithWhitespace() {
        Composite word = new Composite(ComponentType.WORD);
        word.add(new Leaf('H'));
        word.add(new Leaf(' '));
        word.add(new Leaf('i'));

        int count = service.countSymbols(word);

        assertEquals(count, 3);
    }
}
