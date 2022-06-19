package JpaShop_.JpaShop_.domain.item;


import JpaShop_.JpaShop_.domain.Category;
import JpaShop_.JpaShop_.exception.NotEnoughStockException;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //부모클래스에서 Inheritance 전략 작성해줘야 함.
@DiscriminatorColumn(name = "dtype") //상속하는 부모 클래스에서 작성.
public abstract class Item {
    @Id @GeneratedValue
    @Column(name ="item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<Category>();

    //==비즈니스 로직==//
    public void addStock(int quantity){
        this.stockQuantity+=quantity;
    }

    public void removeStock(int quantity){
        int restStock = this.stockQuantity-quantity;
        if (restStock<0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity=restStock;
    }
    public void updateItem(String name, int count){
        this.name=name;
        this.stockQuantity=count;
    }

}
