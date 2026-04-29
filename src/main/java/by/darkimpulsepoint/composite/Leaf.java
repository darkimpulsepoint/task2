package by.darkimpulsepoint.composite;

public class Leaf implements TextComponent {
    private final char value;

    public Leaf(char value) {
        this.value = value;
    }

    @Override
    public String getContent() {
        return String.valueOf(value);
    }

    @Override
    public void add(TextComponent component) {
        throw new UnsupportedOperationException("Cannot add to leaf");
    }

    @Override
    public TextComponent getChild(int index) {
        throw new UnsupportedOperationException("Leaf has no children");
    }

    @Override
    public int getChildCount() {
        return 0;
    }

    public char getValue() {
        return value;
    }
}
