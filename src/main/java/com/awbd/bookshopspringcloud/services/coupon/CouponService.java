package com.awbd.bookshopspringcloud.services.coupon;

import com.awbd.bookshopspringcloud.models.Coupon;
import com.awbd.bookshopspringcloud.models.User;
import com.awbd.bookshopspringcloud.repositories.CouponRepository;
import org.springframework.stereotype.Service;

@Service
public class CouponService implements ICouponService {
    final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public Coupon findCoupon(int userId) {
        return couponRepository.findByUser(userId).orElse(null);
    }

    @Override
    public void delete(Coupon coupon) {
        couponRepository.delete(coupon);
    }

    @Override
    public Coupon insert(Double discount, User user) {
        return couponRepository.save(new Coupon(discount, user));
    }
}
