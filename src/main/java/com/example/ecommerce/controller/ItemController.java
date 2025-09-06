package com.example.ecommerce.controller;

import com.example.ecommerce.dto.ItemDtos.ItemRequest;
import com.example.ecommerce.model.Item;
import com.example.ecommerce.repo.ItemRepository;
import com.example.ecommerce.spec.ItemSpecifications;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired private ItemRepository itemRepository;

    @GetMapping
    public Page<Item> list(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size,
            @RequestParam Optional<BigDecimal> minPrice,
            @RequestParam Optional<BigDecimal> maxPrice,
            @RequestParam Optional<String> category,
            @RequestParam Optional<String> q
    ) {
        Specification<Item> spec = Specification.where(ItemSpecifications.priceGte(minPrice.orElse(null)))
                .and(ItemSpecifications.priceLte(maxPrice.orElse(null)))
                .and(ItemSpecifications.categoryEq(category.orElse(null)))
                .and(ItemSpecifications.nameLike(q.orElse(null)));
        return itemRepository.findAll(spec, PageRequest.of(page.orElse(0), size.orElse(20)));
    }

    @GetMapping("/{id}")
    public Item get(@PathVariable Long id) {
        return itemRepository.findById(id).orElseThrow();
    }

    @PostMapping
    public Item create(@Valid @RequestBody ItemRequest req) {
        Item i = Item.builder()
                .name(req.name())
                .description(req.description())
                .price(req.price())
                .category(req.category())
                .imageUrl(req.imageUrl())
                .build();
        return itemRepository.save(i);
    }

    @PutMapping("/{id}")
    public Item update(@PathVariable Long id, @Valid @RequestBody ItemRequest req) {
        Item i = itemRepository.findById(id).orElseThrow();
        i.setName(req.name());
        i.setDescription(req.description());
        i.setPrice(req.price());
        i.setCategory(req.category());
        i.setImageUrl(req.imageUrl());
        return itemRepository.save(i);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        itemRepository.deleteById(id);
    }
}
