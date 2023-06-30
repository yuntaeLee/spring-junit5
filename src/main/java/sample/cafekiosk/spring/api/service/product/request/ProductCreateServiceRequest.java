package sample.cafekiosk.spring.api.service.product.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

@Getter
@NoArgsConstructor
public class ProductCreateServiceRequest {

    private String name;
    private int price;
    private ProductType type;
    private ProductSellingStatus sellingStatus;

    @Builder
    public ProductCreateServiceRequest(String name, int price, ProductType type, ProductSellingStatus sellingStatus) {
        this.name = name;
        this.price = price;
        this.type = type;
        this.sellingStatus = sellingStatus;
    }

    public Product toEntity(String nextProductNumber) {
        return Product.builder()
                .productNumber(nextProductNumber)
                .name(name)
                .price(price)
                .type(type)
                .sellingStatus(sellingStatus)
                .build();
    }
}
