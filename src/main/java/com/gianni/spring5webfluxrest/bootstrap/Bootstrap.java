package com.gianni.spring5webfluxrest.bootstrap;

import com.gianni.spring5webfluxrest.domain.Category;
import com.gianni.spring5webfluxrest.domain.Vendor;
import com.gianni.spring5webfluxrest.repository.CategoryRepository;
import com.gianni.spring5webfluxrest.repository.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {
    private CategoryRepository categoryRepository;
    private VendorRepository vendorRepository;


    public Bootstrap(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("###########BOOTSTRAP###########");
        if (categoryRepository.count().block() == 0) {
            System.out.println("###########LOADING CATEGORY DATA###########");
            Category cat1 = new Category();
            cat1.setDescription("This is the First Category");
            Category cat2 = new Category();
            cat2.setDescription("This is the Second Category");
            Category cat3 = new Category();
            cat3.setDescription("This is the Third Category");
            categoryRepository.save(cat1).block();
            categoryRepository.save(cat2).block();
            categoryRepository.save(cat3).block();
        }
        if (vendorRepository.count().block() == 0) {
            System.out.println("###########LOADING VENDOR DATA###########");
            Vendor ven1 = new Vendor();
            ven1.setName("Gianni");
            ven1.setLastName("Fabriziani");
            Vendor ven2 = new Vendor();
            ven2.setName("Luisa");
            ven2.setLastName("Fabriziani");
            Vendor ven3 = new Vendor();
            ven3.setName("Iulia");
            ven3.setLastName("Zelinca");
            vendorRepository.save(ven1).block();
            vendorRepository.save(ven2).block();
            vendorRepository.save(ven3).block();
        }
    }


    private void loadCategories() {

    }

    private void loadVendors() {

    }

}
