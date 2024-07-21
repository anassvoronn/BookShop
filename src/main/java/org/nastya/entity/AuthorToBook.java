package org.nastya.entity;

public class AuthorToBook {

    private int authorId;
    private int bookId;

    @Override
    public String toString() {
        return "AuthorToBook{" +
                "authorId=" + authorId +
                ", bookId=" + bookId +
                '}';
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}
