package com.swing.doodle.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetRoundResultDto {
	String nickname;
	String profileImageUrl;
	String roundImageUrl;
}
