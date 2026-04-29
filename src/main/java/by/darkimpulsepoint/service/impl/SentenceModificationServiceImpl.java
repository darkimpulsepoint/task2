package by.darkimpulsepoint.service.impl;

import by.darkimpulsepoint.composite.Composite;
import by.darkimpulsepoint.composite.ComponentType;
import by.darkimpulsepoint.composite.TextComponent;
import by.darkimpulsepoint.service.SentenceModificationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class SentenceModificationServiceImpl implements SentenceModificationService {
    private static final Logger logger = LogManager.getLogger(SentenceModificationServiceImpl.class);

    @Override
    public void swapFirstLastLexeme(TextComponent text) {
        List<Composite> sentences = extractSentences(text);

        logger.info("Swapping first and last lexemes in {} sentences", sentences.size());

        for (Composite sentence : sentences) {
            List<Composite> lexemes = extractLexemes(sentence);
            if (lexemes.size() >= 2) {
                Composite modifiedSentence = new Composite(ComponentType.SENTENCE);
                modifiedSentence.add(lexemes.get(lexemes.size() - 1));
                for (int i = 1; i < lexemes.size() - 1; i++) {
                    modifiedSentence.add(lexemes.get(i));
                }
                modifiedSentence.add(lexemes.get(0));
                logger.info("{}", modifiedSentence.getContent());
            } else {
                logger.info("{}", sentence.getContent());
            }
        }
    }

    private List<Composite> extractSentences(TextComponent component) {
        List<Composite> sentences = new ArrayList<>();
        if (component instanceof Composite composite) {
            if (composite.getType() == ComponentType.SENTENCE) {
                sentences.add(composite);
            } else {
                for (int i = 0; i < component.getChildCount(); i++) {
                    sentences.addAll(extractSentences(component.getChild(i)));
                }
            }
        }
        return sentences;
    }

    private List<Composite> extractLexemes(Composite sentence) {
        List<Composite> lexemes = new ArrayList<>();
        for (int i = 0; i < sentence.getChildCount(); i++) {
            TextComponent child = sentence.getChild(i);
            if (child instanceof Composite && ((Composite) child).getType() == ComponentType.LEXEME) {
                lexemes.add((Composite) child);
            }
        }
        return lexemes;
    }
}
