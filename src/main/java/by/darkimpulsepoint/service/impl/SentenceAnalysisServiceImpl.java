package by.darkimpulsepoint.service.impl;

import by.darkimpulsepoint.composite.Composite;
import by.darkimpulsepoint.composite.ComponentType;
import by.darkimpulsepoint.composite.Leaf;
import by.darkimpulsepoint.composite.TextComponent;
import by.darkimpulsepoint.service.SentenceAnalysisService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class SentenceAnalysisServiceImpl implements SentenceAnalysisService {
    private static final Logger logger = LogManager.getLogger(SentenceAnalysisServiceImpl.class);

    @Override
    public void findSentencesWithSameWords(TextComponent text) {
        List<Composite> sentences = extractSentences(text);
        Map<String, List<Integer>> wordToSentences = new HashMap<>();

        for (int i = 0; i < sentences.size(); i++) {
            Set<String> words = extractWords(sentences.get(i));
            for (String word : words) {
                wordToSentences.computeIfAbsent(word.toLowerCase(), k -> new ArrayList<>()).add(i);
            }
        }

        Map<String, Integer> wordOccurrences = new HashMap<>();
        for (Map.Entry<String, List<Integer>> entry : wordToSentences.entrySet()) {
            if (entry.getValue().size() > 1) {
                wordOccurrences.put(entry.getKey(), entry.getValue().size());
            }
        }

        int maxCount = 0;
        for (int count : wordOccurrences.values()) {
            if (count > maxCount) {
                maxCount = count;
            }
        }

        logger.info("Maximum sentences with same word: {}", maxCount);

        for (Map.Entry<String, Integer> entry : wordOccurrences.entrySet()) {
            if (entry.getValue() == maxCount) {
                logger.info("Word '{}' appears in {} sentences:", entry.getKey(), entry.getValue());
                List<Integer> sentenceIndices = wordToSentences.get(entry.getKey());
                for (int idx : sentenceIndices) {
                    logger.info("  - {}", sentences.get(idx).getContent());
                }
            }
        }
    }

    @Override
    public void sortSentencesByLetter(TextComponent text, char targetLetter) {
        List<Composite> sentences = extractSentences(text);
        List<SentenceWithCount> sentencesWithCount = new ArrayList<>();

        for (Composite sentence : sentences) {
            int count = countLetter(sentence, targetLetter);
            sentencesWithCount.add(new SentenceWithCount(sentence, count));
        }

        sentencesWithCount.sort(Comparator.comparingInt(a -> a.count));

        logger.info("Sorted {} sentences by letter '{}'", sentences.size(), targetLetter);
        for (SentenceWithCount swc : sentencesWithCount) {
            logger.info("({}) {}", swc.count, swc.sentence.getContent());
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

    private Set<String> extractWords(TextComponent component) {
        Set<String> words = new HashSet<>();
        if (component instanceof Composite composite) {
            if (composite.getType() == ComponentType.WORD) {
                words.add(component.getContent());
            } else {
                for (int i = 0; i < component.getChildCount(); i++) {
                    words.addAll(extractWords(component.getChild(i)));
                }
            }
        }
        return words;
    }

    private int countLetter(TextComponent component, char letter) {
        int count = 0;
        if (component instanceof Leaf) {
            char c = ((Leaf) component).getValue();
            if (Character.toLowerCase(c) == Character.toLowerCase(letter)) {
                count++;
            }
        } else {
            for (int i = 0; i < component.getChildCount(); i++) {
                count += countLetter(component.getChild(i), letter);
            }
        }
        return count;
    }

    private static class SentenceWithCount {
        final Composite sentence;
        final int count;

        SentenceWithCount(Composite sentence, int count) {
            this.sentence = sentence;
            this.count = count;
        }
    }
}
