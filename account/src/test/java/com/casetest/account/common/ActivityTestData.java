package com.casetest.account.common;

import java.time.LocalDateTime;

import com.casetest.account.domain.Account.AccountId;
import com.casetest.account.domain.Activity.ActivityId;
import com.casetest.account.domain.Activity;
import com.casetest.account.domain.Money;

public class ActivityTestData {
    public static ActivityBuilder defaultActivity(){
		return new ActivityBuilder()
				.withSourceAccount(new AccountId(42, "Alice"))
				.withTargetAccount(new AccountId(41L, "Bob"))
				.withWhenHappened(LocalDateTime.now())
				.withMoney(Money.of(999L));
	}

	public static class ActivityBuilder {
		private ActivityId id;
		private AccountId sourceAccountId;
		private AccountId targetAccountId;
		private LocalDateTime whenHappened;
		private Money money;

		public ActivityBuilder withId(ActivityId id) {
			this.id = id;
			return this;
		}

		public ActivityBuilder withSourceAccount(AccountId accountId) {
			this.sourceAccountId = accountId;
			return this;
		}

		public ActivityBuilder withTargetAccount(AccountId accountId) {
			this.targetAccountId = accountId;
			return this;
		}

		public ActivityBuilder withWhenHappened(LocalDateTime whenHappened) {
			this.whenHappened = whenHappened;
			return this;
		}

		public ActivityBuilder withMoney(Money money) {
			this.money = money;
			return this;
		}

		public Activity build() {
			return new Activity(
					this.id,
					this.whenHappened,
					this.sourceAccountId,
					this.targetAccountId,
					this.money);
		}
	}
}
