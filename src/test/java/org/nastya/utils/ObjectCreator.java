package org.nastya.utils;

import org.nastya.entity.*;

import java.time.LocalDate;

public class ObjectCreator {

    public static Author createAuthor(String name, String birthDate, String deathDate, Gender gender, Country country) {
        Author author = new Author();
        author.setName(name);
        if (birthDate != null) {
            LocalDate date = LocalDate.parse(birthDate);
            author.setBirthDate(date);
        }
        if (deathDate != null) {
            LocalDate dateOfDeath = LocalDate.parse(deathDate);
            author.setDeathDate(dateOfDeath);
        }
        author.setGender(gender);
        author.setCountry(country);
        return author;
    }

    public static Book createBook(String title, String publishingYear, Genre genre) {
        Book book = new Book();
        book.setTitle(title);
        if (publishingYear != null) {
            int date = Integer.parseInt(publishingYear);
            book.setPublishingYear(date);
        }
        book.setGenre(genre);
        return book;
    }

    public static AuthorToBook createAuthorToBook(int authorId, int bookId) {
        AuthorToBook authorToBook = new AuthorToBook();
        authorToBook.setAuthorId(authorId);
        authorToBook.setBookId(bookId);
        return authorToBook;
    }

    public static BookViews createBookViews(int bookId, int viewsCount) {
        BookViews bookViews = new BookViews();
        bookViews.setBookId(bookId);
        bookViews.setViewsCount(viewsCount);
        return bookViews;
    }
}
