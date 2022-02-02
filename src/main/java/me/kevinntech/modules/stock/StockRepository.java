package me.kevinntech.modules.stock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Transactional(readOnly = true)
public interface StockRepository extends JpaRepository<Stock, Long> {

    Stock findByProductId(Long productId);

    List<Stock> findByProductIdIn(Collection<Long> productIds);

    boolean existsStockByProductId(Long productId);
}
