package com.casetest.account.application.service;

import java.time.LocalDateTime;

import com.casetest.account.application.port.in.MakeOrder;
import com.casetest.account.application.port.in.OrderTransferUseCase;
import com.casetest.account.application.port.out.FindCustomerPort;
import com.casetest.account.application.port.out.LoadAtivitiesPort;
import com.casetest.account.application.port.out.SendNotificationPort;
import com.casetest.account.application.port.out.UpdateAccountStatePort;
import com.casetest.account.domain.Account;
import com.casetest.account.domain.Activity;
import com.casetest.account.domain.Money;
import com.casetest.account.domain.Account.AccountId;
import com.casetest.account.domain.exception.AccountNotActiveException;
import com.casetest.account.domain.exception.ExeccededLimitPerDayException;
import com.casetest.account.domain.exception.ThresholdExceededException;

public class TransactionService implements OrderTransferUseCase {

    private final FindCustomerPort findCustomerPort;
    private final LoadAtivitiesPort loadAtivitiesPort;
    private final SendNotificationPort sendNotificationPort;
    private final UpdateAccountStatePort updateAccountStatePort;
    private final MoneyTransferLimit moneyTransferLimit;
    private final PerDayLimit limitPerDay;

    public TransactionService(
            FindCustomerPort findCustomerPort,
            LoadAtivitiesPort loadAtivitiesPort,
            SendNotificationPort sendNotificationPort,
            UpdateAccountStatePort updateAccountStatePort,
            MoneyTransferLimit moneyTransferLimit,
            PerDayLimit limitPerDay) {
      this.findCustomerPort = findCustomerPort;
      this.loadAtivitiesPort = loadAtivitiesPort;
      this.sendNotificationPort = sendNotificationPort;
      this.updateAccountStatePort = updateAccountStatePort;
      this.moneyTransferLimit = moneyTransferLimit;
      this.limitPerDay = limitPerDay;
    }

    @Override
    public boolean transfer(MakeOrder makeOrder) {

      checkLimit(makeOrder);
      
      LocalDateTime baselineTime = LocalDateTime.now().minusMinutes(20);
     
      var sourceAccount = findCustomerPort.searchCustomer(makeOrder.sourceAccount());
      checkIfAccountIsActive(sourceAccount);
      var targetAccount = findCustomerPort.searchCustomer(makeOrder.targetAccount());
      checkIfAccountIsActive(targetAccount);

      sourceAccount = loadAtivitiesPort.loadActivities(sourceAccount, baselineTime);
      targetAccount = loadAtivitiesPort.loadActivities(targetAccount, baselineTime);

      
      AccountId sourceAccountId = sourceAccount.getId()
      .orElseThrow(() -> new IllegalStateException("expected source account ID not to be empty"));
      AccountId targetAccountId = targetAccount.getId()
      .orElseThrow(() -> new IllegalStateException("expected target account ID not to be empty"));
      
      if(sourceAccount.credit(makeOrder.money(), targetAccountId)) {
        if(targetAccount.debit(makeOrder.money(), sourceAccountId)) {
          checkIfExeccededLimitPerDay(sourceAccount, makeOrder.money());
          if(sendNotificationPort.notifyAboutTransfer(makeOrder)) {
            updateAccountStatePort.updateActivities(sourceAccount);
            updateAccountStatePort.updateActivities(targetAccount);
            return true;
          }
        }
      }

      return false;
    }

    private void checkLimit(MakeOrder order) {
      if(order.money().isGreaterThan(moneyTransferLimit.getMaximumTransferLimit())){
        throw new ThresholdExceededException(moneyTransferLimit.getMaximumTransferLimit(), order.money());
      }
	  }

    private void checkIfAccountIsActive(Account account) {
      if(!account.isActive()) {
        throw new AccountNotActiveException(account.getId().get().getName());
      }
    }

    private void checkIfExeccededLimitPerDay(Account account, Money money) {
      LocalDateTime limitPerDay = LocalDateTime.now().minusHours(24);
      Money transferedToday = account.getLedger().getActivities().stream()
      .filter(a -> a.getWhenHappened().isAfter(limitPerDay))
      .map(Activity::getMoney)
      .reduce(Money.ZERO, Money::add);
      if(transferedToday.isGreaterThanOrEqualTo(this.limitPerDay.getLimitPerDay()) || 
          account.getLedger().getStartTimestamp().isBefore(limitPerDay)) {
        throw new ExeccededLimitPerDayException("Limit exceeded");
      }
    }


}
