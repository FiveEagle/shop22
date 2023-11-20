package com.example.pmproject.Service;

import com.example.pmproject.Entity.Member;
import com.example.pmproject.Entity.Product;
import com.example.pmproject.Entity.Basket;
import com.example.pmproject.Repository.MemberRepository;
import com.example.pmproject.Repository.ProductRepository;
import com.example.pmproject.Repository.BasketRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BasketService {

    private final BasketRepository basketRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    public void addToCart(String email, Long productId) throws NotFoundException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("상품을 찾을 수 없습니다"));

        Member currentUser = memberRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다"));

        // 장바구니에 상품 추가
        Basket basket = new Basket();
        basket.setUser(currentUser);
        basket.setProduct(product);

        // 추가적인 필요한 로직
        checkDuplicateProduct(currentUser, product);

        // 수량, 가격 계산 로직
        calculateQuantityAndPrice(basket, product);

        // 장바구니에 저장
        BasketRepository.save(basket);
        System.out.println(currentUser.getName() + "님의 쇼핑 카트에 " + product.getName() + " 상품이 추가되었습니다.");
    }

    private void checkDuplicateProduct(Member member, Product product) {
        // 중복 상품 체크 로직
        if (basketRepository.existsByUserAndProduct(member, product)) {
            throw new RuntimeException("이미 장바구니에 존재하는 상품입니다.");
        }
    }

    private void calculateQuantityAndPrice(Basket basket, Product product) {
        // 수량 및 가격 계산 로직
        int quantity = 1; // 예시로 수량은 1로 설정
        basket.setQuantity(quantity);
        basket.setPrice(product.getPrice() * quantity);
    }
}
