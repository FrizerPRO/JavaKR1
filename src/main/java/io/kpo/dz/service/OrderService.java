package io.kpo.dz.service;

import io.kpo.dz.domain.Cart;
import io.kpo.dz.domain.Order;
import io.kpo.dz.model.OrderDTO;
import io.kpo.dz.repos.BookRepository;
import io.kpo.dz.repos.CartRepository;
import io.kpo.dz.repos.OrderRepository;
import io.kpo.dz.util.NotFoundException;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;


    public OrderService(final OrderRepository orderRepository,
                        final CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
    }

    public List<OrderDTO> findAll() {
        final List<Order> orders = orderRepository.findAll(Sort.by("id"));
        return orders.stream()
                .map(order -> mapToDTO(order, new OrderDTO()))
                .toList();
    }

    public OrderDTO get(final Long id) {
        return orderRepository.findById(id)
                .map(order -> mapToDTO(order, new OrderDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final OrderDTO orderDTO) {
        final Order order = new Order();
        mapToEntity(orderDTO, order);
        return orderRepository.save(order).getId();
    }

    public void update(final Long id, final OrderDTO orderDTO) {
        final Order order = orderRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(orderDTO, order);
        orderRepository.save(order);
    }

    public void delete(final Long id) {
        orderRepository.deleteById(id);
    }

    private OrderDTO mapToDTO(final Order order, final OrderDTO orderDTO) {
        orderDTO.setId(order.getId());
        orderDTO.setCart(order.getCart() == null ? null : order.getCart().getId());
        return orderDTO;
    }

    private Order mapToEntity(final OrderDTO orderDTO, final Order order) {
        final Cart cart = orderDTO.getCart() == null ? null : cartRepository.findById(orderDTO.getCart())
                .orElseThrow(() -> new NotFoundException("cart not found"));
        order.setCart(cart);
        return order;
    }

    public boolean cartExists(final Long id) {
        return orderRepository.existsByCartId(id);
    }

}
