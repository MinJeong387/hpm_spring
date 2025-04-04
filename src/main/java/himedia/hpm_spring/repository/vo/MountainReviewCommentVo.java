package himedia.hpm_spring.repository.vo;

import java.time.LocalDateTime;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MountainReviewCommentVo {

	private Long id;						// 	댓글 고유번호
	private String nickname;				//	users 테이블의 nickname과 매핑
	private String content;					// 	댓글 내용
	
	@JsonProperty("update_date")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updateDate;		//	댓글 게시 시간
	
	@JsonProperty("reviews_id")
	private Long reviewsId;				// 	등산 후기 게시글 작성자 아이디
	
	@JsonProperty("users_id")
	private Long usersId;				//	댓글 작성자 아이디
	
	@JsonProperty("parent_id")
	private Long parentId; 				// 부모 댓글 ID (대댓글인 경우)
}
