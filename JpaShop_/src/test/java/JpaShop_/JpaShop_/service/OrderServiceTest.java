package JpaShop_.JpaShop_.service;

import JpaShop_.JpaShop_.domain.Address;
import JpaShop_.JpaShop_.domain.Member;
import JpaShop_.JpaShop_.domain.Order;
import JpaShop_.JpaShop_.domain.OrderStatus;
import JpaShop_.JpaShop_.domain.item.Book;
import JpaShop_.JpaShop_.domain.item.Item;
import JpaShop_.JpaShop_.exception.NotEnoughStockException;
import JpaShop_.JpaShop_.repository.MemberRepository;
import JpaShop_.JpaShop_.repository.OrderRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderService orderService;

    @Test
    public void 상품주문() throws Exception{
        //given
        Member member = createMember();
        Item item = createBook("시골 jpa",10000,10);
        int orderCount=2;
        //when
        Long orderId = orderService.order(member.getId(), item.getId(),orderCount);
        //then
        Order order=orderRepository.findOne(orderId);

        assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, order.getStatus());
        assertEquals("주문한 상품 종류 수가 정확해야 한다.",1,order.getOrderItems().size());
        assertEquals("주문 가격은 주문 수량* 주문 가격이다.",orderCount*10000, order.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야 한다.",8,item.getStockQuantity());

    }
    @Test
    public void 주문취소() throws Exception{
        //given
        Member member = createMember();
        Item item = createBook("시골 jpa",10000,10);
        int orderCount=2;
        Long orderId = orderService.order(member.getId(), item.getId(),orderCount);
        //when
        orderService.cancelOrder(orderId);
        //then
        Order order = orderRepository.findOne(orderId);

        assertEquals("주문 상태가 cancel인지",OrderStatus.CANCEL,order.getStatus());
        assertEquals("item의 개수가 정상적으로 복구 되었는지",10,item.getStockQuantity());
    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception{
        //given
        Member member = createMember();
        Item item = createBook("시골 jpa",10000,10);
        int orderCount =11;
        //when
        orderService.order(member.getId(),item.getId(),orderCount);
        //then
        fail("재고 수량 부족 예외가 터져야 합니다.");
    }


    //회원 생성
    private Member createMember(){
        Member member= new Member();
        member.setName("회원 1");
        member.setAddress(new Address("서울","강가","123-123"));
        em.persist(member);

        return member;
    }
    //책(아이템) 생성
    private Book createBook(String name, int price, int stockQuantity){
        Book book=new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }




}
