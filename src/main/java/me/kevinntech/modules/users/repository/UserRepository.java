package me.kevinntech.modules.users.repository;

import me.kevinntech.modules.users.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

// 데이터를 변경하는 것이 없으므로 @Transactional(readOnly = true)를 지정하여 성능 최적화를 한다.
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    User findByEmail(String email);

    User findByNickname(String nickname);
}
