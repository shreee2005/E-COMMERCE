package com.E_Commerce.E_Commerce.Controller;
import com.E_Commerce.E_Commerce.Service.ReviewService;
import com.E_Commerce.E_Commerce.dto.ReviewRequestDto;
import com.E_Commerce.E_Commerce.dto.ReviewResponseDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products/{productId}/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    // GET /api/products/{productId}/reviews
    @GetMapping
    public ResponseEntity<List<ReviewResponseDto>> getReviewsForProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(reviewService.getReviewsForProduct(productId));
    }

    // POST /api/products/{productId}/reviews
    @PostMapping
    public ResponseEntity<ReviewResponseDto> addReview(
            @PathVariable Long productId,
            @RequestBody ReviewRequestDto reviewRequest,
            @AuthenticationPrincipal UserDetails userDetails) {

        String username = userDetails.getUsername();
        ReviewResponseDto newReview = reviewService.addReview(productId, username, reviewRequest);
        return new ResponseEntity<>(newReview, HttpStatus.CREATED);
    }
}