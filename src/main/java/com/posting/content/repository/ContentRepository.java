package com.posting.content.repository;

import com.posting.content.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ContentRepository extends JpaRepository<Content, Long> {

}
