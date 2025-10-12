package com.E_Commerce.E_Commerce.Service.ServiceImpl;

import com.E_Commerce.E_Commerce.Exceptions.ProductNotFoundException;
import com.E_Commerce.E_Commerce.Model.Product;
import com.E_Commerce.E_Commerce.Model.Review;
import com.E_Commerce.E_Commerce.Model.User;
import com.E_Commerce.E_Commerce.Repository.ProductRepository;
import com.E_Commerce.E_Commerce.Repository.ReviewRepository;
import com.E_Commerce.E_Commerce.Repository.UserRepository;
import com.E_Commerce.E_Commerce.Service.ReviewService;
import com.E_Commerce.E_Commerce.dto.ReviewRequestDto;
import com.E_Commerce.E_Commerce.dto.ReviewResponseDto;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class ReviewServiceImpl implements ReviewService {

    private ReviewRepository reviewRepository;
    private ProductRepository productRepository;
    private UserRepository userRepository;


    @Override
    public List<ReviewResponseDto> getReviewsForProduct(Long productId) {
        if(!productRepository.existsById(productId)){
            throw new ProductNotFoundException("Product Not Found ");
        }
        List<Review> reviews = reviewRepository.findByProductId(productId);
        return reviews.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private ReviewResponseDto convertToDto(Review review) {
        ReviewResponseDto reviewResponseDto = new ReviewResponseDto();
        reviewResponseDto.setComment(review.getComment());
        reviewResponseDto.setRating(review.getRating());
        reviewResponseDto.setUsername(review.getUser().getUsername());
        reviewResponseDto.setCreatedAt(review.getCreateAt());
        reviewResponseDto.setId(review.getId());
        reviewResponseDto.setProductId(review.getProduct().getId());
        return reviewResponseDto;
    }

    @Override
    public ReviewResponseDto addReview(Long productId, String username, ReviewRequestDto reviewRequest) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product Not Found"));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        Review newReview = new Review();
        newReview.setProduct(product);
        newReview.setUser(user);
        newReview.setRating(reviewRequest.getRating());
        newReview.setComment(reviewRequest.getComment());

        Review savedReview = reviewRepository.save(newReview);
        return convertToDto(savedReview);
    }
}
