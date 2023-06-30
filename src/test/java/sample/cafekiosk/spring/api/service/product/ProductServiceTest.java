package sample.cafekiosk.spring.api.service.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import sample.cafekiosk.spring.api.service.product.dto.response.ProductResponse;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static sample.cafekiosk.spring.domain.product.ProductType.HANDMADE;

@Transactional
@ActiveProfiles("test")
@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("신규 상품을 등록한다. 상품번호는 가장 최근 상품의 상품번호에서 1이 증가한 값이다.")
    @Test
    void createProduct() {
        // given
        Product product = createProduct("001", "아메리카노", 4000, HANDMADE, SELLING);
        productRepository.save(product);

        ProductCreateRequest request = ProductCreateRequest.builder()
                .name("카푸치노")
                .price(5000)
                .type(HANDMADE)
                .sellingStatus(SELLING)
                .build();

        // when
        ProductResponse productResponse = productService.createProduct(request.toServiceRequest());

        // then
        assertThat(productResponse)
                .extracting("productNumber", "name", "price", "type", "sellingStatus")
                .contains("002", "카푸치노", 5000, HANDMADE, SELLING);

        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(2)
                .extracting("productNumber", "name", "price", "type", "sellingStatus")
                .containsExactlyInAnyOrder(
                        tuple("001", "아메리카노", 4000, HANDMADE, SELLING),
                        tuple("002", "카푸치노", 5000, HANDMADE, SELLING)
                );
    }

    @DisplayName("상품이 존재하지 않는 경우 신규 상품을 등록하면 상품번호는 001이다.")
    @Test
    void createProductWhenProductsIsEmpty() {
        // given
        ProductCreateRequest request = ProductCreateRequest.builder()
                .name("카푸치노")
                .price(5000)
                .type(HANDMADE)
                .sellingStatus(SELLING)
                .build();

        // when
        ProductResponse productResponse = productService.createProduct(request.toServiceRequest());

        // then
        assertThat(productResponse)
                .extracting("productNumber", "name", "price", "type", "sellingStatus")
                .contains("001", "카푸치노", 5000, HANDMADE, SELLING);

        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(1)
                .extracting("productNumber", "name", "price", "type", "sellingStatus")
                .contains(
                        tuple("001", "카푸치노", 5000, HANDMADE, SELLING)
                );
    }

    private Product createProduct(String productNumber, String name, int price, ProductType type, ProductSellingStatus sellingStatus) {
        return Product.builder()
                .productNumber(productNumber)
                .name(name)
                .price(price)
                .type(type)
                .sellingStatus(sellingStatus)
                .build();
    }
}