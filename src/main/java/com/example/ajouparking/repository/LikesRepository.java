package com.example.ajouparking.repository;

import com.example.ajouparking.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikesRepository extends JpaRepository<Likes,Long> {

    @Modifying
    @Query(value = "insert into likes(from_user_id,to_review_id) values(:fromUserId,:toReviewId)",nativeQuery = true)
    void like(long fromUserId,long toReviewId);

    @Modifying
    @Query(value = "delete from likes where from_user_id = :fromUserId and to_review_id = :toReviewId",nativeQuery=true)
    void unlike(long fromUserId, long toReviewId);

    @Query(value = "SELECT COUNT(l) FROM Likes l WHERE l.review.id = :reviewId")
    int getLikesCountByReviewId(@Param("reviewId") long reviewId);
}
