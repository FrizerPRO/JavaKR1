package io.kpo.dz.service;

import io.kpo.dz.domain.Book;
import io.kpo.dz.model.BookDTO;
import io.kpo.dz.model.BookOutDTO;
import io.kpo.dz.repos.BookRepository;
import io.kpo.dz.repos.CartRepository;
import io.kpo.dz.util.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final CartRepository cartRepository;

    public BookService(final BookRepository bookRepository, final CartRepository cartRepository) {
        this.bookRepository = bookRepository;
        this.cartRepository = cartRepository;
    }

    public List<BookOutDTO> findAll() {
        final List<Book> books = bookRepository.findAll(Sort.by("id"));
        return books.stream()
                .map(book -> mapToDTO(book, new BookOutDTO()))
                .toList();
    }

    public BookDTO get(final Long id) {
        return bookRepository.findById(id)
                .map(book -> mapToDTO(book, new BookDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final BookDTO bookDTO) {
        final Book book = new Book();
        mapToEntity(bookDTO, book);
        return bookRepository.save(book).getId();
    }

    public void update(final Long id, final BookDTO bookDTO) {
        final Book book = bookRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(bookDTO, book);
        bookRepository.save(book);
    }

    public void delete(final Long id) {
        final Book book = bookRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        cartRepository.findAllByBooks(book)
                .forEach(cart -> cart.getBooks().remove(book));
        bookRepository.delete(book);
    }

    private BookDTO mapToDTO(final Book book, final BookDTO bookDTO) {
        bookDTO.setId(book.getId());
        bookDTO.setName(book.getName());
        bookDTO.setAuthorName(book.getAuthorName());
        bookDTO.setGenre(book.getGenre());
        bookDTO.setPrice(book.getPrice());
        bookDTO.setDescription(book.getDescription());
        bookDTO.setAmount(book.getAmount());
        return bookDTO;
    }
    private BookOutDTO mapToDTO(final Book book, final BookOutDTO bookDTO) {
        bookDTO.setName(book.getName());
        bookDTO.setAuthorName(book.getAuthorName());
        bookDTO.setGenre(book.getGenre());
        bookDTO.setPrice(book.getPrice());
        return bookDTO;
    }

    private Book mapToEntity(final BookDTO bookDTO, final Book book) {
        book.setName(bookDTO.getName());
        book.setAuthorName(bookDTO.getAuthorName());
        book.setGenre(bookDTO.getGenre());
        book.setPrice(bookDTO.getPrice());
        book.setDescription(bookDTO.getDescription());
        book.setAmount(bookDTO.getAmount());
        return book;
    }

}
