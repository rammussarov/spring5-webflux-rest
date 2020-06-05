package dev.demo.spring5webfluxrest.bootstrap;

import dev.demo.spring5webfluxrest.domain.Category;
import dev.demo.spring5webfluxrest.domain.Vendor;
import dev.demo.spring5webfluxrest.repositories.CategoryRepository;
import dev.demo.spring5webfluxrest.repositories.VendorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final VendorRepository vendorRepository;

    @Override
    public void run(String... args) throws Exception {
        bootstrapCategories();
        bootstrapVendors();
    }

    private void bootstrapCategories() {
        if (categoryRepository.count().block() > 0) {
            return;
        }

        log.info("######## LOADING CATEGORIES ON BOOTSTRAP ########");

        categoryRepository.save(Category.builder().description("Fruits").build()).block();
        categoryRepository.save(Category.builder().description("Nuts").build()).block();
        categoryRepository.save(Category.builder().description("Breads").build()).block();
        categoryRepository.save(Category.builder().description("Meats").build()).block();
        categoryRepository.save(Category.builder().description("Eggs").build()).block();

        log.info("Categories loaded. Total count: {}", categoryRepository.count().block());
    }

    private void bootstrapVendors() {
        if (vendorRepository.count().block() > 0) {
            return;
        }

        log.info("######## LOADING VENDORS ON BOOTSTRAP ########");

        vendorRepository.save(Vendor.builder().firstName("Joe").lastName("Back").build()).block();
        vendorRepository.save(Vendor.builder().firstName("Michael").lastName("Weston").build()).block();
        vendorRepository.save(Vendor.builder().firstName("Jessie").lastName("Waters").build()).block();
        vendorRepository.save(Vendor.builder().firstName("Bill").lastName("Wershi").build()).block();
        vendorRepository.save(Vendor.builder().firstName("Jimmy").lastName("Buffet").build()).block();

        log.info("Vendors loaded. Total count: {}", vendorRepository.count().block());
    }
}
