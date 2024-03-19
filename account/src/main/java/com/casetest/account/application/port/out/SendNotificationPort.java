package com.casetest.account.application.port.out;

import com.casetest.account.application.port.in.MakeOrder;

public interface SendNotificationPort {

    boolean notifyAboutTransfer(MakeOrder order);

}
