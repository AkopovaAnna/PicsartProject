package com.picsart.bookstorelibrary.model;

import com.picsart.bookstorelibrary.utils.DateUtils;

import java.util.Date;
import java.util.StringJoiner;

public class BookSell {

    public static final int DUE_DATE = 14;

    private Integer bookSellId;
    private Integer bookId;
    private Date dateOfPurchase;
    private Double sellPrice;
    private Integer sellQuantity;


    public BookSell() {
        this.sellPrice = 0.0;
        this.sellQuantity = 0;
    }

    public Integer getBookSellId() {
        return bookSellId;
    }

    public void setBookSellId(Integer bookSellId) {
        this.bookSellId = bookSellId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Date getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(Date dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Integer getSellQuantity() {
        return sellQuantity;
    }

    public void setSellQuantity(Integer sellQuantity) {
        this.sellQuantity = sellQuantity;
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(",", "", "\n");
        joiner.add(getBookSellId().toString());
        joiner.add(getBookId().toString());
        joiner.add(DateUtils.convertDateToString(getDateOfPurchase()));
        joiner.add(getSellPrice().toString());
        joiner.add(getSellQuantity().toString());

        return joiner.toString();
    }

}
