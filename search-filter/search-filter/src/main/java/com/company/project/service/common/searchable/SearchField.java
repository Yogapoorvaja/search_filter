package com.company.project.service.common.searchable;


import lombok.Data;

@Data
public class SearchField {
    private final String name;
    private final Object value;
    private final SearchType searchType;


    public String getValueString() {
        return (String) value;
    }
    public static SearchField like(String name, Object value) {
        return new SearchField(name, value, SearchType.LIKE);
    }

    public static SearchField eqBoolean(String name, Object value) {
        return new SearchField(name, value, SearchType.EQ_BOOLEAN);
    }

    public static SearchField eqString(String name, Object value) {
        return new SearchField(name, value, SearchType.EQ_STRING);
    }

    public static SearchField eqInt(String name, Object value) {
        return new SearchField(name, value, SearchType.EQ_INT);
    }

    public Integer getValueInt() {
        return (Integer) value;
    }
    public Boolean getValueBoolean() {
        return (Boolean) value;
    }

    public enum SearchType {
        LIKE,
        EQ_BOOLEAN,
        EQ_STRING,

        EQ_INT
    }
}
