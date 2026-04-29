package by.darkimpulsepoint.service;

import by.darkimpulsepoint.composite.TextComponent;

public interface SentenceAnalysisService {
    void findSentencesWithSameWords(TextComponent text);
    void sortSentencesByLetter(TextComponent text, char targetLetter);
}
