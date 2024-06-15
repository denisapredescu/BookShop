package com.awbd.bookshopspringcloud.repositories;

import com.awbd.bookshopspringcloud.models.Coupon;
import com.awbd.bookshopspringcloud.models.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("mysql")
class CouponRepositoryTest {

    @Mock
    CouponRepository couponRepository;

    @Mock
    UserRepository userRepository;

    @Test
    void findByUser() {
        // Create a user
        User user = new User("username", "email@gmail.com", "password", "firstname", "lastname", true);
        when(userRepository.save(any())).thenReturn(user);

        // Create a coupon associated with the user
        Coupon coupon = new Coupon();
        coupon.setUser(user);
        when(couponRepository.save(any())).thenReturn(coupon);

        // Mock
        when(couponRepository.findByUser(user.getId())).thenReturn(Optional.of(coupon));

        // Call the findByUser method
        Optional<Coupon> foundCoupon = couponRepository.findByUser(user.getId());

        // Assertions
        assertTrue(foundCoupon.isPresent());
        assertNotNull(foundCoupon.get());
        assertEquals(coupon, foundCoupon.get());
    }

    @Test
    void findByUser_notFound() {
        // Mock
        when(couponRepository.findByUser(anyInt())).thenReturn(Optional.empty());

        // Call the findByUser method with a non-existent user id
        Optional<Coupon> foundCoupon = couponRepository.findByUser(999);

        // Assertion
        assertTrue(foundCoupon.isEmpty());
    }

}
