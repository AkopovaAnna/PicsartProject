package com.picsart.bookstorelibrary.model;


import java.util.StringJoiner;

public class PaperBook extends Book {

    private String material;
    private Boolean isSellable;//rent or sell
    private Integer numberOfCopies;


    public Integer getNumberOfCopies() {
        return numberOfCopies;
    }

    public void setNumberOfCopies(Integer numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public Boolean isSellable() {
        return isSellable;
    }

    public void setSellable(Boolean sellable) {
        isSellable = sellable;
    }


    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(",", "", "\n");
        joiner.add(getBookId().toString());
        joiner.add(getTitle());
        joiner.add(getAuthor());
        joiner.add(getLanguage());
        joiner.add(getPrice().toString());
        joiner.add(getMaterial());
        joiner.add(isSellable().toString());
        joiner.add(getNumberOfCopies().toString());
        joiner.add(getGenre().name());

        return joiner.toString();
    }

}
