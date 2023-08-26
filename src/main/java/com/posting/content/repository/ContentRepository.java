package com.posting.content.repository;

import com.posting.content.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Long> {

    // 실험1 제목으로 찾기
    List<Content> findAllByTitle(String title);

    // 실험2 첨부로 like
//    @Query(value = "select n from NOTICE n where n.attachFile like :attachFile%")
//    @Query(value = "select n from NOTICE n where n.attachFile like %:attachFile")
//    @Query(value = "select n from NOTICE n where n.attachFile like %:attachFile%")
    List<Content> findAllByAttachFileLike(@Param("attachFile") String attachFile);

    //실험3 조건 2개로 찾기
    List<Content> findAllByTitleAndAttachFile(String title, String attachFile);

    //실험4 특정값 하나 반환
//    String findAttachFileByNoticeId(long noticeId); Content객체로 반환해야함.
    Content findAttachFileByNoticeId(long noticeId);

    //실험4-2 쿼리로 특정값 하나 반환
//    @Query(value = "select n.attachFile from NOTICE n where n.noticeId = :noticeId")
// 아무래도 반환형태가 Content 라서 n.attachFile 같이 특정값으로만으로는 select할 수 없다.
    @Query(value = "select n from NOTICE n where n.noticeId = :noticeId")
    Content findAttachFileByNoticeId2(@Param("noticeId")long noticeId);
}
