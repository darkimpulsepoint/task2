package by.darkimpulsepoint.task2.composite;

public interface TextComponent {
    String getContent();
    void add(TextComponent component);
    TextComponent getChild(int index);
    int getChildCount();
}
