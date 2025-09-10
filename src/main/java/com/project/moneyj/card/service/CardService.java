//package com.project.moneyj.card.service;
//
//import com.project.moneyj.account.domain.Account;
//import com.project.moneyj.account.dto.AccountLinkRequestDTO;
//import com.project.moneyj.account.dto.AccountLinkResponseDTO;
//import com.project.moneyj.card.domain.Card;
//import com.project.moneyj.card.dto.CardAddRequestDTO;
//import com.project.moneyj.card.repository.CardRepository;
//import com.project.moneyj.codef.service.CodefBankService;
//import com.project.moneyj.trip.domain.TripPlan;
//import com.project.moneyj.trip.repository.TripPlanRepository;
//import com.project.moneyj.user.domain.User;
//import com.project.moneyj.user.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class CardService {
//
//    private final CardRepository cardRepository
//    private final UserRepository userRepository;
//    private final CodefBankService codefBankService;
//    private final TripPlanRepository tripPlanRepository;
//
//    @Transactional
//    public AccountLinkResponseDTO linkUserAccount(Long userId, CardAddRequestDTO request) {
//
//        // 1. DB에서 이 사용자의 연동 계좌가 이미 있는지 찾아봅니다.
//        Optional<Card> existingCardOpt = cardRepository.findByUser_UserId(userId);
//
//        String orgCode = existingCardOpt.isPresent() ? existingCardOpt.get().getOrganizationCode() : request.getOrganizationCode();
//        if (orgCode == null) {
//            throw new IllegalArgumentException("최초 연동 시에는 기관 코드를 반드시 포함해야 합니다.");
//        }
//
//        // 2. CODEF API를 호출하여 최신 카드 정보를 가져옵니다.
//        Map<String, Object> codefResponse = codefBankService.fetchBankAccounts(userId, orgCode);
//        Map<String, Object> data = (Map<String, Object>) codefResponse.get("data");
//        if (data == null || data.get("resDepositTrust") == null) {
//            throw new IllegalStateException("조회된 예금/신탁 계좌가 없습니다.");
//        }
//        List<Map<String, Object>> depositAccounts = (List<Map<String, Object>>) data.get("resDepositTrust");
//
//        Account finalAccount;
//
//        // 3. DB 상태와 요청 내용을 바탕으로 시나리오를 분기합니다.
//        if (existingCardOpt.isPresent()) {
//            Account existingAccount = existingCardOpt.get();
//            String newAccountNumber = request.getAccountNumber();
//
//            // 3-1. 시나리오 B: 계좌 '변경' 요청 (DB에 계좌가 있고, 요청으로 다른 계좌번호가 들어옴)
//            if (newAccountNumber != null && !newAccountNumber.equals(existingAccount.getAccountNumber())) {
//                finalAccount = createOrUpdateAccount(userId, request, depositAccounts, Optional.of(existingAccount));
//                log.info("계좌를 새로운 계좌(accountNumber:{})로 변경했습니다.", newAccountNumber);
//            } else {
//                // 3-2. 시나리오 A: 단순 잔액 '갱신' 요청 (DB에 계좌가 있고, 요청 계좌번호가 없거나 같음)
//                String currentAccountNumber = existingAccount.getAccountNumber();
//                Map<String, Object> currentAccountData = findAccountInList(depositAccounts, currentAccountNumber);
//
//                Integer latestBalance = Integer.parseInt(String.valueOf(currentAccountData.get("resAccountBalance")));
//                existingAccount.updateBalance(latestBalance); // 잔액만 업데이트
//                finalAccount = existingAccount;
//                log.info("기존 계좌(accountNumber:{})의 잔액을 업데이트했습니다.", currentAccountNumber);
//            }
//        } else {
//            // 3-3. 시나리오 C: '최초 연동' 요청 (DB에 계좌가 없음)
//            if (request.getAccountNumber() == null) {
//                throw new IllegalArgumentException("최초 연동 시에는 연동할 계좌번호를 반드시 포함해야 합니다.");
//            }
//            finalAccount = createOrUpdateAccount(userId, request, depositAccounts, Optional.empty());
//            log.info("새로운 계좌(accountNumber:{})를 연동했습니다.", request.getAccountNumber());
//        }
//
//        // 4. 최종 결과를 DTO로 만들어 반환합니다.
//        return AccountLinkResponseDTO.builder()
//                .accountName(finalAccount.getAccountName())
//                .accountNumberDisplay(maskAdvanced(finalAccount.getAccountNumber()))
//                .balance(finalAccount.getBalance())
//                .build();
//    }
//
//    // 계좌 생성 또는 전체 업데이트를 처리하는 헬퍼 메서드 (시나리오 B, C)
//    private Account createOrUpdateAccount(Long userId, AccountLinkRequestDTO request, List<Map<String, Object>> accountList, Optional<Account> accountOpt) {
//        String targetAccountNumber = request.getAccountNumber();
//        Map<String, Object> selectedAccountData = findAccountInList(accountList, targetAccountNumber);
//
//        User user = userRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("DB에서 사용자를 찾을 수 없습니다."));
//        TripPlan tripPlan = tripPlanRepository.findById(request.getTripPlanId()).orElseThrow(() -> new RuntimeException("DB에서 여행 계획을 찾을 수 없습니다."));
//
//        Integer balance = Integer.parseInt(String.valueOf(selectedAccountData.get("resAccountBalance")));
//
//        // 계좌가 이미 있으면 업데이트, 없으면 새로 생성
//        Account account = accountOpt.orElseGet(
//                () -> Account.builder()
//                        .user(user)
//                        .tripPlan(tripPlan)
//                        .accountNumber(targetAccountNumber)
//                        .accountNumberMasked(maskAdvanced(targetAccountNumber))
//                        .balance(balance)
//                        .organizationCode(request.getOrganizationCode())
//                        .accountName((String) selectedAccountData.get("resAccountName"))
//                        .build());
//
//        return accountRepository.save(account);
//    }
//
//    // 계좌 목록에서 특정 계좌번호를 찾는 헬퍼 메서드
//    private Map<String, Object> findAccountInList(List<Map<String, Object>> accountList, String accountNumber) {
//        return accountList.stream()
//                .filter(acc -> accountNumber.equals(String.valueOf(acc.get("resAccount"))))
//                .findFirst()
//                .orElseThrow(() -> new IllegalArgumentException("해당 계좌(" + accountNumber + ")를 목록에서 찾을 수 없습니다."));
//    }
//
//    // 예시: "1234-****-5678" 형태로 마스킹
//    public static String maskAdvanced(String accountNumber) {
//        if (accountNumber == null || accountNumber.length() < 8) {
//            return accountNumber; // 너무 짧으면 마스킹하지 않음
//        }
//        String firstPart = accountNumber.substring(0, 4);
//        String lastPart = accountNumber.substring(accountNumber.length() - 4);
//        return firstPart + "****" + lastPart;
//    }
//}
