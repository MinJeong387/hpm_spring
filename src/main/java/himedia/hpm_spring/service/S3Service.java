package himedia.hpm_spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;

import java.net.URL;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class S3Service {
	
	// config에서 만든 S3Client Bean 주입 
    private final S3Client s3Client;

    private final String bucketName = System.getenv("AWS_BUCKET");
    private final String region = System.getenv("AWS_REGION");
    private final String accessKey = System.getenv("AWS_ACCESS_KEY");
    private final String secretKey = System.getenv("AWS_SECRET_KEY");

   
    // 리액트에서 전달받은 파일명(예: photo.jpg)을 바탕으로
    // 업로드용 presigned URL 발급
    // 사용자가 제한시간 동안 이미지를 업로드 가능하게 해주는 URL  
     
    public URL generatePresignedUrl(String fileName) {
        // 요청 객체 생성
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key("uploads/" + fileName)  // S3 버킷 내 저장 경로
                .build();

        // Presigner 객체 생성
        // Presigned URL을 만들어주는 도구 
        S3Presigner presigner = S3Presigner.builder()
                .region(Region.of(region))
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(accessKey, secretKey)
                        )
                )
                .build();

        // Presigned URL 생성
        PresignedPutObjectRequest presignedRequest = presigner.presignPutObject(req -> 
                req.signatureDuration(Duration.ofMinutes(10)) // 유효시간: 5분
                   .putObjectRequest(objectRequest)
        );

        return presignedRequest.url(); // 이 URL로 리액트에서 PUT 업로드 가능
    }
}
