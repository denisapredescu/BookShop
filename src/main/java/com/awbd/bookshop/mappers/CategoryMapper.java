package com.awbd.bookshop.mappers;

import com.awbd.bookshop.dtos.RequestCategory;
import com.awbd.bookshop.dtos.RequestUser;
import com.awbd.bookshop.models.Category;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Request;

@Component
public class CategoryMapper {
    public Category requestCategory(String category) {
        return new Category(category);
    }
}
