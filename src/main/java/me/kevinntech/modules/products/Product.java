package me.kevinntech.modules.products;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Product {

    @Id @GeneratedValue
    @Column(name = "product_id")
    private Long id;

    @Column(name = "product_code", unique = true)
    private String code;

    @Column(name = "product_name")
    private String name;

    private String brand;

    @Column(name = "product_price")
    private long price;

    private String description;

    @Lob @Basic(fetch = FetchType.EAGER)
    private String productImage;

}
