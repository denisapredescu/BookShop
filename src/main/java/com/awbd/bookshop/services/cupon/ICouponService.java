package com.awbd.bookshop.services.cupon;

import com.awbd.bookshop.models.Coupon;
import com.awbd.bookshop.models.User;

import java.util.Optional;

public interface ICouponService {
    Coupon findCoupon(int userId);

    void delete(Coupon coupon);

    Coupon insert(Double discount, User user);
}
