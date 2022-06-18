package JpaShop_.JpaShop_.controller;

import JpaShop_.JpaShop_.domain.item.Book;
import JpaShop_.JpaShop_.domain.item.Item;
import JpaShop_.JpaShop_.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    /*상품 등록*/
    @GetMapping(value="/items/new")
    public String createForm(Model model){
        model.addAttribute("form",new BookForm());
        return "items/createItemForm";
    }
    @PostMapping(value="/items/new")
    public String create(BookForm bookForm){
        Book book = new Book();
        book.setName(bookForm.getName());
        book.setStockQuantity(bookForm.getStockQuantity());
        book.setPrice(bookForm.getPrice());
        book.setAuthor(bookForm.getAuthor());
        book.setIsbn(bookForm.getIsbn());

        itemService.saveItem(book);
        return "redirect:/items"; //등록 다하면 목록으로 감.
    }


    /*상품 목록*/
    @GetMapping(value ="/items")
    public String list(Model model){
        List<Item> items=itemService.findItems();
        model.addAttribute("items",items);
        return "items/itemList";
    }


}
