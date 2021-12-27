package me.kevinntech.modules.main;

import me.kevinntech.modules.products.Product;
import me.kevinntech.modules.products.ProductRepository;
import me.kevinntech.modules.users.WithUser;
import me.kevinntech.modules.users.dto.UserSaveRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class BaseEntityTest {

    @Autowired ProductRepository productRepository;

    // 모든 테스트를 실행 할 때 마다 UserSaveRequestDto를 만든다.
    @BeforeEach
    void beforeEach(){
        Product product = Product.builder()
                .code("NO-1")
                .name("운동화")
                .brand("브랜드")
                .price(10000)
                .description("운동화 입니다.")
                .build();

        productRepository.save(product);
    }

    // 이메일이 중복되어 생성되지 않도록 삭제를 한다.
    @AfterEach
    void afterEach(){
        productRepository.deleteAll();
    }

    @Test
    @WithUser("kevin") // 웹 페이지에서 로그인을 한 것과 동일한 기능을 함
    @DisplayName("Auditing 테스트")
    void testAuditing() {
        Product findProduct = productRepository.findOneByCode("NO-1");

        assertThat(findProduct.getCode()).isEqualTo("NO-1");
        assertThat(findProduct.getName()).isEqualTo("운동화");
        assertThat(findProduct.getBrand()).isEqualTo("브랜드");
        assertThat(findProduct.getPrice()).isEqualTo(10000);
        assertThat(findProduct.getDescription()).isEqualTo("운동화 입니다.");
        assertThat(findProduct.getCreatedAt()).isNotNull();
        assertThat(findProduct.getCreatedBy()).isEqualTo("kevin");
        assertThat(findProduct.getModifiedAt()).isNotNull();
        assertThat(findProduct.getModifiedBy()).isEqualTo("kevin");
    }

}


