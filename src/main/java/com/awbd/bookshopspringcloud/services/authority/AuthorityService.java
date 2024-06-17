package com.awbd.bookshopspringcloud.services.authority;

import com.awbd.bookshopspringcloud.models.Authority;
import com.awbd.bookshopspringcloud.repositories.AuthorityRepository;
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
