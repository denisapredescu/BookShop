package com.awbd.bookshop.services.coupon;

import com.awbd.bookshop.models.Coupon;
import com.awbd.bookshop.models.User;
import com.awbd.bookshop.repositories.CouponRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CouponServiceTest {
    @Mock
    private CouponRepository couponRepository;

    @InjectMocks
    private CouponService couponServiceUnderTest;

    @Test
    public void findCoupon_CouponFound() {
        int userId = 123;
        Coupon expectedCoupon = new Coupon(10.0, new User());

        when(couponRepository.findByUser(userId)).thenReturn(Optional.of(expectedCoupon));

        Coupon actualCoupon = couponServiceUnderTest.findCoupon(userId);
        assertEquals(expectedCoupon, actualCoupon);
    }

    @Test
    public void testFindCoupon_CouponNotFound() {
        int userId = 123;

        when(couponRepository.findByUser(userId)).thenReturn(Optional.empty());

        Coupon actualCoupon = couponServiceUnderTest.findCoupon(userId);
        assertNull(actualCoupon);
    }

    @Test
    public void delete() {
        Coupon coupon = new Coupon(10.0, new User());

        couponServiceUnderTest.delete(coupon);
        verify(couponRepository).delete(coupon);
    }

    @Test
    public void insert() {
        Double discount = 10.0;
        User user = new User();
        Coupon couponToSave = new Coupon(discount, user);
        Coupon savedCoupon = new Coupon(null, discount, user);

        when(couponRepository.save(couponToSave)).thenReturn(savedCoupon);

        Coupon actualCoupon = couponServiceUnderTest.insert(discount, user);
        assertNotNull(actualCoupon);
        assertEquals(savedCoupon, actualCoupon);
    }
}