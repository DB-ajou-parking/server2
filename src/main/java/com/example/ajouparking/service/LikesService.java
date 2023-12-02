package com.example.ajouparking.service;

import com.example.ajouparking.exceptionHandler.exceptions.CustomApiException;
import com.example.ajouparking.repository.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikesService {

    private final LikesRepository likesRepository;

    @Transactional
    public void putLike(int fromUserId, int toUserId){
        try{
            likesRepository.like(fromUserId,toUserId);
        }catch(Exception e){
            throw new CustomApiException("이미 좋아요를 눌렀음");
        }
    }

    @Transactional
    public void deleteLike(int fromUserId, int toUserId){
        likesRepository.unlike(fromUserId,toUserId);
    }
}
