package com.posting.member.entity;

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
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long memberId;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 100)
    private String email;

    @Column(length = 13)
    private String phone;

    public Member(long memberId) {
        this.memberId = memberId;
    }

    public Member(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public Member(long memberId, String name, String email, String phone) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    //@OneToMany @JoinColumn으로 할지, @OneToMany(mappedBy로 할지...)
    @OneToMany(mappedBy = "content")
    private List<Content> contents = new ArrayList<>();

    public void addContent(Content content) {
        this.contents.add(content);
    }

}
