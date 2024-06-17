package com.awbd.bookshopspringcloud.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "books")
//public class Book {
public class Book extends RepresentationModel<Book> {//extends RepresentationModel<Book>  for links
    @Id
    @GeneratedValue
    private int id;

    @Column(name="name")
    @NotNull
    @NotEmpty(message = "The name cannot be blank!")
    private String name;

    @Column(name = "price")
    @Min(value = 0)
    @NotNull(message = "The price must be set!")
    @Positive(message = "Price should be greater than zero")
    private double price;

    @Column(name = "year_date")
    @Min(value = 0)
    private Integer year = 0;

    @Column(name = "volume")
    @Min(value = 0)
    private Integer volume = 0;

    @Column(name = "series_name")
    private String series_name = null;

    @Column(name = "is_deleted")
    private Boolean is_deleted = false;

    @Column(name = "author_name")
    @NotEmpty(message = "The name cannot be blank!")
    private String author_name;

    @Column(name = "categories")
    @NotEmpty(message = "The category cannot be blank!")
    private String categories;

//    @ManyToOne(targetEntity = Author.class)
//    @PrimaryKeyJoinColumn(name = "author_id")
//    private Author author;

//    @ManyToMany(targetEntity = Category.class)
//    private List<Category> bookCategories = null;

    @OneToMany(mappedBy = "book")
    @JsonManagedReference
    private List<BookBasket> bookBaskets = null;

    public Book() {
    }

    public Book(int id, String name, double price, int year, int volume, String series_name, Boolean is_deleted, String author_name,
                String categories) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.year = year;
        this.volume = volume;
        this.series_name = series_name;
        this.is_deleted = is_deleted;
        this.author_name = author_name;
        this.categories = categories;
    }

    public Book(String name, double price, int year, int volume, String series_name, Boolean is_deleted,String author_name,
                String categories) {
        this.name = name;
        this.price = price;
        this.year = year;
        this.volume = volume;
        this.series_name = series_name;
        this.is_deleted = is_deleted;
        this.author_name = author_name;
        this.categories = categories;
    }

//    public Book(int id, String name, double price, int year, int volume, String series_name, Boolean is_deleted) {
//        this.id = id;
//        this.name = name;
//        this.price = price;
//        this.year = year;
//        this.volume = volume;
//        this.series_name = series_name;
//        this.is_deleted = is_deleted;
//    }

//    public Book(int id,
//                String name,
//                double price,
//                int year,
//                Boolean is_deleted,
//                int volume,
//                String series_name,
//                Author author) {
//        this.id = id;
//        this.name = name;
//        this.price = price;
//        this.year = year;
//        this.is_deleted = is_deleted;
//        this.volume = volume;
//        this.series_name = series_name;
//        this.author = author;
//    }

//    public Book(int id, String name, double price, int year, int volume, String series_name, Boolean is_deleted, Author author, List<Category> bookCategories) {
//        this.id = id;
//        this.name = name;
//        this.price = price;
//        this.year = year;
//        this.volume = volume;
//        this.series_name = series_name;
//        this.is_deleted = is_deleted;
//        this.author = author;
//        this.bookCategories = bookCategories;
//    }

//    public Book(String name, double price, int year, int volume, String series_name, Boolean is_deleted) {
//        this.name = name;
//        this.price = price;
//        this.year = year;
//        this.volume = volume;
//        this.series_name = series_name;
//        this.is_deleted = is_deleted;
//    }

    public Book(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public Book(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Boolean getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(Boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getSeries_name() {
        return series_name;
    }

    public void setSeries_name(String series_name) {
        this.series_name = series_name;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public List<BookBasket> getBookBaskets() {
        return bookBaskets;
    }

    public void setBookBaskets(List<BookBasket> bookBaskets) {
        this.bookBaskets = bookBaskets;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && Double.compare(price, book.price) == 0 && Objects.equals(name, book.name) && Objects.equals(year, book.year) && Objects.equals(volume, book.volume) && Objects.equals(series_name, book.series_name) && Objects.equals(is_deleted, book.is_deleted) && Objects.equals(author_name, book.author_name) && Objects.equals(categories, book.categories) && Objects.equals(bookBaskets, book.bookBaskets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, year, volume, series_name, is_deleted, author_name, categories, bookBaskets);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", year=" + year +
                ", volume=" + volume +
                ", series_name='" + series_name + '\'' +
                ", is_deleted=" + is_deleted +
                ", author_name='" + author_name + '\'' +
                ", categories='" + categories + '\'' +
                ", bookBaskets=" + bookBaskets +
                '}';
    }
}
