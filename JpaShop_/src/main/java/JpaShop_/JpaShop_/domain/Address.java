package JpaShop_.JpaShop_.domain;
import lombok.Getter;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

@Embeddable
//이 내장타입을 사용하는 대상은 그 속성위에 @Embedded 어노테이션붙여줘야 함.
@Getter
public class Address {
    private String city;
    private String street;
    private String zipcode;

    protected Address(){

    }
    public Address(String city,String street, String zipcode){
        this.city=city;
        this.street=street;
        this.zipcode=zipcode;
    }
}
