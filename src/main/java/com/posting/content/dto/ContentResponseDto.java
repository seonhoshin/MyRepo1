package com.posting.content.dto;


import com.posting.category.entity.Category;
import com.posting.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ContentResponseDto {
    private long noticeId;
    private String title;
    private String content;
    private String attachFile;
    private long memberId;
    private long categoryId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public void setMember(Member member){
        this.memberId = member.getMemberId();
    }

    public void setCategory(Category category){
        this.categoryId = category.getCategoryId();
    }


}
