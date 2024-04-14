package com.awbd.bookshop.services.coupon;

import com.awbd.bookshop.models.Coupon;
import com.awbd.bookshop.models.User;

public interface ICouponService {
    Coupon findCoupon(int userId);

    void delete(Coupon coupon);

    Coupon insert(Double discount, User user);
}
