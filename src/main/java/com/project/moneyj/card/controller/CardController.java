//package com.project.moneyj.card.controller;
//
//import com.project.moneyj.auth.dto.CustomOAuth2User;
//import com.project.moneyj.card.dto.CardAddRequestDTO;
//import com.project.moneyj.card.dto.CardLinkResponseDTO;
//import com.project.moneyj.card.service.CardService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/codef/accounts")
//public class CardController {
//
//    private final CardService cardService;
//
//
//    /**
//     * 카드 리스트 조회
//     */
//    @GetMapping("/card")
//    public ResponseEntity<List<CardLinkResponseDTO>> getCardList(
//            @AuthenticationPrincipal CustomOAuth2User customOAuth2User,
//            @RequestBody CardAddRequestDTO request){
//
//    }
//
//
//    /**
//     * 사용자가 선택한 카드를 저장.
//     */
//    @PostMapping("/card")
//    public ResponseEntity<CardLinkResponseDTO> linkAccount(
//            @AuthenticationPrincipal CustomOAuth2User customUser,
//            @RequestBody CardAddRequestDTO request
//    ) {
//        Long userId = customUser.getUserId();
//        // 서비스로부터 DTO를 직접 받음
//        CardLinkResponseDTO responseDto = cardService.linkUserAccount(userId, request);
//        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
//    }
//}
