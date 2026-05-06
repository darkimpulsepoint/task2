package by.darkimpulsepoint.task2.reader;

import by.darkimpulsepoint.task2.exception.TextProcessingException;
import by.darkimpulsepoint.task2.reader.impl.TextFileTextReaderImpl;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.testng.Assert.*;

public class TextFileTextReaderImplTest {

    private TextReader textReader;
    private Path tempFile;

    @BeforeMethod
    public void setUp() {
        textReader = new TextFileTextReaderImpl();
    }

    @AfterMethod
    public void tearDown() throws IOException {
        if (tempFile != null && Files.exists(tempFile)) {
            Files.delete(tempFile);
        }
    }

    @Test
    public void testReadValidFile() throws IOException, TextProcessingException {
        tempFile = Files.createTempFile("test", ".txt");
        String content = "Hello World!\nThis is a test.";
        Files.writeString(tempFile, content);

        String result = textReader.read(tempFile.toString());

        assertEquals(result, content);
    }

    @Test
    public void testReadEmptyFile() throws IOException, TextProcessingException {
        tempFile = Files.createTempFile("empty", ".txt");
        Files.writeString(tempFile, "");

        String result = textReader.read(tempFile.toString());

        assertEquals(result, "");
    }

    @Test
    public void testReadFileWithSpecialCharacters() throws IOException, TextProcessingException {
        tempFile = Files.createTempFile("special", ".txt");
        String content = "Special chars: @#$%^&*()!\nNew line\tTab";
        Files.writeString(tempFile, content);

        String result = textReader.read(tempFile.toString());

        assertEquals(result, content);
    }

    @Test
    public void testReadFileWithUnicodeCharacters() throws IOException, TextProcessingException {
        tempFile = Files.createTempFile("unicode", ".txt");
        String content = "Привет мир! 你好世界! مرحبا بالعالم!";
        Files.writeString(tempFile, content);

        String result = textReader.read(tempFile.toString());

        assertEquals(result, content);
    }

    @Test
    public void testReadLargeFile() throws IOException, TextProcessingException {
        tempFile = Files.createTempFile("large", ".txt");
        StringBuilder largeContent = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            largeContent.append("Line ").append(i).append(": This is a test line.\n");
        }
        Files.writeString(tempFile, largeContent.toString());

        String result = textReader.read(tempFile.toString());

        assertEquals(result, largeContent.toString());
    }

    @Test(expectedExceptions = TextProcessingException.class)
    public void testReadNonExistentFile() throws TextProcessingException {
        textReader.read("/non/existent/file.txt");
    }

    @Test(expectedExceptions = {TextProcessingException.class, IOException.class})
    public void testReadDirectory() throws IOException, TextProcessingException {
        Path tempDir = Files.createTempDirectory("testdir");
        try {
            textReader.read(tempDir.toString());
        } finally {
            Files.delete(tempDir);
        }
    }

    @Test(expectedExceptions = {TextProcessingException.class, NullPointerException.class})
    public void testReadNullPath() throws TextProcessingException {
        textReader.read(null);
    }

    @Test
    public void testReadFileWithMultipleParagraphs() throws IOException, TextProcessingException {
        tempFile = Files.createTempFile("paragraphs", ".txt");
        String content = "First paragraph.\n\nSecond paragraph.\n\nThird paragraph.";
        Files.writeString(tempFile, content);

        String result = textReader.read(tempFile.toString());

        assertEquals(result, content);
    }

    @Test
    public void testReadFileWithOnlyWhitespace() throws IOException, TextProcessingException {
        tempFile = Files.createTempFile("whitespace", ".txt");
        String content = "   \n\t\n   ";
        Files.writeString(tempFile, content);

        String result = textReader.read(tempFile.toString());

        assertEquals(result, content);
    }
}
