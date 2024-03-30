package org.nastya.service;

import org.nastya.dto.BookFormDTO;
import org.nastya.dto.BookListItemDTO;
import org.nastya.entity.Book;

import java.util.List;

public interface BookMapper {
    BookListItemDTO mapToBookListItemDTO(Book book);

    List<BookListItemDTO> mapToBookListItemDTO(List<Book> books);

    BookFormDTO mapToBookFormDTO(Book book);

    Book mapToBook(BookFormDTO BookFormDTO);
}
