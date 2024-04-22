package com.awbd.bookshop.services.authority;

import com.awbd.bookshop.models.Authority;
import com.awbd.bookshop.repositories.AuthorityRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService implements IAuthorityService{
    AuthorityRepository authorityRepository;
    public AuthorityService(AuthorityRepository authorityRepository){
        this.authorityRepository = authorityRepository;
    }

    @Override
    public Authority getAuthorityByName(String authority){
        return authorityRepository.findByAuthority(authority);
    }
}
