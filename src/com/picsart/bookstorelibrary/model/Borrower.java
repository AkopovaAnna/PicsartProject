package com.picsart.bookstorelibrary.model;

import java.util.StringJoiner;

public class Borrower {

    private Integer borrowerID;
    private Integer borrowCardId;
    private String name;
    private String address;
    private String phoneNumber;


    public Integer getBorrowerID() {
        return borrowerID;
    }

    public void setBorrowerID(Integer borrowerID) {
        this.borrowerID = borrowerID;
    }

    public Integer getBorrowCardId() {
        return borrowCardId;
    }

    public void setBorrowCardId(Integer borrowCardId) {
        this.borrowCardId = borrowCardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(",", "", "\n");
        joiner.add(getBorrowerID().toString());
        joiner.add(getBorrowCardId().toString());
        joiner.add(getName());
        joiner.add(getAddress());
        joiner.add(getPhoneNumber());

        return joiner.toString();
    }

}
