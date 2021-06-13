package ru.task.model;

import java.util.Objects;

public class Word {

    private Long siteId;
    private String word;
    private Long countOfWord;

    public Word(Long siteId, String word, Long countOfWord) {
        this.siteId = siteId;
        this.word = word;
        this.countOfWord = countOfWord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word1 = (Word) o;
        return Objects.equals(siteId, word1.siteId) && Objects.equals(word, word1.word) && Objects.equals(countOfWord, word1.countOfWord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(siteId, word, countOfWord);
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Long getCountOfWord() {
        return countOfWord;
    }

    public void setCountOfWord(Long countOfWord) {
        this.countOfWord = countOfWord;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long site_id) {
        this.siteId = siteId;
    }
}
