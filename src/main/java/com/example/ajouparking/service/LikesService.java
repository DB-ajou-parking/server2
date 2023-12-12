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

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikesService {

    private final LikesRepository likesRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    @Transactional(readOnly = true)
    public int getLikesCountByReviewId(long reviewId) {
        return likesRepository.getLikesCountByReviewId(reviewId);
    }

    @Transactional
    public void putLike(long fromUserId, long toReviewId){
        try{
            User fromUser = userRepository.findById(fromUserId)
                    .orElseThrow(() -> new IllegalArgumentException("없는유저"));

            Optional<Review> review = reviewRepository.findById(toReviewId);

            if (review.isPresent()) {
                User toUser = review.get().getUser();

                // Increase tierExp for the user associated with the review
                toUser.plusExp(1);
                toUser.updateTier();
                userRepository.save(toUser);

                // Save the like
                likesRepository.like(fromUserId, toReviewId);
            } else {
                throw new CustomApiException("리뷰를 찾을 수 없음");
            }


        }catch(Exception e){
            throw new CustomApiException("이미 좋아요 하였음");
        }
    }

    @Transactional
    public void deleteLike(long fromUserId, long toUserId){
        User user = userRepository.findById(fromUserId)
                .orElseThrow(() -> new IllegalArgumentException("없는유저"));

        //user.minusExp(1);
        user.updateTier();
        userRepository.save(user);
        likesRepository.unlike(fromUserId,toUserId);
    }



}