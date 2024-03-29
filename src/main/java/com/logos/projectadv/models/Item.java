package com.logos.projectadv.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item implements Comparable<Item>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int itemId;

    private String name;
    private String category;
    private String description;
    private double salary;
    private String image;
    private String location;
    private String phoneNumber;
    private int userId;

    public Item(String name, String category, String description, String phoneNumber,String location, double salary, int userId) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.salary = salary;
        this.userId = userId;
    }



    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "items" )
    private List<Bucket> buckets;


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "items" )
    private List<User> users;


    public Item(int itemId, String name, String description, double salary, String image) {
        this.itemId = itemId;
        this.name = name;
        this.description = description;
        this.salary = salary;
        this.image = image;
    }

    @Override
    public int compareTo(Item o) {
        return (int) (this.getSalary() - o.getSalary());
    }
}
