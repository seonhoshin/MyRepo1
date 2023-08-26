package com.posting.content.service;

import com.posting.category.entity.Category;
import com.posting.content.entity.Content;
import com.posting.content.repository.ContentRepository;
import com.posting.exception.BusinessLogicException;
import com.posting.exception.ExceptionCode;
import com.posting.member.entity.Member;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ContentService {

    private final ContentRepository contentRepository;

    public ContentService(ContentRepository contentRepository){
        this.contentRepository = contentRepository;
    }

    public Content createContent(Content content){

        // JPA 전
//        Content createdContent = content;
//        return createdContent;

        return contentRepository.save(content);

    }

    public Content updateContent(Content content){

        // JPA 전
//        Content updateContent = content;
//        return updateContent;

        Content findContent = findContent(content.getNoticeId());

        Optional.ofNullable(content.getTitle())
                .ifPresent(title -> findContent.setTitle(title));
        Optional.ofNullable(content.getContent())
                .ifPresent(content2 -> findContent.setContent(content2));
        Optional.ofNullable(content.getAttachFile())
                .ifPresent(file -> findContent.setAttachFile(file));
        Optional.ofNullable(content.getCategory())
                .ifPresent(category1 -> findContent.setCategory(category1));
        Optional.ofNullable(content.getMember())
                .ifPresent(member1 -> findContent.setMember(member1));

        findContent.setModifiedAt(LocalDateTime.now());

        return contentRepository.save(findContent);
    }

    public Content findContent(long noticeId){

        // JPA 전
//        Content content = new Content();
//        content.setNoticeId(noticeId);
//        content.setTitle("제목1");
//        content.setContent("내용내용1");
//        content.setAttachFile("첨부1");
//        // 만약 content.createdAt 빼서 안나올 경우... 수동으로 넣어..
//        content.setCreatedAt(LocalDateTime.now());
//        content.setModifiedAt(LocalDateTime.now());
//        Member member = new Member();
//        member.setMemberId(1L);
//        content.setMember(member);
//        Category category = new Category();
//        category.setCategoryId(1L);
//        content.setCategory(category);
//
//        return content;

        Optional<Content> optionalContent =
                contentRepository.findById(noticeId);

        Content findContent =
                optionalContent.orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOUND));

        return findContent;
    }

    // 추후 페이징 예정
    public List<Content> findContents(){

        // JPA 전
//        List<Content> contents = List.of(
//                new Content(1L,"제목1","내용내용1","첨부1",new Category(),new Member(),LocalDateTime.now(), LocalDateTime.now()),
//                new Content(2L,"제목2","내용내용2","첨부2",new Category(),new Member(),LocalDateTime.now(), LocalDateTime.now())
//        );
//
//        return contents;

        return contentRepository.findAll();

    }

    public void deleteContent(long noticeId){

        Content findContent = findContent(noticeId);

        contentRepository.delete(findContent);
    }
}
