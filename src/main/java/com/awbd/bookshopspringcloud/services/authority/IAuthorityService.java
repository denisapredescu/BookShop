package com.awbd.bookshopspringcloud.services.authority;

import com.awbd.bookshopspringcloud.models.Authority;

public interface IAuthorityService {
    Authority getAuthorityByName(String authority);
}
