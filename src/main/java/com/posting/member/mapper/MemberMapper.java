package com.posting.member.mapper;

import com.posting.member.dto.MemberResponseDto;
import com.posting.member.dto.MemberPatchDto;
import com.posting.member.dto.MemberPostDto;
import com.posting.member.entity.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    Member memberPostDtoToMember(MemberPostDto memberPostDto);

    Member memberPatchDtoToMember(MemberPatchDto memberPatchDto);

    MemberResponseDto memberToMemberResponseDto(Member member);

}
