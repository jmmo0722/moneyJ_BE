package com.project.moneyj.transaction.service;

import com.project.moneyj.transaction.domain.TransactionCategory;

public class StoreCategoryMapper {

    public static TransactionCategory mapToCategory(String storeType) {
        if (storeType == null) {
            return TransactionCategory.ETC;
        }

        return switch (storeType) {
            // ====== 식비 (FOOD) ======
            case "커피/음료전문점", "커피전문점", "제과점/아이스크림점", "패스트푸드점", "한식", "중식", "휴게음식점", "일반주점", "일반음식점 기타"
                -> TransactionCategory.FOOD;

            // ====== 교통/이동 (TRANSPORT) ======
            case "철도", "고속.시외버스", "RF대중교통", "택시"
                -> TransactionCategory.TRANSPORT;

            // ====== 쇼핑 (SHOPPING) ======
            case "완구점", "일반잡화판매점", "기성복점", "화장품점", "화원", "인쇄 및 광고", "전자상거래PG", "악세사리점"
                -> TransactionCategory.SHOPPING;

            // ====== 여가/문화 (LEISURE) ======
            case "영화관", "비디오방/게임방", "일반관광호텔"
                -> TransactionCategory.LEISURE;

            // ====== 생활/마트 (GROCERIES) ======
            case "편의점", "슈퍼마켓", "대형마트", "백화점"
                -> TransactionCategory.GROCERIES;

            // ====== 의료/건강 (HEALTH) ======
            case "종합병원", "약국", "미용원" // 건강/미용 쪽으로 묶음
                -> TransactionCategory.HEALTH;

            // ====== 기타 (ETC) ======
            case "자동판매기 운영업", "기타 용역서비스"
                -> TransactionCategory.ETC;

            default -> TransactionCategory.ETC;
        };
    }
}

