package com.awbd.bookshopspringcloud.repositories;

import com.awbd.bookshopspringcloud.models.Coupon;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("h2")
@Slf4j
class CouponRepositoryH2Test {

    @Autowired
    CouponRepository couponRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    void findByUser() {
        Optional<Coupon> foundCoupon = couponRepository.findByUser(1);
        assertTrue(foundCoupon.isPresent());
        assertNotNull(foundCoupon.get());

        log.info("findByUser...");
        log.info("Coupon found: " + foundCoupon.isPresent());
        log.info("Coupon id: " + foundCoupon.get().getId());
        log.info("Coupon discount: " + foundCoupon.get().getDiscount());
        log.info("User id with coupon: " + foundCoupon.get().getUser().getId());
    }

    @Test
    void findByUser_notFound() {
        Optional<Coupon> foundCoupon = couponRepository.findByUser(2);
        assertTrue(foundCoupon.isEmpty());

        log.info("findByUser_notFound...");
        log.info("Coupon found: " + foundCoupon.isPresent());
    }

}
