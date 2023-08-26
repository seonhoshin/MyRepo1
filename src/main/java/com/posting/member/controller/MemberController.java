package com.posting.member.controller;

import com.posting.content.entity.Content;
import com.posting.member.dto.MemberPatchDto;
import com.posting.member.dto.MemberPostDto;
import com.posting.member.dto.MemberResponseDto;
import com.posting.member.entity.Member;
import com.posting.member.mapper.MemberMapper;
import com.posting.member.service.MemberService;
import com.posting.utils.UriCreator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/members")
@Validated
public class MemberController {

    private final static String MEMBER_DEFAULT_URL = "/v1/members";

    private final MemberService memberService;
    private final MemberMapper mapper;

    public MemberController(MemberService memberService, MemberMapper mapper){
        this.memberService = memberService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity postMember(@Valid @RequestBody MemberPostDto requestBody){

        // mapper 역할
//        Content content = new Content();
//        List<Content> contents = new ArrayList<>();
//        contents.add(content);
//
//        Member member = new Member();
//        member.setName(requestBody.getName());
//        member.setEmail(requestBody.getEmail());
//        member.setPhone(requestBody.getPhone());
//        member.setContents(contents);

        Member membermapper = mapper.memberPostDtoToMember(requestBody);

        Member member = memberService.createMember(membermapper);

        URI location = UriCreator.createUri(MEMBER_DEFAULT_URL, member.getMemberId());

//        return new ResponseEntity<>(
//                mapper.memberToMemberResponseDto(response)
//                , HttpStatus.CREATED);
        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{member-id}")
    public ResponseEntity patchMember(@PathVariable("member-id") @Positive long memberId,
                                      @Valid @RequestBody MemberPatchDto requestBody){

        requestBody.setMemberId(memberId);

        // mapper 역할
//        Content content = new Content();
//        List<Content> contents = new ArrayList<>();
//        contents.add(content);
//
//        Member member = new Member();
//        member.setName(requestBody.getName());
//        member.setEmail(requestBody.getEmail());
//        member.setPhone(requestBody.getPhone());
//        member.setContents(contents);

        Member member = mapper.memberPatchDtoToMember(requestBody);

        Member response = memberService.updateMember(member);

        return new ResponseEntity<>(
                mapper.memberToMemberResponseDto(response)
                , HttpStatus.OK);

    }

    @GetMapping("/{member-id}")
    public ResponseEntity getMember(@PathVariable("member-id") @Positive long memberId){

        Member response = memberService.findMember(memberId);

        return new ResponseEntity<>(
                mapper.memberToMemberResponseDto(response)
                , HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getMembers(){

        List<Member> members = memberService.findMembers();

        List<MemberResponseDto> response =
                members.stream()
                        .map(member -> mapper.memberToMemberResponseDto(member))
                        .collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember(@PathVariable("member-id") @Positive long memberId){

        memberService.deleteMember(memberId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
