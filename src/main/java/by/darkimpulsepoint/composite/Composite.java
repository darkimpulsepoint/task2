package by.darkimpulsepoint.composite;

import java.util.ArrayList;
import java.util.List;

public class Composite implements TextComponent {
    private final List<TextComponent> children = new ArrayList<>();
    private final ComponentType type;

    public Composite(ComponentType type) {
        this.type = type;
    }

    @Override
    public String getContent() {
        StringBuilder builder = new StringBuilder();
        String separator = getSeparator();

        for (int i = 0; i < children.size(); i++) {
            if (i > 0 && separator != null) {
                builder.append(separator);
            }
            builder.append(children.get(i).getContent());
        }
        return builder.toString();
    }

    @Override
    public void add(TextComponent component) {
        children.add(component);
    }

    @Override
    public TextComponent getChild(int index) {
        return children.get(index);
    }

    @Override
    public int getChildCount() {
        return children.size();
    }

    public ComponentType getType() {
        return type;
    }

    private String getSeparator() {
        return switch (type) {
            case TEXT -> "\n";
            case PARAGRAPH, SENTENCE -> " ";
            case LEXEME, WORD -> null;
        };
    }
}
