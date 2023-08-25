package com.posting.content.mapper;

import com.posting.category.entity.Category;
import com.posting.content.dto.ContentResponseDto;
import com.posting.content.dto.ContentPatchDto;
import com.posting.content.dto.ContentPostDto;
import com.posting.content.entity.Content;
import com.posting.member.entity.Member;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface ContentMapper {

    default Content contentPostDtoToContent(ContentPostDto contentPostDto){
        Content content = new Content();

        content.setTitle(contentPostDto.getTitle());
        content.setContent(contentPostDto.getContent());
        content.setAttachFile(contentPostDto.getAttachFile());
        Member member = new Member();
        member.setMemberId(contentPostDto.getMemberId());
        content.setMember(member);
        Category category = new Category();
        category.setCategoryId(contentPostDto.getCategoryId());
        content.setCategory(category);
        // 만약 content.createdAt 빼서 안나올 경우... 수동으로 넣어.
//        content.setCreatedAt(LocalDateTime.now());
//        content.setModifiedAt(LocalDateTime.now());

        return content;

    }

    default Content contentPatchDtoToContent(ContentPatchDto contentPatchDto){

        Content content = new Content();

        content.setNoticeId(contentPatchDto.getNoticeId());
        content.setTitle(contentPatchDto.getTitle());
        content.setContent(contentPatchDto.getContent());
        content.setAttachFile(contentPatchDto.getAttachFile());
        Category category = new Category();
        category.setCategoryId(contentPatchDto.getCategoryId());
        content.setCategory(category);
        Member member = new Member();
        member.setMemberId(contentPatchDto.getMemberId());
        content.setMember(member);
        // 만약 content.createdAt 빼서 안나올 경우... 수동으로 넣어.
//        content.setCreatedAt(LocalDateTime.now());
//        content.setModifiedAt(LocalDateTime.now());

        return content;
    }

    default ContentResponseDto contentToContentResponseDto(Content content){
        ContentResponseDto contentResponseDto = new ContentResponseDto();

        contentResponseDto.setNoticeId(content.getNoticeId());
        contentResponseDto.setTitle(content.getTitle());
        contentResponseDto.setContent(content.getContent());
        contentResponseDto.setAttachFile(content.getAttachFile());
        contentResponseDto.setCategoryId(content.getCategory().getCategoryId());
        contentResponseDto.setMemberId(content.getMember().getMemberId());
        contentResponseDto.setCreatedAt(content.getCreatedAt());
        contentResponseDto.setModifiedAt(content.getModifiedAt());

        return contentResponseDto;
    }


}
