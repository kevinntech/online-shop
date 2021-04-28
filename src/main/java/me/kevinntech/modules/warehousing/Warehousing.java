package me.kevinntech.modules.warehousing;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Warehousing {

    @Id @GeneratedValue
    @Column(name = "warehousing_id")
    private Long id;

    @Column(name = "warehousing_price")
    private long price;

    @Column(name = "warehousing_quantity")
    private long quantity;

}
