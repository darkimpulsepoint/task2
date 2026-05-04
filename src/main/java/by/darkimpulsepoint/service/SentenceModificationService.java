package by.darkimpulsepoint.service;

import by.darkimpulsepoint.composite.Composite;
import by.darkimpulsepoint.composite.TextComponent;

import java.util.List;

public interface SentenceModificationService {
    TextComponent swapFirstLastLexeme(TextComponent text);
}
