package com.casetest.account.adapter.port.out.persistence;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.casetest.account.adapter.port.out.persistence.entity.ActivityEntity;
import com.casetest.account.adapter.port.out.persistence.jpa.ActivityRepository;
import com.casetest.account.application.port.out.LoadAtivitiesPort;
import com.casetest.account.application.port.out.UpdateAccountStatePort;
import com.casetest.account.domain.Account;
import com.casetest.account.domain.Account.AccountId;
import com.casetest.account.domain.Activity;
import com.casetest.account.domain.Activity.ActivityId;
import com.casetest.account.domain.Money;

public class AccountPersistenceAdapter implements LoadAtivitiesPort, UpdateAccountStatePort {

    private final ActivityRepository activityRepository;

    public AccountPersistenceAdapter(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public Account loadActivities(Account account, LocalDateTime baselineTime) {

        List<ActivityEntity> activities =
        activityRepository.findByOwnerSince(
            account.getId().get().getId(),
            baselineTime);

        account.ofActivities(new ActivityMappered().mapToDomainEntity(activities));
        
        Long creditalBalance = activityRepository
            .getCreditalBalanceUntil(
                account.getId().get().getId(),
                baselineTime)
            .orElse(0L);

        Long debitBalance = activityRepository
                .getDebitBalanceUntil(
                        account.getId().get().getId(),
                        baselineTime)
                .orElse(0L);

        account.ofBalance(Money.subtract(
            Money.of(debitBalance),
            Money.of(creditalBalance)));

        
        System.out.println("credital_balance: " + creditalBalance);
        System.out.println("debit_balance: " + debitBalance);
        System.out.println("balance: " + account.calculateBalance().getValue());
        
        return account;
    }

    @Override
    public void updateActivities(Account account) {
        account.getLedger().getActivities().stream()
            .filter(activity -> activity.getId() == null)
            .forEach(activity -> {
                activityRepository.save(new ActivityPersisdente().mapToJpaEntity(account.getId().get(), activity));
            });
    }
    
}

class ActivityPersisdente {
    ActivityEntity mapToJpaEntity(AccountId accountId, Activity activity) {
        return new ActivityEntity(
            activity.getWhenHappened(),
            accountId.getId(),
            activity.getSourceAccountId().getId(),
            activity.getTargetAccountId().getId(),
            activity.getMoney().getValue().longValue());
    }

}

class ActivityMappered {
    List<Activity> mapToDomainEntity(List<ActivityEntity> activities) {
        return activities.stream().map(entity -> {
            return new Activity(
            new ActivityId(entity.getId()),
            entity.getWhenHappened(),
            new AccountId(entity.getSourceAccountId(), null),
            new AccountId(entity.getTargetAccountId(), null),
            Money.of(entity.getValue()));
        }).collect(Collectors.toList());
    }
}