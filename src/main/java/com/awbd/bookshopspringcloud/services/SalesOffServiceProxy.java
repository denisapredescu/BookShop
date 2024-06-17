package com.awbd.bookshopspringcloud.services;

import com.awbd.bookshopspringcloud.models.SalesOff;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "salesOff")
public interface SalesOffServiceProxy {
    @GetMapping("/salesOff")
    SalesOff getingSalesOff();
}
