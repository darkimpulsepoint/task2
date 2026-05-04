package by.darkimpulsepoint.service.impl;

import by.darkimpulsepoint.composite.Composite;
import by.darkimpulsepoint.composite.ComponentType;
import by.darkimpulsepoint.composite.Leaf;
import by.darkimpulsepoint.composite.TextComponent;
import by.darkimpulsepoint.service.SentenceAnalysisService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

public class SentenceAnalysisServiceImpl implements SentenceAnalysisService {
    private static final Logger logger = LogManager.getLogger(SentenceAnalysisServiceImpl.class);

    @Override
    public TextComponent findSentencesWithSameWords(TextComponent text) {
        List<Composite> sentences = extractSentences(text);
        Map<String, Set<Integer>> wordToSentenceIndices = new HashMap<>();

        for (int i = 0; i < sentences.size(); i++) {
            Set<String> words = extractWords(sentences.get(i));
            for (String word : words) {
                wordToSentenceIndices.computeIfAbsent(word.toLowerCase(), k -> new HashSet<>()).add(i);
            }
        }

        Map<String, List<Composite>> result = new HashMap<>();
        for (Map.Entry<String, Set<Integer>> entry : wordToSentenceIndices.entrySet()) {
            if (entry.getValue().size() > 1) {
                List<Composite> sentencesWithWord = new ArrayList<>();
                for (int idx : entry.getValue()) {
                    sentencesWithWord.add(sentences.get(idx));
                }
                result.put(entry.getKey(), sentencesWithWord);
            }
        }

        if (result.isEmpty()) {
            logger.info("Maximum sentences with same word: 0");
            return text;
        }

        int maxCount = result.values().stream()
                .mapToInt(List::size)
                .max()
                .orElse(0);

        logger.info("Maximum sentences with same word: {}", maxCount);

        Composite resultText = new Composite(ComponentType.TEXT);
        for (Map.Entry<String, List<Composite>> entry : result.entrySet()) {
            if (entry.getValue().size() == maxCount) {
                logger.info("Word '{}' appears in {} sentences:", entry.getKey(), entry.getValue().size());
                Composite paragraph = new Composite(ComponentType.PARAGRAPH);
                for (Composite sentence : entry.getValue()) {
                    logger.info("  - {}", sentence.getContent());
                    paragraph.add(sentence);
                }
                resultText.add(paragraph);
            }
        }

        return resultText;
    }

    @Override
    public TextComponent sortSentencesByLetter(TextComponent text, char targetLetter) {
        List<Composite> sentences = extractSentences(text);
        List<SentenceWithCount> sentencesWithCount = new ArrayList<>();

        for (Composite sentence : sentences) {
            int count = countLetter(sentence, targetLetter);
            sentencesWithCount.add(new SentenceWithCount(sentence, count));
        }

        sentencesWithCount.sort(Comparator.comparingInt(a -> a.count));

        logger.info("Sorted {} sentences by letter '{}'", sentences.size(), targetLetter);

        Composite resultText = new Composite(ComponentType.TEXT);
        Composite paragraph = new Composite(ComponentType.PARAGRAPH);

        for (SentenceWithCount swc : sentencesWithCount) {
            logger.info("({}) {}", swc.count, swc.sentence.getContent());
            paragraph.add(swc.sentence);
        }

        resultText.add(paragraph);
        return resultText;
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
