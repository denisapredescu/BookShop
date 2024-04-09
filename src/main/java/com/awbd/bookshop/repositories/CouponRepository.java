package com.awbd.bookshop.repositories;

import com.awbd.bookshop.models.Category;
import com.awbd.bookshop.models.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer>  {
    @Query("SELECT coupon FROM Coupon coupon WHERE coupon.user.id = :userId")
    Optional<Coupon> findByUser(int userId);
}
