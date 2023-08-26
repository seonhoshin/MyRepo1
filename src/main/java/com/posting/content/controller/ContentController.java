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

    // 실험1 제목으로 찾기
    // 포스트맨에 http://localhost:8080/posting1/v1/contents/bytitle/제목2 로 요청한다.
    @GetMapping("/bytitle/{title}")
    public ResponseEntity getContentsByTitle(@PathVariable("title") String title){

        List<Content> contents = contentService.findContentsByTitle(title);

        List<ContentResponseDto> response =
                contents.stream().map(content -> mapper.contentToContentResponseDto(content))
                        .collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 실험2 첨부에 포함 찾기
    // 포스트맨에http://localhost:8080/posting1/v1/contents/byfile/첨부2 로 요청한다.
    @GetMapping("/byfile/{attachFile}")
    public ResponseEntity getContentsByAttachFilePlus(@PathVariable("attachFile") String attachFile){

        List<Content> contents = contentService.findContentsByAttachFilePlus(attachFile);

        List<ContentResponseDto> response =
                contents.stream().map(content -> mapper.contentToContentResponseDto(content))
                        .collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 실험3 조건 2개로 조회
    // 포스트맨에 http://localhost:8080/posting1/v1/contents/bytwo?title=제목1&attachFile=첨부1 로 요청한다.
    @GetMapping("/bytwo")
    public ResponseEntity getContentsByTwo(@RequestParam String title, @RequestParam String attachFile){

        List<Content> contents = contentService.findContentsByTwo(title, attachFile);

        List<ContentResponseDto> response =
                contents.stream().map(content -> mapper.contentToContentResponseDto(content))
                        .collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 실험4 특정 값하나 조회
    // 포스트맨에 http://localhost:8080/posting1/v1/contents/byspeci/4 로 요청한다.
    @GetMapping("/byspeci/{notice-id}")
    public ResponseEntity getContentsBySpeci(@PathVariable("notice-id") @Positive long noticeId){

        String contents = contentService.findContentsBySpeci(noticeId);

        return new ResponseEntity<>(contents, HttpStatus.OK);
    }

    // 실험4-2 쿼리로 특정 값하나 조회
    // 포스트맨에 http://localhost:8080/posting1/v1/contents/byspeci2/4 로 요청한다.
    @GetMapping("/byspeci2/{notice-id}")
    public ResponseEntity getContentsBySpeci2(@PathVariable("notice-id") @Positive long noticeId){

        String contents = contentService.findContentsBySpeci2(noticeId);

        return new ResponseEntity<>(contents, HttpStatus.OK);
    }

    @DeleteMapping("/{notice-id}")
    public ResponseEntity deleteContent(@PathVariable("notice-id") @Positive long noticeId){

        contentService.deleteContent(noticeId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
