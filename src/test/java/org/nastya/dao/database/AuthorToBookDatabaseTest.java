package org.nastya.dao.database;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.nastya.dao.AuthorToBookDao;
import org.nastya.entity.AuthorToBook;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AuthorToBookDatabaseTest {
    private AuthorToBookDao authorToBookDao;

    @BeforeEach
    void setUp(){
        authorToBookDao = new AuthorToBookDatabaseDao();
        insertAuthorToBookDatabase(1, 2);
        insertAuthorToBookDatabase(2, 3);
        insertAuthorToBookDatabase(3, 5);
        insertAuthorToBookDatabase(4, 2);
        insertAuthorToBookDatabase(5, 4);
        insertAuthorToBookDatabase(6, 4);
        insertAuthorToBookDatabase(7, 8);
        insertAuthorToBookDatabase(8, 3);
        insertAuthorToBookDatabase(9, 9);
    }

    @AfterEach
    void tearDown() {
        authorToBookDao.deleteAll();
    }

    @Test
    void findByAuthorId() {
        List<AuthorToBook> authorsToBooks = authorToBookDao.findByAuthorId(4);
        assertEquals(4, authorsToBooks.size());
        for (AuthorToBook authorToBook : authorsToBooks) {
            assertEquals(4, authorToBook.getAuthorId());
        }
    }

    @Test
    void findByBookId_thatDoesNotExist() {
        List<AuthorToBook> authorToBook = authorToBookDao.findByBookId(15);
        assertEquals(15, authorToBook.size());
    }

    @Test
    void findByAuthorId_found2AuthorId() {
        insertAuthorToBookDatabase(7, 6);
        List<AuthorToBook> authorToBook = authorToBookDao.findByAuthorId(7);
        assertEquals(2, authorToBook.size());
        for (AuthorToBook authorId : authorToBook) {
            assertEquals(7, authorId.getAuthorId());
        }
    }

    @Test
    void deleteByBookId() {
        int idToDelete = 1;
        authorToBookDao.deleteByBookId(idToDelete);
        assertNull(authorToBookDao.findByBookId(idToDelete), "Book Id should be deleted");
    }



    private void insertAuthorToBookDatabase(int authorId, int bookId) {
        AuthorToBook authorToBook = createAuthorToBook(authorId, bookId);
        authorToBookDao.insert(authorToBook);
    }

    private AuthorToBook createAuthorToBook(int authorId, int bookId) {
        AuthorToBook authorToBook = new AuthorToBook();
        authorToBook.setAuthorId(authorId);
        authorToBook.setBookId(bookId);
        return authorToBook;
    }
}
