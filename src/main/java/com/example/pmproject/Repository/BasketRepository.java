package com.example.pmproject.Repository;

import com.example.pmproject.Entity.Basket;
import com.example.pmproject.Entity.Member;
import com.example.pmproject.Entity.Product;
import org.springframework.stereotype.Repository;

@Repository

public interface BasketRepository {
    static void save(Basket basket) {
    }
    boolean existsByUserAndProduct(Member user, Product product);
}
