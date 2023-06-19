package io.kpo.dz.service;

import io.kpo.dz.domain.Book;
import io.kpo.dz.domain.Cart;
import io.kpo.dz.model.CartDTO;
import io.kpo.dz.repos.BookRepository;
import io.kpo.dz.repos.CartRepository;
import io.kpo.dz.util.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final BookRepository bookRepository;

    public CartService(final CartRepository cartRepository, final BookRepository bookRepository) {
        this.cartRepository = cartRepository;
        this.bookRepository = bookRepository;
    }

    public List<CartDTO> findAll() {
        final List<Cart> carts = cartRepository.findAll(Sort.by("id"));
        return carts.stream()
                .map(cart -> mapToDTO(cart, new CartDTO()))
                .toList();
    }

    public CartDTO get(final Long id) {
        return cartRepository.findById(id)
                .map(cart -> mapToDTO(cart, new CartDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CartDTO cartDTO) {
        final Cart cart = new Cart();
        mapToEntity(cartDTO, cart);
        return cartRepository.save(cart).getId();
    }

    public void update(final Long id, final CartDTO cartDTO) {
        final Cart cart = cartRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(cartDTO, cart);
        cartRepository.save(cart);
    }

    public void delete(final Long id) {
        cartRepository.deleteById(id);
    }

    private CartDTO mapToDTO(final Cart cart, final CartDTO cartDTO) {
        cartDTO.setId(cart.getId());
        cartDTO.setAmount(cart.getAmount());
        cartDTO.setBooks(cart.getBooks().stream()
                .map(book -> book.getId())
                .toList());
        return cartDTO;
    }

    private Cart mapToEntity(final CartDTO cartDTO, final Cart cart) {
        cart.setAmount(cartDTO.getAmount());
        final List<Book> books = bookRepository.findAllById(
                cartDTO.getBooks() == null ? Collections.emptyList() : cartDTO.getBooks());
        if (books.size() != (cartDTO.getBooks() == null ? 0 : cartDTO.getBooks().size())) {
            throw new NotFoundException("one of books not found");
        }
        cart.setBooks(books.stream().collect(Collectors.toSet()));
        if(cartDTO.getId() != null) {
            var existingCart = cartRepository.findById(cartDTO.getId());
            if(existingCart.isPresent() && existingCart.get().getBooks() != null){
                var booksNewed = cart.getBooks();
                booksNewed.addAll(existingCart.get().getBooks());
                cart.setBooks(booksNewed);
            }
            cart.setId(cartDTO.getId());
        }
        return cart;
    }

}
