package com.picsart.bookstorelibrary.model;


import java.util.StringJoiner;

public class Ebook extends Book {

    private String extensionType;
    private Double size;
    private Integer downloadedCount;


    public Ebook() {
        this.downloadedCount = 0;
        this.size = 0.0;

    }

    public Integer getDownloadedCount() {
        return downloadedCount;
    }

    public void setDownloadedCount(Integer downloadedCount) {
        this.downloadedCount = downloadedCount;
    }

    public String getExtensionType() {
        return extensionType;
    }

    public void setExtensionType(String extensionType) {
        this.extensionType = extensionType;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }


    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(",", "", "\n");
        joiner.add(getBookId().toString());
        joiner.add(getTitle());
        joiner.add(getAuthor());
        joiner.add(getLanguage());
        joiner.add(getPrice().toString());
        joiner.add(getExtensionType());
        joiner.add(getSize().toString());
        joiner.add(getDownloadedCount().toString());
        joiner.add(getGenre().name());

        return joiner.toString();
    }
}
