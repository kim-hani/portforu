package org.pinggu.portforu.domain.comment.repository;

import org.pinggu.portforu.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPortfolioId(Long portfolioId);

    @Modifying
    @Query("UPDATE Comment c SET c.content = COALESCE(:content, c.content) " +
            "WHERE c.id = :commentId AND c.deletedAt IS NULL")
    Integer updateComment(@Param("commentId") Long commentId,
                      @Param("content") String content);

}
