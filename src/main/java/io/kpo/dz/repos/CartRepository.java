package io.kpo.dz.repos;

import io.kpo.dz.domain.Book;
import io.kpo.dz.domain.Cart;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findAllByBooks(Book book);

}
