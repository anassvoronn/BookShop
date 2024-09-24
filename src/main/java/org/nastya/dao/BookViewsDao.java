package org.nastya.dao;

import org.nastya.entity.BookViews;

public interface BookViewsDao {

    void incrementViewsCountByBookId(int bookId);

    int getViewsCountByBookId(int bookId);

    void insert(BookViews bookViews);

    void deleteAll();
}