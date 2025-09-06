package com.example.ecommerce.config;

import com.example.ecommerce.model.Item;
import com.example.ecommerce.repo.ItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner seedItems(ItemRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                repo.saveAll(List.of(
                    Item.builder().name("Classic T-Shirt").description("100% cotton").price(new BigDecimal("599")).category("Apparel").imageUrl("https://picsum.photos/seed/tshirt/400").build(),
                    Item.builder().name("Running Shoes").description("Lightweight").price(new BigDecimal("1999")).category("Footwear").imageUrl("https://picsum.photos/seed/shoes/400").build(),
                    Item.builder().name("Wireless Headphones").description("Over-ear, Bluetooth").price(new BigDecimal("2999")).category("Electronics").imageUrl("https://picsum.photos/seed/headphones/400").build(),
                    Item.builder().name("Coffee Mug").description("Ceramic 350ml").price(new BigDecimal("249")).category("Home").imageUrl("https://picsum.photos/seed/mug/400").build()
                ));
            }
        };
    }
}
