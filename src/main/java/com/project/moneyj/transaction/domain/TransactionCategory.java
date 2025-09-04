package com.project.moneyj.transaction.domain;

public enum TransactionCategory {
    FOOD("식비"),              // 외식, 배달, 카페 포함
    TRANSPORT("교통/이동"),     // 버스, 지하철, 택시, 기차, 항공
    SHOPPING("쇼핑"),          // 온라인/오프라인 쇼핑, 패션, 잡화
    LEISURE("여가/문화"),      // 영화, 공연, 여행, 전시, 운동
    GROCERIES("생활/마트"),     // 편의점, 마트, 대형쇼핑몰
    HOUSING("주거/관리비"),     // 월세, 관리비, 공과금
    COMMUNICATION("통신"),     // 휴대폰, 인터넷
    HEALTH("의료/건강"),        // 병원, 약국, 헬스장
    EDUCATION("교육/학습"),     // 학원, 서적, 강의
    FINANCE("금융/보험"),       // 보험료, 금융수수료
    ETC("기타");               // 분류 불가능한 경우

    private final String description;

    TransactionCategory(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
