package by.darkimpulsepoint.task2.service;

import by.darkimpulsepoint.task2.composite.TextComponent;

public interface SentenceAnalysisService {
    TextComponent findSentencesWithSameWords(TextComponent text);
    TextComponent sortSentencesByLetter(TextComponent text, char targetLetter);
}
