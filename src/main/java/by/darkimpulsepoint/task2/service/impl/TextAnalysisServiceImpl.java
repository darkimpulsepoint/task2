package by.darkimpulsepoint.task2.service.impl;

import by.darkimpulsepoint.task2.composite.Leaf;
import by.darkimpulsepoint.task2.composite.TextComponent;
import by.darkimpulsepoint.task2.service.TextAnalysisService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TextAnalysisServiceImpl implements TextAnalysisService {
    private static final Logger logger = LogManager.getLogger(TextAnalysisServiceImpl.class);

    @Override
    public int countLetters(TextComponent text) {
        return countInComponent(text, true);
    }

    @Override
    public int countSymbols(TextComponent text) {
        return countInComponent(text, false);
    }

    private int countInComponent(TextComponent component, boolean countLetters) {
        int count = 0;
        if (component instanceof Leaf) {
            char c = ((Leaf) component).getValue();
            if (countLetters) {
                if (Character.isLetter(c)) {
                    count++;
                }
            } else {
                count++;
            }
        } else {
            for (int i = 0; i < component.getChildCount(); i++) {
                count += countInComponent(component.getChild(i), countLetters);
            }
        }
        return count;
    }

    @Override
    public void displayLetterAndSymbolCount(TextComponent text) {
        int letterCount = countLetters(text);
        int symbolCount = countSymbols(text);

        logger.info("Letter count: {}, Symbol count: {}", letterCount, symbolCount);
    }
}
