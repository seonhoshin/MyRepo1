package com.posting.content.controller;

import com.posting.category.entity.Category;
import com.posting.content.dto.ContentPatchDto;
import com.posting.content.dto.ContentPostDto;
import com.posting.content.dto.ContentResponseDto;
import com.posting.content.entity.Content;
import com.posting.content.mapper.ContentMapper;
import com.posting.content.service.ContentService;
import com.posting.member.entity.Member;
import com.posting.utils.UriCreator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/contents")
@Validated
public class ContentController {

    private final static String CONTENT_DEFAULT_URL = "/v1/contents";
    private final ContentService contentService;
    private final ContentMapper mapper;

    public ContentController(ContentService contentService, ContentMapper mapper){
        this.contentService = contentService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity postContent(@Valid @RequestBody ContentPostDto requestBody){

        // mapper 역할
//        Content content = new Content();
//        content.setTitle(requestBody.getTitle());
//        content.setContent(requestBody.getContent());
//        content.setAttachFile(requestBody.getAttachFile());
//        Category category = new Category();
//        category.setCategoryId(requestBody.getCategoryId());
//        content.setCategory(category);
//        Member member = new Member();
//        member.setMemberId(requestBody.getMemberId());
//        content.setMember(member);
//        // 만약 content.createdAt 빼서 안나올 경우... 수동으로 넣어..
//        //content.setCreatedAt(LocalDateTime.now());
//        //content.setModifiedAt(LocalDateTime.now());

        Content contentmapper = mapper.contentPostDtoToContent(requestBody);

        Content content = contentService.createContent(contentmapper);

        URI location = UriCreator.createUri(CONTENT_DEFAULT_URL, content.getNoticeId());

        // uri를 보내기 위해 대체
//        return new ResponseEntity<>(
//                mapper.contentToContentResponseDto(response)
//                ,HttpStatus.CREATED);

        return ResponseEntity.created(location).build();

    }

    @PatchMapping("/{notice-id}")
    public ResponseEntity patchContent(@PathVariable("notice-id") @Positive long noticeId,
                                       @Valid @RequestBody ContentPatchDto requestBody){

        requestBody.setNoticeId(noticeId);

        // mapper 역할
//        Content content = new Content();
//        content.setTitle(requestBody.getTitle());
//        content.setContent(requestBody.getContent());
//        content.setAttachFile(requestBody.getAttachFile());
//        Category category = new Category();
//        category.setCategoryId(requestBody.getCategoryId());
//        content.setCategory(category);
//        Member member = new Member();
//        member.setMemberId(requestBody.getMemberId());
//        content.setMember(member);
//        // 만약 content.createdAt 빼서 안나올 경우... 수동으로 넣어..
//        //content.setCreatedAt(LocalDateTime.now());
//        //content.setModifiedAt(LocalDateTime.now());

        Content content = mapper.contentPatchDtoToContent(requestBody);

        Content response = contentService.updateContent(content);

        return new ResponseEntity<>(
                mapper.contentToContentResponseDto(response)
                , HttpStatus.OK);
    }

    @GetMapping("/{notice-id}")
    public ResponseEntity getContent(@PathVariable("notice-id") @Positive long noticeId){

        Content response = contentService.findContent(noticeId);

        return new ResponseEntity<>(
                mapper.contentToContentResponseDto(response)
                , HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getContents(){

        List<Content> contents = contentService.findContents();

        List<ContentResponseDto> response =
                contents.stream().map(content -> mapper.contentToContentResponseDto(content))
                        .collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{notice-id}")
    public ResponseEntity deleteContent(@PathVariable("notice-id") @Positive long noticeId){

        contentService.deleteContent(noticeId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
