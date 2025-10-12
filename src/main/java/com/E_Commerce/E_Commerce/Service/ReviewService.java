package com.E_Commerce.E_Commerce.Service;

import com.E_Commerce.E_Commerce.dto.ReviewRequestDto;
import com.E_Commerce.E_Commerce.dto.ReviewResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ReviewService {
    List<ReviewResponseDto> getReviewsForProduct(Long productId);
    ReviewResponseDto addReview(Long productId , String username , ReviewRequestDto reviewRequest);
}
