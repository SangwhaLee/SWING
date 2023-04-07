package com.swing.doodle.model.dto;

import com.swing.doodle.model.entity.History;
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
	String content;
	String meaningKr;
	
	public static GetRoundResultDto toDto (History history) {
		return new GetRoundResultDto(
				history.getUser().getNickname(),
				history.getUser().getProfileImageUrl(),
				history.getGameImageUrl(),
				history.getRound().getWord().getContent(),
				history.getRound().getWord().getMeaningKr()
		);
	}
}
