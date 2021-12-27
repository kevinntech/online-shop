package me.kevinntech.modules.orders.repository;

import me.kevinntech.modules.orders.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Transactional(readOnly = true)
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select distinct o from Order o " +
            "join fetch o.orderProducts op " +
            "join fetch op.product p " +
            "order by o.id")
    List<Order> findAllOrderById();

}


