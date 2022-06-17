package JpaShop_.JpaShop_.service;

import JpaShop_.JpaShop_.domain.*;
import JpaShop_.JpaShop_.domain.item.Item;
import JpaShop_.JpaShop_.repository.ItemRepository;
import JpaShop_.JpaShop_.repository.MemberRepository;
import JpaShop_.JpaShop_.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;

    /* 주문 생성 */
    @Transactional
    public Long order(Long memberId, Long itemId, int count){

        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송 정보 생성
        Delivery delivery=new Delivery();
        delivery.setDeliveryStatus(DeliveryStatus.READY);
        delivery.setAddress(member.getAddress()); //member에 저장되어 있는 address를 가져온다고 가정.(다른 배송주소 없이)

        //주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item,item.getPrice(),count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        orderRepository.save(order);
        return order.getId();
    }

    /* 주문 취소 */
    @Transactional
    public void cancelOrder(Long orderId){
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }

    /* 주문 검색 */

}
