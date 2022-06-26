package JpaShop_.JpaShop_.api;

import JpaShop_.JpaShop_.domain.Address;
import JpaShop_.JpaShop_.domain.Order;
import JpaShop_.JpaShop_.domain.OrderSearch;
import JpaShop_.JpaShop_.domain.OrderStatus;
import JpaShop_.JpaShop_.repository.OrderRepository;
import JpaShop_.JpaShop_.repository.order.simplequery.OrderSimpleQueryDto;
import JpaShop_.JpaShop_.repository.order.simplequery.OrderSimpleQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    /*
    * v1 : 엔티티 직접 노출.
    * - Hibernate5모듈 등록 => lazy=null 처리
    * - 양방향 관계 무한 루프 => @JsonIgnore -> entity에 프론트 종속적이게 된다.
     */
    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1(){
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        //return all ; -> 여기서 바로 엔티티를 반환하면 무한루프 오류가 발생한다. (양방향 매핑)
        for (Order order : all){
            order.getMember().getName(); // LAZY 강제 초기화
            order.getDelivery().getAddress(); //LAZY 강제 초기화
        }
        return all;
    } //이거 entity에 @JsonIgnore 안해줘서 무한루프 오류발생.

    /*
    * v2 : 엔티티를 조회해서 dto로 변환
    * N+1문제가 발생함.
     */
    @GetMapping("api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2(){
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(toList());
        return result;
    }

    /*
     * v3 : Fetch 조인 이용함.
     */
    @GetMapping("api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3(){
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(toList());
        return result;
    }

    /*
     * v4 : jpa에서 dto로 필요한 것만 조회한다.
     */
    @GetMapping("api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4(){
        List<OrderSimpleQueryDto> orders = orderSimpleQueryRepository.findOrderDtos();
        return orders;
    }





    @Data
    static class SimpleOrderDto{
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order){
            orderId = order.getId();
            name=order.getMember().getName(); //lazy초기화
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress(); //lazy초기화.
        }

    }
}
