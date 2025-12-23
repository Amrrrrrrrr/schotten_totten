package com.schottenTotten.model;

import java.util.List;
import java.util.Objects;

public class ResultatCombinaison implements Comparable<ResultatCombinaison> {

    private final TypeCombinaison type;
    private final int sum;

    private final List<ClanCarte> cards;

    public ResultatCombinaison(TypeCombinaison type, int sum, List<ClanCarte> cards) {
        this.type = type;
        this.sum = sum;
        this.cards = cards;
    }

    public TypeCombinaison getType() {
        return type;
    }

    public int getSum() {
        return sum;
    }

    public List<ClanCarte> getCards() {
        return cards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResultatCombinaison other = (ResultatCombinaison) o;

        return sum == other.sum && Objects.equals(type, other.type) && Objects.equals(cards, other.cards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, sum, cards);
    }

    
    @Override
    public int compareTo(ResultatCombinaison other) {
        int diffType = this.type.compareTo(other.type);
        if (diffType != 0) {
            return diffType;
        }

        if (this.sum < other.sum) {
            return -1;
        }
        if (this.sum > other.sum) {
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "ResultatCombinaison{type=" + type + ", sum=" + sum + ", cards=" + cards + '}';
    }
}
