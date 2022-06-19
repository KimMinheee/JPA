package JpaShop_.JpaShop_.service;

import JpaShop_.JpaShop_.domain.item.Item;
import JpaShop_.JpaShop_.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }
    public Item findItem(Long id){
        return itemRepository.findOne(id);
    }

    /* 영속성 컨텍스트가 자동으로 update해줌)*/
    @Transactional
    public void updateItem(Long id, String name, int price){
        Item item = itemRepository.findOne(id);
        item.setName(name);
        item.setPrice(price);
        item.updateItem(name,price); //setter남발 줄이기 위해.
    }


}
