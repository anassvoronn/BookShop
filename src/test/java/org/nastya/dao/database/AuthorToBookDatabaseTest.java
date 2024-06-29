package org.nastya.dao.database;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.nastya.dao.AuthorToBookDao;
import org.nastya.entity.AuthorToBook;

public class AuthorToBookDatabaseTest {
    private AuthorToBookDao authorToBookDao;

    @BeforeEach
    void setUp(){
        authorToBookDao = new AuthorToBookDatabaseDao();
        insertAuthorToBookDatabase(12, 2);
        insertAuthorToBookDatabase(23, 3);
        insertAuthorToBookDatabase(18, 5);
        insertAuthorToBookDatabase(45, 2);
        insertAuthorToBookDatabase(16, 4);
        insertAuthorToBookDatabase(12, 4);
        insertAuthorToBookDatabase(67, 8);
        insertAuthorToBookDatabase(39, 3);
        insertAuthorToBookDatabase(67, 9);
    }

    @AfterEach
    void tearDown() {
        authorToBookDao.deleteAll();
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
