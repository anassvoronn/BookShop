package org.nastya.dao.database;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.nastya.dao.AuthorToBookDao;
import org.nastya.entity.AuthorToBook;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AuthorToBookDatabaseTest {
    private AuthorToBookDao authorToBookDao;

    @BeforeEach
    void setUp() {
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
        int currentAuthorId = 4;
        List<AuthorToBook> authorsToBooks = authorToBookDao.findByAuthorId(currentAuthorId);
        assertEquals(1, authorsToBooks.size());
        for (AuthorToBook authorToBook : authorsToBooks) {
            assertEquals(currentAuthorId, authorToBook.getAuthorId());
        }
    }

    @Test
    void findByBookId() {
        int currentBookId = 5;
        List<AuthorToBook> authorsToBooks = authorToBookDao.findByBookId(currentBookId);
        assertEquals(1, authorsToBooks.size());
        for (AuthorToBook authorToBook : authorsToBooks) {
            assertEquals(3, authorToBook.getAuthorId());
        }
    }

    @Test
    void findByBookId_thatDoesNotExist() {
        List<AuthorToBook> authorToBook = authorToBookDao.findByBookId(15);
        assertEquals(0, authorToBook.size());
    }

    @Test
    void findByAuthorId_thatDoesNotExist() {
        List<AuthorToBook> authorToBook = authorToBookDao.findByAuthorId(10);
        assertEquals(0, authorToBook.size());
    }

    @Test
    void findByAuthorId_found2AuthorId() {
        int currentAuthorId = 7;
        insertAuthorToBookDatabase(currentAuthorId, 6);
        List<AuthorToBook> authorToBook = authorToBookDao.findByAuthorId(currentAuthorId);
        assertEquals(2, authorToBook.size());
        for (AuthorToBook authorId : authorToBook) {
            assertEquals(currentAuthorId, authorId.getAuthorId());
        }
    }

    @Test
    void findByBookId_found3BookId() {
        int currentBookId = 4;
        insertAuthorToBookDatabase(7, currentBookId);
        List<AuthorToBook> authorToBook = authorToBookDao.findByBookId(currentBookId);
        assertEquals(3, authorToBook.size());
        for (AuthorToBook authorId : authorToBook) {
            assertEquals(currentBookId, authorId.getBookId());
        }
    }

    @Test
    void deleteByBookId() {
        int currentBookId = 4;
        List<AuthorToBook> beforeDeletion = authorToBookDao.findByBookId(currentBookId);
        assertEquals(2, beforeDeletion.size());
        authorToBookDao.deleteByBookId(currentBookId);
        List<AuthorToBook> afterDeletion = authorToBookDao.findByBookId(currentBookId);
        assertTrue(afterDeletion.isEmpty());
    }

    @Test
    void deleteByAuthorId() {
        int currentAuthorId = 2;
        insertAuthorToBookDatabase(currentAuthorId, 4);
        insertAuthorToBookDatabase(currentAuthorId, 7);
        insertAuthorToBookDatabase(currentAuthorId, 9);
        List<AuthorToBook> beforeDeletion = authorToBookDao.findByAuthorId(currentAuthorId);
        assertEquals(4, beforeDeletion.size());
        authorToBookDao.deleteByAuthorId(currentAuthorId);
        List<AuthorToBook> afterDeletion = authorToBookDao.findByAuthorId(currentAuthorId);
        assertTrue(afterDeletion.isEmpty());
    }

    @Test
    void testInsert() {
        AuthorToBook authorToBook = new AuthorToBook();
        int authorId = 1;
        authorToBook.setAuthorId(authorId);
        int bookId = 2;
        authorToBook.setBookId(bookId);
        List<AuthorToBook> beforeByAuthorId = authorToBookDao.findByAuthorId(authorId);
        List<AuthorToBook> beforeByBookId = authorToBookDao.findByBookId(bookId);
        authorToBookDao.insert(authorToBook);
        List<AuthorToBook> afterByAuthorId = authorToBookDao.findByAuthorId(authorId);
        List<AuthorToBook> afterByBookId = authorToBookDao.findByBookId(bookId);
        assertEquals(beforeByAuthorId.size() + 1, afterByAuthorId.size());
        assertEquals(beforeByBookId.size() + 1, afterByBookId.size());
    }

    @Test
    public void deleteAllByBookId() {
        List<AuthorToBook> authorsToBooks = authorToBookDao.findByBookId(3);
        assertEquals(2, authorsToBooks.size());
        authorToBookDao.deleteAll();
        authorsToBooks = authorToBookDao.findByBookId(3);
        assertEquals(0, authorsToBooks.size());
    }

    @Test
    public void deleteAllByAuthorId() {
        int currentAuthorId = 6;
        insertAuthorToBookDatabase(currentAuthorId, 1);
        insertAuthorToBookDatabase(currentAuthorId, 5);
        insertAuthorToBookDatabase(currentAuthorId, 3);
        List<AuthorToBook> authorsToBooks = authorToBookDao.findByAuthorId(6);
        assertEquals(4, authorsToBooks.size());
        authorToBookDao.deleteAll();
        authorsToBooks = authorToBookDao.findByAuthorId(6);
        assertEquals(0, authorsToBooks.size());
    }

    @Test
    public void findByBookId_nothingFound() {
        List<AuthorToBook> authorsToBooks = authorToBookDao.findByBookId(12);
        assertEquals(0, authorsToBooks.size());
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
