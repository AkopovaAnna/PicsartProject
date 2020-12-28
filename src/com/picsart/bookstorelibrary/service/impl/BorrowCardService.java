package com.picsart.bookstorelibrary.service.impl;

import com.picsart.bookstorelibrary.exception.CustomValidationException;
import com.picsart.bookstorelibrary.model.BorrowerCard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.picsart.bookstorelibrary.validator.BorrowerCardValidator.validateCardNumber;

public class BorrowCardService {

    private static final String CARD_FILE_PATH = "src/com/picsart/bookstorelibrary/resources/cards.txt";
    private static BorrowCardService cardService;
    private FileProcessingService fileService;

    private BorrowCardService() {
        this.fileService = FileProcessingService.getInstance();
    }

    public static BorrowCardService getInstance() {
        if (cardService == null) {
            cardService = new BorrowCardService();
        }
        return cardService;
    }

    public void addBorrower(BorrowerCard card) throws IOException, CustomValidationException {
        validateCard(card);
        card.setBorrowCardId(calculateBorrowCardId());
        fileService.exportDataToFile(CARD_FILE_PATH, card.toString());
        System.out.println("Borrow Card was created");
    }

    public void validateCard(BorrowerCard card) throws CustomValidationException, IOException {
        validateCardNumber(card.getCardNumb());
        validateUniqueCard(card.getCardNumb());
    }

    public void validateUniqueCard(String cardNum) throws IOException, CustomValidationException {
        List<BorrowerCard> cards = getCards();
        for (BorrowerCard card : cards) {
            if (card.getCardNumb().equals(cardNum)) {
                throw new CustomValidationException("card already exists");
            }
        }
    }

    private Integer calculateBorrowCardId() throws IOException {
        List<BorrowerCard> cards = getCards();
        Integer id = 0;
        if (cards.size() > 0) {
            id = cards.get(cards.size() - 1).getBorrowCardId();
        }
        return id + 1;
    }

    public List<BorrowerCard> getCards() throws IOException {
        List<String> cardContent = fileService.readFromFile(CARD_FILE_PATH);
        return convertToCard(cardContent);
    }

    public List<BorrowerCard> convertToCard(List<String> lines) {
        List<BorrowerCard> cards = new ArrayList<>();
        for (String card : lines) {
            cards.add(createCard(card));
        }
        return cards;
    }

    private static BorrowerCard createCard(String data) {
        String[] tokens = data.split(",");
        BorrowerCard card = new BorrowerCard();
        card.setBorrowCardId(Integer.parseInt(tokens[0]));
        card.setCardNumb(tokens[1]);
        card.setBalance(Double.parseDouble(tokens[2]));
        return card;
    }

    public BorrowerCard getByCardNum(String cardNum) throws IOException, CustomValidationException {
        List<BorrowerCard> cards = getCards();
        for (BorrowerCard card : cards) {
            if (card.getCardNumb().equals(cardNum)) {
                return card;
            }
        }
        throw new CustomValidationException("Card with that number not exist");
    }
}
