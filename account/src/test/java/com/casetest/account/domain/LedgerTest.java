package com.casetest.account.domain;

import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import java.time.LocalDateTime;

import com.casetest.account.domain.Account.AccountId;
import static com.casetest.account.common.ActivityTestData.defaultActivity;

public class LedgerTest {

	@Test
	void calculatesStartTimestamp() {
		Ledger ledger = new Ledger(
				defaultActivity().withWhenHappened(startDate()).build(),
				defaultActivity().withWhenHappened(inBetweenDate()).build(),
				defaultActivity().withWhenHappened(endDate()).build());

		Assertions.assertThat(ledger.getStartTimestamp()).isEqualTo(startDate());
	}

	@Test
	void calculatesEndTimestamp() {
		Ledger ledger = new Ledger(
				defaultActivity().withWhenHappened(startDate()).build(),
				defaultActivity().withWhenHappened(inBetweenDate()).build(),
				defaultActivity().withWhenHappened(endDate()).build());

		Assertions.assertThat(ledger.getEndTimestamp()).isEqualTo(endDate());
	}
    
	@Test
	void calculatesBalance() {

		AccountId account1 = new AccountId(1L, "Alice");
		AccountId account2 = new AccountId(2L, "Bob");

		Ledger book = new Ledger(
                defaultActivity()
						.withSourceAccount(account1)
						.withTargetAccount(account2)
						.withMoney(Money.of(999)).build(),
				defaultActivity()
						.withSourceAccount(account1)
						.withTargetAccount(account2)
						.withMoney(Money.of(1)).build(),
				defaultActivity()
						.withSourceAccount(account2)
						.withTargetAccount(account1)
						.withMoney(Money.of(500)).build());

		Assertions.assertThat(book.calculateBalance(account1).getValue()).isEqualTo(Money.of(-500).getValue());
		Assertions.assertThat(book.calculateBalance(account2).getValue()).isEqualTo(Money.of(500).getValue());
	}

	private LocalDateTime startDate() {
		return LocalDateTime.of(2019, 8, 3, 0, 0);
	}

	private LocalDateTime inBetweenDate() {
		return LocalDateTime.of(2019, 8, 4, 0, 0);
	}

	private LocalDateTime endDate() {
		return LocalDateTime.of(2019, 8, 5, 0, 0);
	}

}
