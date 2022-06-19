package JpaShop_.JpaShop_.controller;

import JpaShop_.JpaShop_.domain.Member;
import JpaShop_.JpaShop_.domain.Order;
import JpaShop_.JpaShop_.domain.OrderSearch;
import JpaShop_.JpaShop_.domain.item.Item;
import JpaShop_.JpaShop_.service.ItemService;
import JpaShop_.JpaShop_.service.MemberService;
import JpaShop_.JpaShop_.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class OrderController {
    private final MemberService memberService;
    private final ItemService itemService;
    private final OrderService orderService;

    @GetMapping(value ="/order")
    public String createForm(Model model){
        List<Member> members= memberService.findMembers();
        List<Item> items=itemService.findItems();


        model.addAttribute("members",members);
        model.addAttribute("items",items);
        return "order/orderForm";
    }

    @PostMapping(value="/order")
    public String order(@RequestParam("memberId") Long memberId, @RequestParam("itemId") Long itemId, @RequestParam("count") int count){
        orderService.order(memberId,itemId, count);

        return "redirect:/orders";
    }

    @GetMapping(value = "/orders")
    public String orderList(@ModelAttribute("orderSearch")OrderSearch orderSearch, Model model){
        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders",orders);

        return "order/orderList";
    }

    @PostMapping(value="/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId){
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }
}
