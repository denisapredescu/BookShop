package com.awbd.bookshop.repositories;

import com.awbd.bookshop.models.BookBasket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookBasketRepository extends JpaRepository<BookBasket, Integer> {
    @Query("SELECT b FROM BookBasket b WHERE b.book.id = :bookId AND b.basket.id = :basketId")
    Optional<BookBasket> findBookInBasket(Integer bookId, Integer basketId);
}
