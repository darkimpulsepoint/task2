package by.darkimpulsepoint.service;

import by.darkimpulsepoint.composite.TextComponent;

public interface TextAnalysisService {
    int countLetters(TextComponent text);
    int countSymbols(TextComponent text);
    void displayLetterAndSymbolCount(TextComponent text);
}
