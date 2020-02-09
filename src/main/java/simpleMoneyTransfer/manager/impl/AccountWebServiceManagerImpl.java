package simpleMoneyTransfer.manager.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import simpleMoneyTransfer.accessor.impl.DataBaseAccessorImpl;
import simpleMoneyTransfer.constants.Errors;
import simpleMoneyTransfer.exceptions.SimpleMoneyTransferApplicationException;
import simpleMoneyTransfer.manager.spi.AccountWebServiceManager;
import simpleMoneyTransfer.webServices.dto.AccountDTO;
import com.google.inject.Inject;
import simpleMoneyTransfer.webServices.dto.UpdateDTO;

@Slf4j
public class AccountWebServiceManagerImpl implements AccountWebServiceManager{

    @Inject
    private DataBaseAccessorImpl accessor;

    @Override
    public void createAccount(AccountDTO accountDTO) {
        Long accountNumber = accountDTO.getAccountNumber();
        if (!accessor.hasKey(accountNumber)) {
            accessor.save(accountNumber, accountDTO);
        } else {
            log.error("Account already exists for the account number : {}", accountNumber);
            throw new SimpleMoneyTransferApplicationException(Errors.ACCOUNT_NUMBER_ALREADY_EXISTS_ERR);
        }
    }

    @Override
    public void updateAccount(AccountDTO accountDTO) {
        Long accountNumber = accountDTO.getAccountNumber();
        if (accessor.hasKey(accountNumber)) {
            accessor.save(accountNumber, accountDTO);
        } else {
            log.error("Account Not Found for account number : {}", accountNumber);
            throw new SimpleMoneyTransferApplicationException(Errors.ACCOUNT_NUMBER_NOT_FOUND_ERR);
        }
    }

    @Override
    public void updateAccount(UpdateDTO updateDTO) {
        Long accountNumber = updateDTO.getAccountNumber();
        if (accessor.hasKey(accountNumber)) {
            AccountDTO accountDTO = accessor.get(accountNumber);
            String emailId = updateDTO.getEmailId();
            String mobileNo = updateDTO.getMobileNo();
            if (!StringUtils.isEmpty(emailId)) {
                accountDTO.setEmailId(emailId);
            }
            if (!StringUtils.isEmpty(mobileNo)) {
                accountDTO.setMobileNo(mobileNo);
            }
            accessor.save(accountNumber, accountDTO);
        } else {
            log.error("Account Not Found for account number : {}", accountNumber);
            throw new SimpleMoneyTransferApplicationException(Errors.ACCOUNT_NUMBER_NOT_FOUND_ERR);
        }
    }

    @Override
    public AccountDTO getAccount(Long accountNumber) {
        if (accessor.hasKey(accountNumber)) {
            return accessor.get(accountNumber);
        } else {
            log.error("Account Number Not Found : {}", accountNumber);
            throw new SimpleMoneyTransferApplicationException(Errors.ACCOUNT_NUMBER_NOT_FOUND_ERR);
        }
    }

    @Override
    public void deleteAccount(Long accountNumber) {
        if (accessor.hasKey(accountNumber)) {
            accessor.remove(accountNumber);
        } else  {
            log.error("Account not found for account number : {}", accountNumber);
            throw new SimpleMoneyTransferApplicationException(Errors.ACCOUNT_NUMBER_NOT_FOUND_ERR);
        }
    }
}
