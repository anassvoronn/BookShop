package org.nastya;

import org.nastya.dao.AuthorDao;
import org.nastya.dao.database.AuthorDatabaseDao;

public class JdbcTest {

    public static void main(String[] args) {
        AuthorDao authorDao = new AuthorDatabaseDao();
        System.out.println(authorDao.findAll());
    }

}