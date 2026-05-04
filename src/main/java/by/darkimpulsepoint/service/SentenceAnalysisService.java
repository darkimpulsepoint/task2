package by.darkimpulsepoint.service;

import by.darkimpulsepoint.composite.TextComponent;

public interface SentenceAnalysisService {
    TextComponent findSentencesWithSameWords(TextComponent text);
    TextComponent sortSentencesByLetter(TextComponent text, char targetLetter);
}
