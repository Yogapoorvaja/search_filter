package com.company.project.service.common.searchable;

import lombok.Data;

import java.util.List;

@Data
public class SearchCriteria {
    private final List<SearchField> searchFields;
    private final int page;
    private final int size;
    private final String orderBy;
    private final Direction sortDirection;

    public enum Direction {
        ASCENDING("asc"),
        DESCENDING("desc");

        private final String value;

        Direction(String value) {
            this.value = value;
        }

        public static Direction findByValue(String value) {
            if (value.equals(DESCENDING.value)) {
                return DESCENDING;
            } else {
                return ASCENDING;
            }
        }
    }
}
