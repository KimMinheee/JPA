package JpaShop_.JpaShop_.domain;


import JpaShop_.JpaShop_.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name ="order_item")
public class OrderItem {
    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id")
    private Order order;//주문

    private int orderPrice; //주문 가격
    private int count; //주문 수량

    //==생성 메소드==//

    //==비즈니스 로직==//

    //==조회 로직==//

    /** 주문 상품 전체 가격 조회 **/


}
