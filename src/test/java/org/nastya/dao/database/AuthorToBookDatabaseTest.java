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
        assertEquals(1, authorsToBooks.size());
        for (AuthorToBook authorToBook : authorsToBooks) {
            assertEquals(4, authorToBook.getAuthorId());
        }
    }

    @Test
    void findByBookId() {
        List<AuthorToBook> authorsToBooks = authorToBookDao.findByBookId(5);
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
    void findByAuthorId_found2AuthorId() {
        insertAuthorToBookDatabase(7, 6);
        List<AuthorToBook> authorToBook = authorToBookDao.findByAuthorId(7);
        assertEquals(2, authorToBook.size());
        for (AuthorToBook authorId : authorToBook) {
            assertEquals(7, authorId.getAuthorId());
        }
    }

    @Test
    void findByAuthorId_found3BookId() {
        insertAuthorToBookDatabase(7, 4);
        List<AuthorToBook> authorToBook = authorToBookDao.findByBookId(4);
        assertEquals(3, authorToBook.size());
        for (AuthorToBook authorId : authorToBook) {
            assertEquals(4, authorId.getBookId());
        }
    }

    @Test
    void deleteByBookId() {
        int idToDelete = 1;
        List<AuthorToBook> result = authorToBookDao.findByBookId(idToDelete);
        assertTrue(result.isEmpty());
    }

    @Test
    void testSave() {
        AuthorToBook authorToBook = new AuthorToBook();
        authorToBook.setAuthorId(5);
        authorToBook.setBookId(6);
        authorToBookDao.save(authorToBook);
        List<AuthorToBook> updatedAuthorToBooks = authorToBookDao.findByAuthorId(5);
        assertEquals(9, updatedAuthorToBooks.size());
        assertEquals(5, updatedAuthorToBooks.get(0).getAuthorId());
        assertEquals(6, updatedAuthorToBooks.get(0).getBookId());
    }

    @Test
    void testInsert() {
        AuthorToBook authorToBook = new AuthorToBook();
        authorToBook.setAuthorId(1);
        authorToBook.setBookId(2);
        authorToBookDao.insert(authorToBook);
        assertEquals(1, authorToBook.getAuthorId());
        assertEquals(2, authorToBook.getBookId());
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
