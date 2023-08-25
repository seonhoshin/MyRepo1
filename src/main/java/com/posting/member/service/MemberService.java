package com.posting.member.service;

import com.posting.exception.BusinessLogicException;
import com.posting.exception.ExceptionCode;
import com.posting.member.entity.Member;
import com.posting.content.entity.Content;
import com.posting.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    public Member createMember(Member member){

        // JPA 전
//        Member createdMember = member;
//
//        return createdMember;

        return memberRepository.save(member);
    }

    public Member updateMember(Member member){

        // JPA 전
//        Member updateMember = member;
//
//        return updateMember;

        Member findMember = findMember(member.getMemberId());

        // Optional 적용시킨 멤버만 update된다. 그래서 업데이트를 해야하는 멤버라면 Optional 적용시켜.
        // 멤버를 일일이 optional 해줘야하는데 member객체의 멤버를 일괄 업데이트하는 방법 없나?
        Optional.ofNullable(member.getName())
                .ifPresent(name -> findMember.setName(name));
        Optional.ofNullable(member.getEmail())
                .ifPresent(email -> findMember.setEmail(email));
        Optional.ofNullable(member.getPhone())
                .ifPresent(phone -> findMember.setPhone(phone));

        return memberRepository.save(findMember);
    }

    public Member findMember(long memberId){

        // JPA 전
//        Content content = new Content();
//        List<Content> contents = new ArrayList<>();
//        contents.add(content);
//        Member member = new Member(memberId, "name","eee@naa.com","010-8888-7777", contents);
//
//        return member;

        Optional<Member> optionalMember =
                memberRepository.findById(memberId);

        Member findMember =
                optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOUND));

        return findMember;

    }

    // 추후 페이징 예정
    public List<Member> findMembers(){

        // JPA 전
//        Content content = new Content();
//        List<Content> contents = new ArrayList<>();
//        contents.add(content);
//        Content content2 = new Content();
//        List<Content> content2s = new ArrayList<>();
//        content2s.add(content2);
//
//        List<Member> members = List.of(
//                new Member(1L, "name","eee@naa.com","010-8888-7777", contents),
//                new Member(3L, "name3","eee3@naa.com","010-8888-7777", content2s)
//        );
//
//        return members;

        return memberRepository.findAll();
    }

    public void deleteMember(long memberId){

        Member findMember = findMember(memberId);

        memberRepository.delete(findMember);

    }
}
