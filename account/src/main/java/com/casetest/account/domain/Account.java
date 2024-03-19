package com.casetest.account.domain;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

public class Account {
    
    private final AccountId id;
    private final boolean active;
    private Money balance;
    private Ledger ledger;

    private Account(AccountId id, boolean active, Money balance, Ledger ledger) {
        this.id = id;
        this.active = active;
        this.balance = balance;
        this.ledger = ledger;
    }

    public static Account of(AccountId id, boolean active, Money balance, Ledger ledger) {
        return new Account(id, active, balance, ledger);
    }

    public void ofActivities(List<Activity> activities) {
        this.ledger = new Ledger(activities);
    }

    public void ofBalance(Money balance) {
        this.balance = balance;
    }

    public Optional<AccountId> getId(){
        return Optional.ofNullable(this.id);
    }

    public boolean isActive() {
        return this.active;
    }

	public Money calculateBalance() {
		return Money.add(
				this.balance,
				this.ledger.calculateBalance(this.id));
	}

	public boolean credit(Money money, AccountId targetAccountId) {

		if (!mayCredit(money)) {
			return false;
		}

		Activity credit = new Activity(
                LocalDateTime.now(),
				this.id,
				targetAccountId,
				money);
		this.ledger.addActivity(credit);
		return true;
	}

	private boolean mayCredit(Money money) {
		return Money.add(
				this.calculateBalance(),
				money.negate())
				.isPositiveOrZero();
	}

	public boolean debit(Money money, AccountId sourceAccountId) {
		Activity debit = new Activity(
                LocalDateTime.now(),
				sourceAccountId,
				this.id,
				money);
		this.ledger.addActivity(debit);
		return true;
	}

    public Ledger getLedger() {
        return this.ledger;
    }

    public static class AccountId {
        
        private final long id;
        private final String name;

        public AccountId(String name) {
            this.id = 0;
            this.name = name;
        }

        public AccountId(long id, String name) {
            this.id = id;
            this.name = name;
        }

        public long getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
