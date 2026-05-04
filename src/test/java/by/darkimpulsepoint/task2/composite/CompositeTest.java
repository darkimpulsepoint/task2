package by.darkimpulsepoint.task2.composite;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class CompositeTest {

    @Test
    public void testLeafCreation() {
        Leaf leaf = new Leaf('A');
        assertEquals(leaf.getContent(), "A");
        assertEquals(leaf.getValue(), 'A');
    }

    @Test
    public void testCompositeWordCreation() {
        Composite word = new Composite(ComponentType.WORD);
        word.add(new Leaf('H'));
        word.add(new Leaf('i'));
        assertEquals(word.getContent(), "Hi");
    }

    @Test
    public void testCompositeSentenceCreation() {
        Composite sentence = new Composite(ComponentType.SENTENCE);
        Composite word1 = new Composite(ComponentType.WORD);
        word1.add(new Leaf('H'));
        word1.add(new Leaf('i'));

        Composite lexeme = new Composite(ComponentType.LEXEME);
        lexeme.add(word1);

        sentence.add(lexeme);
        assertEquals(sentence.getContent(), "Hi");
    }

    @Test
    public void testCompositeChildCount() {
        Composite composite = new Composite(ComponentType.TEXT);
        composite.add(new Composite(ComponentType.PARAGRAPH));
        composite.add(new Composite(ComponentType.PARAGRAPH));
        assertEquals(composite.getChildCount(), 2);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void testLeafCannotAddChildren() {
        Leaf leaf = new Leaf('A');
        leaf.add(new Leaf('B'));
    }
}
