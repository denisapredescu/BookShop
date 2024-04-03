package com.awbd.bookshop.repositories;

import com.awbd.bookshop.models.Book;
import com.awbd.bookshop.models.Coupon;
import com.awbd.bookshop.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("mysql")
class CouponRepositoryTest {
    @Autowired
    CouponRepository couponRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    void findByUser() {
        User user = new User("username", "email@gmail.com", "password", "firstname", "lastname", true);
        User savedUser = userRepository.save(user);
        assertNotNull(savedUser);

        Coupon coupon = new Coupon();
        coupon.setUser(user);
        Coupon savedCoupon = couponRepository.save(coupon);
        assertNotNull(savedCoupon);

        Optional<Coupon> foundCoupon = couponRepository.findByUser(savedUser.getId());
        assertTrue(foundCoupon.isPresent());
        assertNotNull(foundCoupon.get());
        assertEquals(savedCoupon, foundCoupon.get());
    }

    @Test
    void findByUser_notFound() {
        int nonExistentUserId = 999;

        Optional<Coupon> foundCoupon = couponRepository.findByUser(nonExistentUserId);
        assertTrue(foundCoupon.isEmpty());
    }

//    @Test
//    void delete() {
//        User user = new User("username", "email@gmail.com", "password", "firstname", "lastname", true);
//        User savedUser = userRepository.save(user);
//        assertNotNull(savedUser);
//
//        Coupon coupon = new Coupon();
//        coupon.setUser(user);
//        Coupon savedCoupon = couponRepository.save(coupon);
//        assertNotNull(savedCoupon);
//
//        Optional<Coupon> foundCoupon = couponRepository.findByUser(savedUser.getId());
//        assertTrue(foundCoupon.isPresent());
//        assertNotNull(foundCoupon.get());
//        assertEquals(savedCoupon, foundCoupon.get());
//
//        System.out.println("Deleted User ID: " + savedUser.getId());
//
//        couponRepository.delete(savedUser.getId());
//        Optional<Coupon> notFoundCoupon = couponRepository.findByUser(savedUser.getId());
//        System.out.println("Deleted User ID: " + notFoundCoupon.get());
//        assertTrue(notFoundCoupon.isEmpty());
//        assertNull(notFoundCoupon.get());
//    }
}