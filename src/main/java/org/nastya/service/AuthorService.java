package org.nastya.service;

import org.nastya.dao.AuthorDao;
import org.nastya.dao.AuthorToBookDao;
import org.nastya.dao.BookDao;
import org.nastya.dto.AuthorFormDTO;
import org.nastya.dto.AuthorListItemDTO;
import org.nastya.dto.BookListItemDTO;
import org.nastya.entity.Author;
import org.nastya.entity.AuthorToBook;
import org.nastya.entity.Book;
import org.nastya.service.UserClient.UserContext;
import org.nastya.service.UserClient.UserClient;
import org.nastya.service.exception.AuthorNotFoundException;
import org.nastya.service.exception.UserClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorService {
    private static final Logger log = LoggerFactory.getLogger(AuthorService.class);
    private final AuthorDao authorDao;
    private final BookDao bookDao;
    private final AuthorToBookDao authorToBookDao;
    private final BookMapper bookMapper;
    private final AuthorMapper authorMapper;
    private final UserClient userClient;
    private final UserContext userContext;

    public AuthorService(final AuthorDao authorDao,
                         final AuthorToBookDao authorToBookDao,
                         final AuthorMapper authorMapper,
                         final BookDao bookDao,
                         final BookMapper bookMapper,
                         final UserClient userClient,
                         final UserContext userContext) {
        this.authorDao = authorDao;
        this.authorToBookDao = authorToBookDao;
        this.authorMapper = authorMapper;
        this.bookDao = bookDao;
        this.bookMapper = bookMapper;
        this.userClient = userClient;
        this.userContext = userContext;
    }

    public List<AuthorListItemDTO> findAll() {
        List<Author> authors = authorDao.findAll();
        log.info("Found '{}' authors", authors.size());
        List<AuthorListItemDTO> dtos = authorMapper.mapToAuthorListItemDTO(authors);
        for (AuthorListItemDTO authorDto : dtos) {
            LocalDate birthDate = authorDto.getBirthDate();
            LocalDate deathDate = authorDto.getDeathDate();
            int ages = calculateAge(birthDate, deathDate);
            authorDto.setAge(ages);
            log.info("Calculated age '{}' for author '{}' with id '{}'",
                    authorDto.getAge(), authorDto.getName(), authorDto.getId());
        }
        return dtos;
    }

    private int calculateAge(LocalDate dateOfBirth, LocalDate deathDate) {
        if (dateOfBirth == null) {
            log.info("Date of birth was not provided. Returning 0");
            return 0;
        }
        if (deathDate == null) {
            LocalDate now = LocalDate.now();
            log.info("Date of death was not provided. Using current date '{}'", now);
            return Period.between(dateOfBirth, now).getYears();
        }
        return Period.between(dateOfBirth, deathDate).getYears();
    }

    public AuthorFormDTO findById(int id) throws AuthorNotFoundException {
        Author author = authorDao.findById(id);
        if (author == null) {
            log.info("Author with id '{}' was not found", id);
            throw new AuthorNotFoundException("There is no author with this id " + id);
        }
        log.info("Found author '{}' with id '{}'", author.getName(), author.getId());
        AuthorFormDTO dto = authorMapper.mapToAuthorFormDTO(author);
        LocalDate birthDate = dto.getBirthDate();
        LocalDate deathDate = dto.getDeathDate();
        int ages = calculateAge(birthDate, deathDate);
        dto.setAge(ages);
        List<AuthorToBook> authorToBooks = authorToBookDao.findByAuthorId(id);
        List<BookListItemDTO> bookListDTO = new ArrayList<>();
        for (AuthorToBook authorToBook : authorToBooks) {
            Book book = bookDao.findById(authorToBook.getBookId());
            if (book != null) {
                BookListItemDTO bookListItemDTO = bookMapper.mapToBookListItemDTO(book);
                bookListDTO.add(bookListItemDTO);
            }
        }
        dto.setBooks(bookListDTO);
        log.info("Calculated age '{}' for author '{}' with id '{}'",
                dto.getAge(), dto.getName(), dto.getId());
        return dto;
    }

    public void deleteAuthor(int authorId) throws AuthorNotFoundException {
        String userSession = userContext.getCurrentUserSession();
        if (!userClient.isUserAuthorized(userSession)) {
            throw new UserClientException("User is not authorized to perform this action");
        }
        Author author = authorDao.findById(authorId);
        if (author == null) {
            log.info("Author with id '{}' was not found", authorId);
            throw new AuthorNotFoundException("There is no author with this id " + authorId);
        }
        authorDao.deleteById(authorId);
        log.info("Deleted author '{}' with id '{}'", author.getName(), author.getId());
    }

    public void updateAuthor(AuthorFormDTO authorFormDTO) throws AuthorNotFoundException {
        String userSession = userContext.getCurrentUserSession();
        if (!userClient.isUserAuthorized(userSession)) {
            throw new UserClientException("User is not authorized to perform this action");
        }
        Author existingAuthor = authorDao.findById(authorFormDTO.getId());
        if (existingAuthor == null) {
            log.info("Author with id '{}' was not found for update", authorFormDTO.getId());
            throw new AuthorNotFoundException("There is no author with this id " + authorFormDTO.getId());
        }
        Author updatedAuthor = authorMapper.mapToAuthor(authorFormDTO);
        authorDao.save(updatedAuthor);
        log.info("Updated author '{}' with id '{}'", updatedAuthor.getName(), updatedAuthor.getId());
    }

    public void addAuthor(AuthorFormDTO authorFormDTO) {
        String userSession = userContext.getCurrentUserSession();
        if (!userClient.isUserAuthorized(userSession)) {
            throw new UserClientException("User is not authorized to perform this action");
        }
        Author author = authorMapper.mapToAuthor(authorFormDTO);
        authorDao.insert(author);
        log.info("Added author '{}' with id '{}'", author.getName(), author.getId());
    }
}
