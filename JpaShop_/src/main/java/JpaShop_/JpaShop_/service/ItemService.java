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

}
