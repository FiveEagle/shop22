package com.example.pmproject.Entity;

import lombok.*;

import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.util.Date;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "product")
@SequenceGenerator(sequenceName = "product_SEQ", name = "product_SEQ", allocationSize = 1)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_SEQ")
    private Long productId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer price;


    @Column
    private String img;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<ProductComment> productCommentList;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date createdAt;

}
