package com.awbd.bookshop.repositories;

import com.awbd.bookshop.dtos.BookFromBasketDetails;
import com.awbd.bookshop.models.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Integer> {
    @Query("SELECT b FROM Basket b WHERE b.user.id = :userId AND b.sent = false")
    Optional<Basket> findByUserId(Integer userId);

    @Query("SELECT new com.awbd.bookshop.dtos.BookFromBasketDetails(bookBasket.book.name, bookBasket.price, bookBasket.copies, bookBasket.book.id) " +
            "FROM BookBasket bookBasket " +
            "WHERE bookBasket.basket.id = :basketId")
    List<BookFromBasketDetails> findBooksFromCurrentBasket(Integer basketId);
}
