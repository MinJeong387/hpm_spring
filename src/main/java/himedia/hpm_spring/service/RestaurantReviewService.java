package himedia.hpm_spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import himedia.hpm_spring.mappers.RestaurantReviewMapper;
import himedia.hpm_spring.repository.vo.CommunityVo;
import himedia.hpm_spring.repository.vo.RestaurantReviewVo;

@Service
public class RestaurantReviewService {

    @Autowired
    private RestaurantReviewMapper rReviewMapper;
    
    @Autowired
	private RestaurantPhotoService restaurantPhotoService;
    
    // 모든 맛집 리뷰 게시글 조회
    public List<RestaurantReviewVo> retrieveAllReviews() {
        return rReviewMapper.retrieveAllReviews();
    }

    // 특정 맛집 리뷰 게시글 조회
    public RestaurantReviewVo retrieveReviewById(Long id) {
        return rReviewMapper.retrieveReviewById(id);
    }

    // 사용자의 맛집 리뷰 게시글 조회
    public List<RestaurantReviewVo> retrieveMyReviews(Long id) {
        return rReviewMapper.retrieveMyReviews(id);
    }
    
    // 키워드 기반 게시글 조회
 	public List<RestaurantReviewVo> retrieveReviewsByKeyword(String keyword) {
 		String pattern = "(^|[^가-힣a-zA-Z0-9])" + keyword + "([^가-힣a-zA-Z0-9]|$)";
 		return rReviewMapper.retrieveReviewsByKeyword(pattern);
 	}

    // 맛집 리뷰 게시글 생성
    public RestaurantReviewVo createReview(RestaurantReviewVo review) {
        // 맛집 리뷰 게시글 생성
    	rReviewMapper.createReview(review);

        // 생성된 맛집 리뷰 게시글의 ID를 이용해 리뷰 게시글을 다시 조회하여 반환
        Long id = review.getId();
        return rReviewMapper.retrieveReviewById(id);
    }

    // 맛집 리뷰 게시글 일부 수정 (PATCH)
    public RestaurantReviewVo updateReview(RestaurantReviewVo review) {
        // 맛집 리뷰 업데이트 (일부 필드 수정)
        int updatedRows = rReviewMapper.updateReview(review);
        
        if (updatedRows > 0) {
            return rReviewMapper.retrieveReviewById(review.getId());
        } else {
            throw new RuntimeException("Failed to update restaurantReview");
        }
    }

    // 맛집 리뷰 게시글 삭제
    public void deleteReview(Long id, Long usersId) {
    	
    	// 게시글 이미지 먼저 삭제
    	restaurantPhotoService.deletePhotoByRestaurantId(id.intValue());
        
    	int deletedRows = rReviewMapper.deleteReview(id, usersId);
        if (deletedRows == 0) {
            throw new RuntimeException("Failed to delete restaurantReview with ID: " + id + " for user ID: " + usersId);
        }
    }
    
	// 조회수 증가 메서드
	public void incrementViews(Long id) {
		rReviewMapper.incrementViews(id); 
	}
}
