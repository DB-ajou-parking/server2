package com.example.ajouparking.repository;

import com.example.ajouparking.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LikesRepository extends JpaRepository<Likes,Long> {

    @Modifying
    @Query(value = "insert into likes(from_user_id,to_user_id) values(:fromUserId,:toUserId)",nativeQuery = true)
    void like(int fromUserId,int toUserId);

    @Modifying
    @Query(value = "delete from likes where from_user_id = :fromUserId and to_user_id = :toUserId",nativeQuery=true)
    void unlike(int fromUserId, int toUserId);
}
