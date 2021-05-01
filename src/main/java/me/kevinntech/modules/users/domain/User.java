package me.kevinntech.modules.users.domain;

import lombok.*;
import me.kevinntech.modules.orders.Order;
import me.kevinntech.modules.products.Product;
import me.kevinntech.modules.stock.Stock;
import me.kevinntech.modules.warehousing.dto.WarehousingSaveRequestDto;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String nickname;

    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();

    private String password;

    private LocalDateTime joinedAt;

    private String bio;

    @Lob @Basic(fetch = FetchType.EAGER)
    private String profileImage;

    @Builder
    private User(String email, String nickname, String password){
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public void completeSignUp() {
        this.joinedAt = LocalDateTime.now();
    }

}