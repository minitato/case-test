package com.casetest.account.common;

import com.casetest.account.domain.Account;
import com.casetest.account.domain.Account.AccountId;
import com.casetest.account.domain.Ledger;
import com.casetest.account.domain.Money;

public class AccountTestData {

	public static AccountBuilder defaultAccount() {
		return new AccountBuilder()
				.withAccountId(new AccountId(42L, "Alice"))
				.withBalance(Money.of(999L))
				.withLedger(new Ledger(
						ActivityTestData.defaultActivity().build(),
						ActivityTestData.defaultActivity().build()));
	}


	public static class AccountBuilder {

		private AccountId accountId;
		private boolean active;		
		private Money balance;
		private Ledger Ledger;

		public AccountBuilder withAccountId(AccountId accountId) {
			this.accountId = accountId;
			return this;
		}

		public AccountBuilder withActive(boolean active) {
			this.active = active;
			return this;
		}

		public AccountBuilder withBalance(Money balance) {
			this.balance = balance;
			return this;
		}

		public AccountBuilder withLedger(Ledger Ledger) {
			this.Ledger = Ledger;
			return this;
		}

		public Account build() {
			return Account.of(this.accountId, this.active, this.balance, this.Ledger);
		}

	}


}
