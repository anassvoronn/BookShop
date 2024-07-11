package org.nastya.dao.database;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.nastya.dao.AuthorToBookDao;
import org.nastya.entity.AuthorToBook;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.nastya.utils.ObjectCreator.createAuthorToBook;

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
        List<AuthorToBook> connections = authorToBookDao.findByAuthorId(currentAuthorId);
        assertEquals(1, connections.size());
        for (AuthorToBook connection : connections) {
            assertEquals(currentAuthorId, connection.getAuthorId());
        }
    }

    @Test
    void findByBookId() {
        int currentBookId = 5;
        List<AuthorToBook> connections = authorToBookDao.findByBookId(currentBookId);
        assertEquals(1, connections.size());
        for (AuthorToBook connection : connections) {
            assertEquals(3, connection.getAuthorId());
        }
    }

    @Test
    void findByBookId_thatDoesNotExist() {
        List<AuthorToBook> connections = authorToBookDao.findByBookId(15);
        assertEquals(0, connections.size());
    }

    @Test
    void findByAuthorId_thatDoesNotExist() {
        List<AuthorToBook> connections = authorToBookDao.findByAuthorId(10);
        assertEquals(0, connections.size());
    }

    @Test
    void findByAuthorId_found2AuthorId() {
        int currentAuthorId = 7;
        insertAuthorToBookDatabase(currentAuthorId, 6);
        List<AuthorToBook> connections = authorToBookDao.findByAuthorId(currentAuthorId);
        assertEquals(2, connections.size());
        for (AuthorToBook connection : connections) {
            assertEquals(currentAuthorId, connection.getAuthorId());
        }
    }

    @Test
    void findByBookId_found3BookId() {
        int currentBookId = 4;
        insertAuthorToBookDatabase(7, currentBookId);
        List<AuthorToBook> connections = authorToBookDao.findByBookId(currentBookId);
        assertEquals(3, connections.size());
        for (AuthorToBook connection : connections) {
            assertEquals(currentBookId, connection.getBookId());
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
        List<AuthorToBook> beforeInsertingByAuthorId = authorToBookDao.findByAuthorId(authorId);
        List<AuthorToBook> beforeInsertingByBookId = authorToBookDao.findByBookId(bookId);
        authorToBookDao.insert(authorToBook);
        List<AuthorToBook> afterInsertingByAuthorId = authorToBookDao.findByAuthorId(authorId);
        List<AuthorToBook> afterInsertingByBookId = authorToBookDao.findByBookId(bookId);
        assertEquals(beforeInsertingByAuthorId.size() + 1, afterInsertingByAuthorId.size());
        assertEquals(beforeInsertingByBookId.size() + 1, afterInsertingByBookId.size());
    }

    @Test
    public void deleteAllByBookId() {
        List<AuthorToBook> connections = authorToBookDao.findByBookId(3);
        assertEquals(2, connections.size());
        authorToBookDao.deleteAll();
        connections = authorToBookDao.findByBookId(3);
        assertEquals(0, connections.size());
    }

    @Test
    public void deleteAllByAuthorId() {
        int currentAuthorId = 6;
        insertAuthorToBookDatabase(currentAuthorId, 1);
        insertAuthorToBookDatabase(currentAuthorId, 5);
        insertAuthorToBookDatabase(currentAuthorId, 3);
        List<AuthorToBook> connections = authorToBookDao.findByAuthorId(6);
        assertEquals(4, connections.size());
        authorToBookDao.deleteAll();
        connections = authorToBookDao.findByAuthorId(6);
        assertEquals(0, connections.size());
    }

    @Test
    public void findByBookId_nothingFound() {
        List<AuthorToBook> connections = authorToBookDao.findByBookId(12);
        assertEquals(0, connections.size());
    }

    private void insertAuthorToBookDatabase(int authorId, int bookId) {
        AuthorToBook authorToBook = createAuthorToBook(authorId, bookId);
        authorToBookDao.insert(authorToBook);
    }
}
