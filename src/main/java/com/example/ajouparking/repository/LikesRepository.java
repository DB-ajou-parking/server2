package com.example.ajouparking.repository;

import com.example.ajouparking.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LikesRepository extends JpaRepository<Likes,Long> {

    @Modifying
    @Query(value = "insert into likes(from_user_id,to_review_id) values(:fromUserId,:toReviewId)",nativeQuery = true)
    void like(int fromUserId,int toReviewId);

    @Modifying
    @Query(value = "delete from likes where from_user_id = :fromUserId and to_review_id = :toReviewId",nativeQuery=true)
    void unlike(int fromUserId, int toReviewId);
}
