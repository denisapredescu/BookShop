package com.awbd.bookshopspringcloud.services.coupon;

import com.awbd.bookshopspringcloud.models.Coupon;
import com.awbd.bookshopspringcloud.models.User;

public interface ICouponService {
    Coupon findCoupon(int userId);

    void delete(Coupon coupon);

    Coupon insert(Double discount, User user);
}
