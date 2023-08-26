package com.posting.category.entity;

import com.posting.content.entity.Content;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long categoryId;

    @Column(length = 100, nullable = false, updatable = true)
    private String categoryName;

    public Category(long categoryId) {
        this.categoryId = categoryId;
    }

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public Category(long categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    //@OneToMany @JoinColumn으로 할지, @OneToMany(mappedBy로 할지...)
    @OneToMany(mappedBy = "content")
    private List<Content> contents = new ArrayList<>();

    public void addContent(Content content) {
        this.contents.add(content);
    }




}
