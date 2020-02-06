package simpleMoneyTransfer.manager.spi;

import simpleMoneyTransfer.webServices.dto.TransferDTO;

public interface TransferWebServiceManager {

    void transfer(TransferDTO transferDTO);
}
