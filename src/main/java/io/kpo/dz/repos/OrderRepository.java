package io.kpo.dz.repos;

import io.kpo.dz.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Long> {

    boolean existsByCartId(Long id);

}
