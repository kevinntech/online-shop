package me.kevinntech.modules.stock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface StockRepository extends JpaRepository<Stock, Long> {

    Stock findByProductId(Long productId);

    boolean existsStockByProductId(Long productId);
}
