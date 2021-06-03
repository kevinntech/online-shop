package me.kevinntech.modules.warehousing;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface WarehousingRepository extends JpaRepository<Warehousing, Long> {
}
