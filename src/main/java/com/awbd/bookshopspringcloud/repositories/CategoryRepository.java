//package com.awbd.bookshop.repositories;
//
//import com.awbd.bookshop.models.Category;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//
//@Repository
//public interface CategoryRepository extends JpaRepository<Category, Integer> {
//    @Query("SELECT c FROM Category c WHERE c.name = :name")
//    Optional<Category> findByName(String name);
//}
