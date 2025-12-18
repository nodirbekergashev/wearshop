package com.powerofwear.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.powerofwear.common.base.BaseEntity;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "categories",
        indexes = {
                @Index(name = "idx_category_slug", columnList = "slug", unique = true),
                @Index(name = "idx_category_parent", columnList = "parent_id")
        })
@Getter
@Setter
public class Category extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String name;

    @Column(unique = true, nullable = false, length = 120)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String description;
}