package com.awbd.bookshop.repositories;

import com.awbd.bookshop.models.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
    Authority findByAuthority(String roleUser);
}
