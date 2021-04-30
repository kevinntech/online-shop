package me.kevinntech.modules.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByCode(String code);

    @Query("select p from Product p order by p.id")
    List<Product> findAllOrderById();

    Product findOneById(Long id);

    Product findOneByCode(String code);

    @Query("select p from Stock s join s.product p where s.quantity > 0 order by p.id")
    List<Product> findStockOrderById();
}
