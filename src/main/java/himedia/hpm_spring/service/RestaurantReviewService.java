package himedia.hpm_spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import himedia.hpm_spring.mappers.RestaurantReviewMapper;
import himedia.hpm_spring.repository.vo.RestaurantReviewVo;

@Service
public class RestaurantReviewService {

    @Autowired
    private RestaurantReviewMapper restaurantReviewMapper;

    // 모든 맛집 리뷰 게시글 조회
    public List<RestaurantReviewVo> retrieveAllReviews() {
        return restaurantReviewMapper.retrieveAllReviews();
    }

    // 특정 맛집 리뷰 게시글 조회
    public RestaurantReviewVo retrieveReviewById(Long id) {
        return restaurantReviewMapper.retrieveReviewById(id);
    }

    // 사용자의 맛집 리뷰 게시글 조회
    public List<RestaurantReviewVo> retrieveMyReviews(Long id) {
        return restaurantReviewMapper.retrieveMyReviews(id);
    }

    // 맛집 리뷰 게시글 생성
    public RestaurantReviewVo createReview(RestaurantReviewVo review) {
        // 맛집 리뷰 게시글 생성
    	restaurantReviewMapper.createReview(review);

        // 생성된 맛집 리뷰 게시글의 ID를 이용해 리뷰 게시글을 다시 조회하여 반환
        Long id = review.getId();
        return restaurantReviewMapper.retrieveReviewById(id);
    }

    // 맛집 리뷰 게시글 일부 수정 (PATCH)
    public RestaurantReviewVo updateReview(RestaurantReviewVo review) {
        // 맛집 리뷰 업데이트 (일부 필드 수정)
        int updatedRows = restaurantReviewMapper.updateReview(review);
        
        if (updatedRows > 0) {
            return restaurantReviewMapper.retrieveReviewById(review.getId());
        } else {
            throw new RuntimeException("Failed to update restaurantReview");
        }
    }

    // 맛집 리뷰 게시글 전체 수정 (PUT)
//    public RestaurantReviewVo replaceReview(RestaurantReviewVo review) {
//        // 리뷰 게시글 전체 수정
//        int updatedRows = restaurantReviewMapper.replaceReview(review);
//        
//        if (updatedRows > 0) {
//            return restaurantReviewMapper.retrieveReviewById(review.getId());
//        } else {
//            throw new RuntimeException("Failed to replace restaurantReview");
//        }
//    }

    // 맛집 리뷰 게시글 삭제
    public void deleteReview(Long id) {
        int deletedRows = restaurantReviewMapper.deleteReview(id);
        if (deletedRows == 0) {
            throw new RuntimeException("Failed to delete restaurantReview with ID: " + id);
        }
    }
}
