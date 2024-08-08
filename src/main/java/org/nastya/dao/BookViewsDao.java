package org.nastya.dao;

import org.nastya.entity.BookViews;

public interface BookViewsDao {

    void incrementViewCount(BookViews bookViews);
}