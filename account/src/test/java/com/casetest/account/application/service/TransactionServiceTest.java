package com.casetest.account.application.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.assertj.core.api.Assertions;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import com.casetest.account.application.port.in.MakeOrder;
import com.casetest.account.application.port.out.FindCustomerPort;
import com.casetest.account.application.port.out.LoadAtivitiesPort;
import com.casetest.account.application.port.out.SendNotificationPort;
import com.casetest.account.application.port.out.UpdateAccountStatePort;
import com.casetest.account.domain.Account;
import com.casetest.account.domain.Account.AccountId;
import com.casetest.account.domain.Money;

public class TransactionServiceTest {

    private final FindCustomerPort findCustomerPort =
		    Mockito.mock(FindCustomerPort.class);

	private final LoadAtivitiesPort loadAtivitiesPort =	
			Mockito.mock(LoadAtivitiesPort.class);

    private final SendNotificationPort sendNotificationPort =
            Mockito.mock(SendNotificationPort.class);

	private final UpdateAccountStatePort updateAccountStatePort =
			Mockito.mock(UpdateAccountStatePort.class);

	private final TransactionService transactionService =
			new TransactionService(
                findCustomerPort,
				loadAtivitiesPort,
                sendNotificationPort, 
                updateAccountStatePort, 
                moneyTransferLimit(),
				perDayLimit()
            );

    // @Test
    void givenCreditalFails_thenOnlySourceAccount() {

        AccountId sourceAccountId = new AccountId(42l, "Alice");
        Account sourceAccount = givenAnAccountWithId(sourceAccountId);

        AccountId targetAccountId = new AccountId(44l, "Bob");
        Account targetAccount = givenAnAccountWithId(targetAccountId);

		givenCreditalWillFail(sourceAccount);
        givenDebitWillSucceed(targetAccount);

        MakeOrder order = new MakeOrder(
                sourceAccountId,
                Money.of(300L),
                targetAccountId);


        boolean success = transactionService.transfer(order);

        Assertions.assertThat(success).isTrue();
    }

	void transactionSucceeds() {

		Account sourceAccount = givenSourceAccount();
		Account targetAccount = givenTargetAccount();

		givenCreditalWillSucceed(sourceAccount);
		givenDebitWillSucceed(targetAccount);

		Money money = Money.of(500L);

		MakeOrder makeOrder = new MakeOrder(
				sourceAccount.getId().get(),
				money,
				targetAccount.getId().get());

		boolean success = transactionService.transfer(makeOrder);

		AccountId sourceAccountId = sourceAccount.getId().get();
		AccountId targetAccountId = targetAccount.getId().get();

        Assertions.assertThat(success).isTrue();

		BDDMockito.then(sourceAccount).should().credit(eq(money), eq(targetAccountId));
		BDDMockito.then(targetAccount).should().debit(eq(money), eq(sourceAccountId));

		thenAccountsHaveBeenUpdated(sourceAccountId, targetAccountId);
	}

    //@Test
	private void thenAccountsHaveBeenUpdated(AccountId... accountIds){
		ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);
        BDDMockito.then(updateAccountStatePort)
                .should(times(accountIds.length))
                .updateActivities(accountCaptor.capture());
		List<AccountId> updatedAccountIds = accountCaptor.getAllValues()
				.stream()
                .map(Account::getId)
				.map(Optional::get)
				.collect(Collectors.toList());

		for(AccountId accountId : accountIds){
			Assertions.assertThat(updatedAccountIds).contains(accountId);
		}
	}

	private void givenDebitWillSucceed(Account account) {
		BDDMockito.given(account.debit(any(Money.class), any(AccountId.class)))
				.willReturn(true);
	}

	private void givenCreditalWillFail(Account account) {
		BDDMockito.given(account.credit(any(Money.class), any(AccountId.class)))
				.willReturn(false);
	}

	private void givenCreditalWillSucceed(Account account) {
		BDDMockito.given(account.credit(any(Money.class), any(AccountId.class)))
				.willReturn(true);
	}

	private Account givenTargetAccount(){
		return givenAnAccountWithId(new AccountId(42L, "Bob"));
	}

	private Account givenSourceAccount(){
		return givenAnAccountWithId(new AccountId(41L, "Alice"));
	}

	private Account givenAnAccountWithId(AccountId id) {
		Account account = Mockito.mock(Account.class);
		BDDMockito.given(account.getId())
				.willReturn(Optional.of(id));
		BDDMockito.given(findCustomerPort.searchCustomer(eq(account.getId().get())))
				.willReturn(account);
		return account;
	}

	private MoneyTransferLimit moneyTransferLimit(){
		return new MoneyTransferLimit(Money.of(Long.MAX_VALUE));
	}

	private PerDayLimit perDayLimit(){
		return new PerDayLimit(Money.of(Long.MAX_VALUE));
	}

}
