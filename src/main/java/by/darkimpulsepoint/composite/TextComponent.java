package by.darkimpulsepoint.composite;

public interface TextComponent {
    String getContent();
    void add(TextComponent component);
    TextComponent getChild(int index);
    int getChildCount();
}
