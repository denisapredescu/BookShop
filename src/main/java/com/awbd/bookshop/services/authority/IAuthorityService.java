package com.awbd.bookshop.services.authority;

import com.awbd.bookshop.models.Authority;

public interface IAuthorityService {
    Authority getAuthorityByName(String authority);
}
