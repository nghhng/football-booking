package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
@Data
@Builder
@AllArgsConstructor
public class CreateCommentRequest {

    private String targetId;
    private String targetType;
    private Integer rating;
    private String body;
}
