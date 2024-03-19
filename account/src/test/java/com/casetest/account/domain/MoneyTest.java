package com.casetest.account.domain;

import java.math.BigDecimal;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


public class MoneyTest {
    
 @Test
    public void testIsPositiveOrZero() {
        Money zeroMoney = Money.of(0L);
        Money positiveMoney = Money.of(100L);
        Money negativeMoney = Money.of(-100L);

        Assertions.assertThat(zeroMoney.isPositiveOrZero()).isTrue();
        Assertions.assertThat(positiveMoney.isPositiveOrZero()).isTrue();
        Assertions.assertThat(negativeMoney.isPositiveOrZero()).isFalse();
    }

    @Test
    public void testIsNegative() {
        Money zeroMoney = Money.of(0L);
        Money positiveMoney = Money.of(100L);
        Money negativeMoney = Money.of(-100L);

        Assertions.assertThat(zeroMoney.isNegative()).isFalse();
        Assertions.assertThat(positiveMoney.isNegative()).isFalse();
        Assertions.assertThat(negativeMoney.isNegative()).isTrue();
    }

    @Test
    public void testIsPositive() {
        Money zeroMoney = Money.of(0L);
        Money positiveMoney = Money.of(100L);
        Money negativeMoney = Money.of(-100L);

        Assertions.assertThat(zeroMoney.isPositive()).isFalse();
        Assertions.assertThat(positiveMoney.isPositive()).isTrue();
        Assertions.assertThat(negativeMoney.isPositive()).isFalse();
    }

    @Test
    public void testIsGreaterThanOrEqualTo() {
        Money money1 = Money.of(100L);
        Money money2 = Money.of(50L);
        Money money3 = Money.of(100L);

        Assertions.assertThat(money1.isGreaterThanOrEqualTo(money2)).isTrue();
        Assertions.assertThat(money1.isGreaterThanOrEqualTo(money3)).isTrue();
        Assertions.assertThat(money2.isGreaterThanOrEqualTo(money1)).isFalse();
    }

    @Test
    public void testIsGreaterThan() {
        Money money1 = Money.of(100L);
        Money money2 = Money.of(50L);
        Money money3 = Money.of(100L);

        Assertions.assertThat(money1.isGreaterThan(money2)).isTrue();
        Assertions.assertThat(money1.isGreaterThan(money3)).isFalse();
        Assertions.assertThat(money2.isGreaterThan(money1)).isFalse();
    }

    @Test
    public void testAdd() {
        Money money1 = Money.of(100L);
        Money money2 = Money.of(50L);

        Money result = Money.add(money1, money2);

        Assertions.assertThat(result.getValue()).isEqualTo(new BigDecimal(150));
    }

    @Test
    public void testMinus() {
        Money money1 = Money.of(100L);
        Money money2 = Money.of(50L);

        Money result = money1.minus(money2);

        Assertions.assertThat(result.getValue()).isEqualTo(new BigDecimal(50));
    }

    @Test
    public void testPlus() {
        Money money1 = Money.of(100L);
        Money money2 = Money.of(50L);

        Money result = money1.plus(money2);

        Assertions.assertThat(result.getValue()).isEqualTo(new BigDecimal(150));
    }

    @Test
    public void testSubtract() {
        Money money1 = Money.of(100L);
        Money money2 = Money.of(50L);

        Money result = Money.subtract(money1, money2);

        Assertions.assertThat(result.getValue()).isEqualTo(new BigDecimal(50));
    }

    @Test
    public void testNegate() {
        Money money = Money.of(100L);

        Money result = money.negate();

        Assertions.assertThat(result.getValue()).isEqualTo(new BigDecimal(-100));
    }

}
