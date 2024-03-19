package com.casetest.account.adapter.in.rest;

import static org.mockito.ArgumentMatchers.eq;

import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.casetest.account.adapter.port.in.rest.TransferController;
import com.casetest.account.application.port.in.MakeOrder;
import com.casetest.account.application.port.in.OrderTransferUseCase;
import com.casetest.account.domain.Account.AccountId;
import com.casetest.account.domain.Money;


@WebMvcTest(controllers = TransferController.class)
public class TransferTest {

    @Autowired
	private MockMvc mockMvc;

	@MockBean
	private OrderTransferUseCase orderTransferUseCase;

	// @Test
	void testTransfer() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/accounts/transfer/{sourceName}/{value}/{destinationName}",
				"Alice", 500, "Bob")
				.header("Content-Type", "application/json"))
				.andExpect(MockMvcResultMatchers.status().isOk());

				
		BDDMockito.then(orderTransferUseCase).should()
				.transfer(eq(new MakeOrder(
								new AccountId("Alice"), 
								Money.of(500L), 
								new AccountId("Bob"))));
	}

}
