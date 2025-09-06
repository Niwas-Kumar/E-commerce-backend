package com.example.ecommerce.spec;

import com.example.ecommerce.model.Item;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ItemSpecifications {
    public static Specification<Item> priceGte(BigDecimal min) {
        return (root, query, cb) -> min == null ? null : cb.greaterThanOrEqualTo(root.get("price"), min);
    }
    public static Specification<Item> priceLte(BigDecimal max) {
        return (root, query, cb) -> max == null ? null : cb.lessThanOrEqualTo(root.get("price"), max);
    }
    public static Specification<Item> categoryEq(String category) {
        return (root, query, cb) -> (category == null || category.isBlank()) ? null : cb.equal(root.get("category"), category);
    }
    public static Specification<Item> nameLike(String q) {
        return (root, query, cb) -> (q == null || q.isBlank()) ? null : cb.like(cb.lower(root.get("name")), "%" + q.toLowerCase() + "%");
    }
}
