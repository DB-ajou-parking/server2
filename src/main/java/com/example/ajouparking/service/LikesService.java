package com.example.ajouparking.service;

import com.example.ajouparking.entity.Review;
import com.example.ajouparking.entity.User;
import com.example.ajouparking.exceptionHandler.exceptions.CustomApiException;
import com.example.ajouparking.repository.LikesRepository;
import com.example.ajouparking.repository.ReviewRepository;
import com.example.ajouparking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikesService {

    private final LikesRepository likesRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    @Transactional
    public void putLike(int fromUserId, Long toReviewId){
        try{
//            Review review = reviewRepository.findById(toReviewId)
//                    .orElseThrow(() -> new IllegalArgumentException("없는리뷰"));
//            User user = userRepository.findById(fromUserId)
//                    .orElseThrow(() -> new IllegalArgumentException("없는유저"));
//
//            System.out.println("=================================="+review.getUser()+"==================================");
//            user.setTierExp(user.getTierExp()+1);
//
//            userRepository.save(user);
            likesRepository.like(fromUserId,toReviewId);
        }catch(Exception e){
            throw new CustomApiException("좋아요 에러");
        }
    }

    @Transactional
    public void deleteLike(int fromUserId, Long toUserId){
        likesRepository.unlike(fromUserId,toUserId);
    }
}
