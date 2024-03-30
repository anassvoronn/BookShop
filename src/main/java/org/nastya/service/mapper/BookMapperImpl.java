package org.nastya.service.mapper;

import org.nastya.dto.BookFormDTO;
import org.nastya.dto.BookListItemDTO;
import org.nastya.entity.Book;
import org.nastya.service.BookMapper;
import org.nastya.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookMapperImpl implements BookMapper {
    private static final Logger log = LoggerFactory.getLogger(BookService.class);

    @Override
    public BookListItemDTO mapToBookListItemDTO(Book book) {
        log.info("Mapping book '{}' with name '{}' to BookListItemDTO", book.getId(), book.getTitle());
        BookListItemDTO dto = new BookListItemDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setPublishingYear(book.getPublishingYear());
        dto.setGenre(book.getGenre());
        return dto;
    }

    @Override
    public List<BookListItemDTO> mapToBookListItemDTO(List<Book> books) {
        log.info("Mapping '{}' books to BookListItemDTO", books.size());
        List<BookListItemDTO> dtos = new ArrayList<>();
        for (Book book : books) {
            BookListItemDTO dto = mapToBookListItemDTO(book);
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public BookFormDTO mapToBookFormDTO(Book book) {
        log.info("Mapping author '{}' with name '{}' to AuthorFormDTO", book.getId(), book.getTitle());
        BookFormDTO dto = new BookFormDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setPublishingYear(book.getPublishingYear());
        dto.setGenre(book.getGenre());
        return dto;
    }

    @Override
    public Book mapToBook(BookFormDTO BookFormDTO) {
        Book bookEntity = new Book();
        bookEntity.setId(BookFormDTO.getId());
        bookEntity.setTitle(BookFormDTO.getTitle());
        bookEntity.setPublishingYear(BookFormDTO.getPublishingYear());
        bookEntity.setGenre(BookFormDTO.getGenre());
        return bookEntity;
    }
}
