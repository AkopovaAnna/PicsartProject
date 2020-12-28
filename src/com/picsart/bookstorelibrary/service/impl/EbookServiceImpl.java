package com.picsart.bookstorelibrary.service.impl;

import com.picsart.bookstorelibrary.exception.CustomValidationException;
import com.picsart.bookstorelibrary.model.Book;
import com.picsart.bookstorelibrary.model.Ebook;
import com.picsart.bookstorelibrary.model.Genre;
import com.picsart.bookstorelibrary.service.EbookService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class EbookServiceImpl implements EbookService {

    private static final String EBOOK_FILE_PATH = "src/com/picsart/bookstorelibrary/resources/eBook.txt";
    private static EbookServiceImpl ebookService;
    private FileProcessingService fileService;

    private EbookServiceImpl() {
        this.fileService = FileProcessingService.getInstance();
    }

    public static EbookServiceImpl getInstance() {
        if (ebookService == null) {
            ebookService = new EbookServiceImpl();
        }
        return ebookService;
    }


    @Override
    public void addBook(Book book) throws IOException {
        Ebook ebook = (Ebook) book;
        ebook.setBookId(calculateEbookId());
        fileService.exportDataToFile(EBOOK_FILE_PATH, ebook.toString());
        System.out.println("Book was added");
    }

    private Integer calculateEbookId() throws IOException {
        List<Ebook> eBooks = getEBooks();
        Integer id = 0;
        if (eBooks.size() > 0) {
            id = eBooks.get(eBooks.size() - 1).getBookId();
        }
        return id + 1;
    }

    @Override
    public Double calculatePrice(Double price) {
        if (LocalDate.now().getMonthValue() == 9 && LocalDate.now().getDayOfMonth() == 12) {
            return (price - price * 0.2);
        }
        return price;
    }

    @Override
    public void updateBook(Book book) throws IOException {
        List<Ebook> oldEbookList = getEBooks();
        StringBuilder updatedEbookList = new StringBuilder();
        for (Ebook eb : oldEbookList) {
            Ebook ebook = (Ebook) book;
            if (eb.getBookId().equals(ebook.getBookId())) {
                eb.setDownloadedCount(ebook.getDownloadedCount());
            }
            updatedEbookList.append(eb.toString());
        }
        fileService.updateFile(EBOOK_FILE_PATH, updatedEbookList.toString());
    }

    @Override
    public void preview(Integer bookId) throws IOException, CustomValidationException {
        Ebook ebook = (Ebook) getBookById(bookId);
        System.out.println("you can preview the content of the book " + ebook.getTitle());
    }

    @Override
    public void download(Integer bookId) throws IOException, CustomValidationException {

        Ebook ebook = (Ebook) getBookById(bookId);
        int newCount = ebook.getDownloadedCount();
        ebook.setDownloadedCount(++newCount);
        ebookService.updateBook(ebook);
    }


    public List<Ebook> getEBooks() throws IOException {
        List<String> usersContent = fileService.readFromFile(EBOOK_FILE_PATH);
        return convertToBook(usersContent);
    }

    public List<Ebook> convertToBook(List<String> lines) {

        List<Ebook> ebooks = new ArrayList<>();
        for (String ebook : lines) {
            ebooks.add(createEBook(ebook));
        }
        return ebooks;
    }

    public static Ebook createEBook(String data) {
        String[] tokens = data.split(",");
        Ebook eBook = new Ebook();
        eBook.setBookId(Integer.parseInt(tokens[0]));
        eBook.setTitle(tokens[1]);
        eBook.setAuthor(tokens[2]);
        eBook.setLanguage(tokens[3]);
        eBook.setPrice(Double.parseDouble(tokens[4]));
        eBook.setExtensionType(tokens[5]);
        eBook.setSize(Double.parseDouble(tokens[6]));
        eBook.setDownloadedCount(Integer.parseInt(tokens[7]));
        eBook.setGenre(Genre.valueOf(tokens[8]));
        return eBook;
    }

    @Override
    public Book getBookById(Integer bookId) throws IOException, CustomValidationException {
        List<Ebook> ebooks = getEBooks();
        for (Book ebook : ebooks) {
            if (ebook.getBookId().equals(bookId)) {
                return ebook;
            }
        }
        throw new CustomValidationException("Book Not Found");
    }

    public String getMaxDownloaded() throws IOException {
        List<Ebook> ebooks = getEBooks();
        Map<Ebook, Integer> downloadedBooks = new HashMap<>();
        Ebook ebook;
        for (Book book : ebooks) {
            ebook = (Ebook) book;
            downloadedBooks.put(ebook, ebook.getDownloadedCount());
        }

        Integer max = Collections.max(downloadedBooks.values());
        List<Ebook> keys = new ArrayList<>();
        for (Map.Entry<Ebook, Integer> entry : downloadedBooks.entrySet()) {
            if (entry.getValue().equals(max)) {
                keys.add(entry.getKey());
            }
        }
        return keys.get(0).getTitle();
    }

    public void printEBookInfoById(Integer id) throws IOException, CustomValidationException {
        Ebook ebook = (Ebook) getBookById(id);
        System.out.println(ebook.toString());
    }
}
