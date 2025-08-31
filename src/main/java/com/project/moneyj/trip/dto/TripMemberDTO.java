package com.project.moneyj.trip.dto;

import com.project.moneyj.trip.domain.TripMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TripMemberDTO {

    private Long userId;
    private String nickname;
    private String email;
    private String image_url;

    public static TripMemberDTO fromEntity(TripMember tripMember){
        return TripMemberDTO.builder()
                .userId(tripMember.getUser().getUser_id())
                .nickname(tripMember.getUser().getNickname())
                .email(tripMember.getUser().getEmail())
                .image_url(tripMember.getUser().getImage_url()).build();
    }
}
