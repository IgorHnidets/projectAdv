package com.logos.projectadv.models;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
//@Data
public class Bucket {

    @Id
    private int bucketId;

    private Timestamp created_date;

    @OneToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private User user;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "items_bucket",
            joinColumns = @JoinColumn(name = "bucketId"),
            inverseJoinColumns = @JoinColumn(name = "itemId"))
    @ToString.Exclude
    private List<Item> items;

    public Bucket(int id, Timestamp created_date, User user) {
        this.bucketId = id;
        this.created_date = created_date;
        this.user = user;
    }

}
