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
		Money[] balances = activities.stream()
				.reduce(
					new Money[]{Money.ZERO, Money.ZERO},
					(acc, activity) -> {
						if (activity.getTargetAccountId().equals(accountId)) {
							acc[0] = Money.add(acc[0], activity.getMoney());
						}
						if (activity.getSourceAccountId().equals(accountId)) {
							acc[1] = Money.add(acc[1], activity.getMoney());
						}
						return acc;
					},
					(acc1, acc2) -> new Money[]{
						Money.add(acc1[0], acc2[0]),
						Money.add(acc1[1], acc2[1])
					}
				);
		
		Money debit = balances[0];
		Money credit = balances[1];
		return Money.add(debit, credit.negate());
	}

	public void addActivity(Activity activity) {
		this.activities.add(activity);
	}

	public List<Activity> getActivities() {
		return this.activities;
	}

}
