package by.darkimpulsepoint.parser;

public final class RegexPatterns {
    public static final String PARAGRAPH_PATTERN = "(?m)^.+$";
    public static final String SENTENCE_PATTERN = "[^.!?]+[.!?]+";
    public static final String LEXEME_PATTERN = "\\S+";
    public static final String WORD_PATTERN = "[a-zA-Zа-яА-ЯёЁ]+";

    private RegexPatterns() {
    }
}
