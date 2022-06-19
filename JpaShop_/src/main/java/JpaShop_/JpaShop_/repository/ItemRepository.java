package JpaShop_.JpaShop_.repository;

import JpaShop_.JpaShop_.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager em;

    public void save(Item item){
        if(item.getId()==null){
            //item을 처음 등록할 때(신규)
            em.persist(item);
        }
        else{
            //기존에 등록한 적 있는(id가 있는) item을 등록할 때
           // em.merge(item); //이거 나중에 고칠거임.
        }
    }

    public Item findOne(Long id){
        return em.find(Item.class,id);
    }
    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }
}
