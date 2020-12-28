package com.picsart.bookstorelibrary.model;

import com.picsart.bookstorelibrary.utils.DateUtils;

import java.util.Date;
import java.util.StringJoiner;

public class BookRent {

    public static final int EXPIRATION_DUE_DATE = 20;
    public static final double PER_DAY_FINE = 1000;

    private Integer bookRentId;
    private Integer bookId;
    private Integer borrowerID;
    private Date rentDay;

    public Integer getBookRentId() {
        return bookRentId;
    }

    public void setBookRentId(Integer bookRentId) {
        this.bookRentId = bookRentId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getBorrowerID() {
        return borrowerID;
    }

    public void setBorrowerID(Integer borrowerID) {
        this.borrowerID = borrowerID;
    }

    public Date getRentDay() {
        return rentDay;
    }

    public void setRentDay(Date rentDay) {
        this.rentDay = rentDay;
    }


    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(",", "", "\n");
        joiner.add(getBookRentId().toString());
        joiner.add(getBookId().toString());
        joiner.add(getBorrowerID().toString());
        joiner.add(DateUtils.convertDateToString(getRentDay()));

        return joiner.toString();
    }
}
