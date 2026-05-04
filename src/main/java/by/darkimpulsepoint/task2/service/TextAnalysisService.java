package by.darkimpulsepoint.task2.service;

import by.darkimpulsepoint.task2.composite.TextComponent;

public interface TextAnalysisService {
    int countLetters(TextComponent text);
    int countSymbols(TextComponent text);
    void displayLetterAndSymbolCount(TextComponent text);
}
