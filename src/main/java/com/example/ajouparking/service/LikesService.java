package com.example.ajouparking.service;

import com.example.ajouparking.entity.User;
import com.example.ajouparking.exceptionHandler.exceptions.CustomApiException;
import com.example.ajouparking.repository.LikesRepository;
import com.example.ajouparking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikesService {

    private final LikesRepository likesRepository;
    private final UserRepository userRepository;

    @Transactional
    public void putLike(long fromUserId, long toReviewId){
        try{
            User user = userRepository.findById(fromUserId)
                    .orElseThrow(() -> new IllegalArgumentException("없는유저"));

            user.plusExp(1);

            userRepository.save(user);
            likesRepository.like(fromUserId,toReviewId);
        }catch(Exception e){
            throw new CustomApiException("이미 좋아요 하였음");
        }
    }

    @Transactional
    public void deleteLike(long fromUserId, long toUserId){
        User user = userRepository.findById(fromUserId)
                .orElseThrow(() -> new IllegalArgumentException("없는유저"));

        user.minusExp(1);
        userRepository.save(user);
        likesRepository.unlike(fromUserId,toUserId);
    }



}