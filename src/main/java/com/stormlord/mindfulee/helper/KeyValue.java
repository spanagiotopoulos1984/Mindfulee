package com.stormlord.mindfulee.helper;

import java.util.Objects;

public class KeyValue {
    private Integer key;
    private Integer value;

    public KeyValue() {
    }

    public KeyValue(Integer key, Integer value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeyValue keyValue = (KeyValue) o;
        return Objects.equals(key, keyValue.key);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(key);
    }
}
