package ru.task.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import ru.task.dao.SiteDAO;
import ru.task.dao.WordDAO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Site {
    private Long id;
    private String url;
    private List<Word> words = new ArrayList<>();

    public Site() {
    }

    public Site(String url) throws IOException {
        Document page = Jsoup.connect(url).get();
        String html = page.outerHtml();
        String fileName = getFileName(page.head().text()) + ".html";
        this.url = url;
        this.id = SiteDAO.insert(this.url);
        String text = page.body()
                .text()
                .replaceAll("[\\{\\',\\.\\!\\?\";:\\[\\]\\(\\)\\n\\r\\t0-9]+", "")
                .replaceAll("\\s+", " ")
                .toLowerCase(Locale.ROOT);

        saveFile(html, fileName);
        toFillListOfWords(this.id, text);
        words.stream().forEach(word -> WordDAO.insert(word));
    }

    private void toFillListOfWords(Long id, String text) {
        String[] array = text.split(" ");
        HashMap<String, Long> dictionary = new HashMap<>();

        for (String str : array)
            if (dictionary.containsKey(str))
                dictionary.put(str, dictionary.get(str) + 1);
            else
                dictionary.put(str, (long) 1);

        for (Map.Entry<String, Long> pair : dictionary.entrySet())
            words.add(new Word(this.id, pair.getKey(), pair.getValue()));
    }

    public void addWord(Word word) {
        words.add(word);
    }

    private void saveFile(String html, String fileName) {
        String relativePath = "filesDirectory" + File.separator + fileName;
        File file = new File(relativePath);

        try {
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file, false);
            fileWriter.write(html);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String getFileName(String text) {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(text.replaceAll("[\\s\\.,\\-]+", " ")
                .trim()
                .split(" "))
                .map(t -> t = t.substring(0, 1).toUpperCase(Locale.ROOT) + t.substring(1))
                .forEach(sb::append);

        return sb.toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }
}

