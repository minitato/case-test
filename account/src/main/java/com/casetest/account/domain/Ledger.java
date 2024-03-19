package com.casetest.account.domain;

import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Ledger {
    
    private List<Activity> activities;

	public LocalDateTime getStartTimestamp() {
		return activities.stream()
				.min(Comparator.comparing(Activity::getWhenHappened))
				.orElseThrow(IllegalStateException::new)
				.getWhenHappened();
	}

	public LocalDateTime getEndTimestamp() {
		return activities.stream()
				.max(Comparator.comparing(Activity::getWhenHappened))
				.orElseThrow(IllegalStateException::new)
				.getWhenHappened();
	}

    public Ledger(Activity... activities) {
        this.activities = new ArrayList<>(Arrays.asList(activities));
    }

	public Ledger(List<Activity> activities) {
        this.activities = activities;
    }
    
	public Money calculateBalance(Account.AccountId accountId) {
		Money debit = activities.stream()
				.filter(a -> a.getTargetAccountId().equals(accountId))
				.map(Activity::getMoney)
				.reduce(Money.ZERO, Money::add);

		Money credit = activities.stream()
				.filter(a -> a.getSourceAccountId().equals(accountId))
				.map(Activity::getMoney)
				.reduce(Money.ZERO, Money::add);

		return Money.add(debit, credit.negate());
	}

	public void addActivity(Activity activity) {
		this.activities.add(activity);
	}

	public List<Activity> getActivities() {
		return this.activities;
	}

}
