package com.picsart.bookstorelibrary.model;

import java.util.StringJoiner;

public class BorrowerCard {

    private Integer borrowCardId;
    private String cardNumb;
    private Double balance;

    public BorrowerCard() {
        this.balance = 0D;
    }

    public Integer getBorrowCardId() {
        return borrowCardId;
    }

    public void setBorrowCardId(Integer borrowCardId) {
        this.borrowCardId = borrowCardId;
    }

    public String getCardNumb() {
        return cardNumb;
    }

    public void setCardNumb(String cardNumb) {
        this.cardNumb = cardNumb;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(",", "", "\n");
        joiner.add(String.valueOf(getBorrowCardId()));
        joiner.add(getCardNumb());
        joiner.add(String.valueOf(getBalance()));
        //enum

        return joiner.toString();
    }
}
