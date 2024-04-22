package com.awbd.bookshop.services.authority;

import com.awbd.bookshop.models.Authority;
import com.awbd.bookshop.repositories.AuthorityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthorityServiceTest {
    @Mock
    private AuthorityRepository authorityRepository;

    @InjectMocks
    private AuthorityService authorityService;

    @Test
    public void getAuthorityByName()
    {
        Authority authority = new Authority("ROLE_USER");
        when(authorityRepository.findByAuthority("ROLE_USER")).thenReturn(authority);

        Authority actualAuthority = authorityService.getAuthorityByName("ROLE_USER");
        assertEquals(authority, actualAuthority);
    }
}
