package com.casetest.account.domain;

import static com.casetest.account.common.AccountTestData.defaultAccount;
import static com.casetest.account.common.ActivityTestData.defaultActivity;

import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;

import com.casetest.account.domain.Account.AccountId;

public class AccountTest {
    
    	@Test
	void calculatesBalance() {
		AccountId accountId = new AccountId(1L, "Alice");
		Account account = defaultAccount()
				.withAccountId(accountId)
				.withBalance(Money.of(555L))
				.withLedger(new Ledger(
						defaultActivity()
								.withTargetAccount(accountId)
								.withMoney(Money.of(999L)).build(),
						defaultActivity()
								.withTargetAccount(accountId)
								.withMoney(Money.of(1L)).build()))
				.build();

		Money balance = account.calculateBalance();

		Assertions.assertThat(balance.getValue()).isEqualTo(Money.of(1555L).getValue());
	}

	@Test
	void creditalSucceeds() {
		AccountId accountId = new AccountId(1L, "Alice");
		Account account = defaultAccount()
				.withAccountId(accountId)
				.withBalance(Money.of(555L))
				.withLedger(new Ledger(
						defaultActivity()
								.withTargetAccount(accountId)
								.withMoney(Money.of(999L)).build(),
						defaultActivity()
								.withTargetAccount(accountId)
								.withMoney(Money.of(1L)).build()))
				.build();

		AccountId randomTargetAccount = new AccountId(99L, "Alice");
		boolean success = account.credit(Money.of(555L), randomTargetAccount);

		Assertions.assertThat(success).isTrue();
		Assertions.assertThat(account.getLedger().getActivities()).hasSize(3);
		Assertions.assertThat(account.calculateBalance().getValue()).isEqualTo(Money.of(1000L).getValue());
	}

	@Test
	void creditalFailure() {
		AccountId accountId = new AccountId(1L, "Alice");
		Account account = defaultAccount()
				.withAccountId(accountId)
				.withBalance(Money.of(555L))
				.withLedger(new Ledger(
						defaultActivity()
								.withTargetAccount(accountId)
								.withMoney(Money.of(999L)).build(),
						defaultActivity()
								.withTargetAccount(accountId)
								.withMoney(Money.of(1L)).build()))
				.build();

		boolean success = account.credit(Money.of(1556L), new AccountId(99L, "Alice"));

		Assertions.assertThat(success).isFalse();
		Assertions.assertThat(account.getLedger().getActivities()).hasSize(2);
		Assertions.assertThat(account.calculateBalance().getValue()).isEqualTo(Money.of(1555L).getValue());
	}

	@Test
	void debitSuccess() {
		AccountId accountId = new AccountId(1L, "Alice");
		Account account = defaultAccount()
				.withAccountId(accountId)
				.withBalance(Money.of(555L))
				.withLedger(new Ledger(
						defaultActivity()
								.withTargetAccount(accountId)
								.withMoney(Money.of(999L)).build(),
						defaultActivity()
								.withTargetAccount(accountId)
								.withMoney(Money.of(1L)).build()))
				.build();

		boolean success = account.debit(Money.of(445L), new AccountId(99L, "Alice"));

		Assertions.assertThat(success).isTrue();
		Assertions.assertThat(account.getLedger().getActivities()).hasSize(3);
		Assertions.assertThat(account.calculateBalance().getValue()).isEqualTo(Money.of(2000L).getValue());
	}

}
