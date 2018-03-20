package com.gianni.spring5webfluxrest.controllers;

import com.gianni.spring5webfluxrest.domain.Category;
import com.gianni.spring5webfluxrest.domain.Vendor;
import com.gianni.spring5webfluxrest.repository.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class VendorControllerTest {
    WebTestClient webTestClient;
    VendorRepository vendorRepository;
    VendorController vendorController;

    @Before
    public void setUp() throws Exception {
        vendorRepository = Mockito.mock(VendorRepository.class);
        vendorController = new VendorController(vendorRepository);
        webTestClient = WebTestClient.bindToController(vendorController).build();
    }

    @Test
    public void list() {
        given(vendorRepository.findAll()).willReturn(Flux.just(Vendor.builder().name("Ven1").build(),
                Vendor.builder().name("Ven2").build(), Vendor.builder().name("Ven3").build()));
        webTestClient.get().uri("/api/v1/vendors").exchange().expectBodyList(Vendor.class).hasSize(3);
    }

    @Test
    public void getById() {
        given(vendorRepository.findById("someid")).willReturn(Mono.just(Vendor.builder().name("Ven1")
                .build()));
        webTestClient.get().uri("/api/v1/vendors/someid").exchange().expectBody(Vendor.class);
    }

    @Test
    public void testCreateVendor() {
        given(vendorRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Vendor.builder().build()));

        Mono<Vendor> vendToSaveMono = Mono.just(Vendor.builder().name("Some Vendor").build());
        webTestClient
                .post()
                .uri("/api/v1/vendors")
                .body(vendToSaveMono, Vendor.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    public void testUpdateVendor() {
        given(vendorRepository.save(any(Vendor.class))).willReturn(Mono.just(Vendor.builder().build
                ()));
        Mono<Vendor> vendToUpdate = Mono.just(Vendor.builder().name("Some Vendor").build());
        webTestClient
                .put()
                .uri("/api/v1/vendors/someid")
                .body(vendToUpdate, Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void testPatchVendorWithChanges() {
        given(vendorRepository.findById("someid")).willReturn(Mono.just(Vendor.builder().build()));
        given(vendorRepository.save(any(Vendor.class))).willReturn(Mono.just(Vendor.builder().build
                ()));
        Mono<Vendor> vendToUpdateMono = Mono.just(Vendor.builder().name("Some Vendor").build());
        webTestClient
                .patch()
                .uri("/api/v1/vendors/someid")
                .body(vendToUpdateMono, Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();
        verify(vendorRepository).save(any());
    }

    @Test
    public void testPatchVendorNoChanges() {
        given(vendorRepository.findById("someid")).willReturn(Mono.just(Vendor.builder().build()));
        given(vendorRepository.save(any(Vendor.class))).willReturn(Mono.just(Vendor.builder().build
                ()));
        Mono<Vendor> vendToUpdateMono = Mono.just(Vendor.builder().build());
        webTestClient
                .patch()
                .uri("/api/v1/vendors/someid")
                .body(vendToUpdateMono, Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();
        verify(vendorRepository, never()).save(any());

    }
}