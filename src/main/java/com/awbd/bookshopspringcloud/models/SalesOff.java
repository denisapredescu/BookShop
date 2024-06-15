package com.awbd.bookshopspringcloud.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class SalesOff {
    private String authors; //list of authors for Sales off
    private String categories; //list of categories for Sales Off
    private int lowSalesOff;
    private int lowNoBooks; // the minimum limit of books that enter for the low discount
    private int mediumSalesOff;
    private int mediumNoBooks;//the maximum limit of books that enter for the low discount and minimum -1 for medium discount
    private int highSalesOff;
    private int highNoBooks;// the maximum limit for medium and the minimum - 1 limit of books that enter for the high discount
    private String versionId;
}