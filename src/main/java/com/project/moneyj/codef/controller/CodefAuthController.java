package com.project.moneyj.codef.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.moneyj.codef.dto.AccountCreateRequest;
import com.project.moneyj.codef.dto.BankTxnListReq;
import com.project.moneyj.codef.service.CodefAccountService;
import com.project.moneyj.codef.service.CodefAuthService;
import com.project.moneyj.codef.service.CodefBankService;
import com.project.moneyj.codef.service.CodefCredentialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/codef")
public class CodefAuthController {

    // TODO userId -> @AuthenticationPrincipal 변경

    private final CodefAuthService codefAuthService;
    private final CodefAccountService accountService;
    private final CodefCredentialService credSvc;
    private final CodefBankService bankSvc;


    @GetMapping("/token")
    public ResponseEntity<?> getToken() {
        String token = codefAuthService.getValidAccessToken();
        String masked = token.length() > 12 ? token.substring(0, 12) + "..." : token;
        return ResponseEntity.ok().body("{\"accessToken\":\"" + masked + "\"}");
    }

    @PostMapping("/connected-id")
    public ResponseEntity<?> createConnectedId(
            @RequestParam Long userId,
            @RequestBody AccountCreateRequest request){

        // 요청 객체나 리스트가 비어있는지 확인하는 방어 코드
        if (request == null || request.getAccountList() == null || request.getAccountList().isEmpty()) {
            return ResponseEntity.badRequest().body("{\"error\":\"계정 정보가 비어있습니다.\"}");
        }

        // 리스트에서 첫 번째 AccountInput 객체를 꺼내서 서비스로 전달합니다.
        AccountCreateRequest.AccountInput input = request.getAccountList().get(0);

        String connectedId = accountService.createConnectedId(userId, input);
        return ResponseEntity.ok().body("{\"connectedId\":\"" + connectedId + "\"}");
    }

    // 계정 추가
    @PostMapping("/credentials")
    public ResponseEntity<?> addCredential(@RequestParam Long userId,
                                           @RequestBody AccountCreateRequest.AccountInput input) throws Exception {
        String raw = credSvc.addCredential(userId, input);
        return ResponseEntity.ok(raw);
    }

    // 계정 목록
    @GetMapping("/credentials")
    public ResponseEntity<?> listCredentials(@RequestParam Long userId) {
        return ResponseEntity.ok(credSvc.listCredentials(userId));
    }

    // 은행 - 계좌 목록
    @GetMapping("/bank/accounts")
    public ResponseEntity<?> bankAccounts(@RequestParam Long userId,
                                          @RequestParam String organization) {
        return ResponseEntity.ok(bankSvc.fetchBankAccounts(userId, organization));
    }

    // 은행 - 거래내역
    @PostMapping("/bank/transactions")
    public ResponseEntity<?> bankTransactions(@RequestParam Long userId,
                                              @RequestBody BankTxnListReq req) {
        return ResponseEntity.ok(bankSvc.fetchTransactions(userId, req));
    }
}
