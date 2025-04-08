package org.pinggu.portforu.domain.comment.repository;

import org.pinggu.portforu.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPortfolioId(Long portfolioId);

    Optional<Comment> findByIdAndDeletedAtIsNull(Long id);

    List<Comment> findAllByPortfolioIdAndDeletedAtIsNull(Long portfolioId);
}
