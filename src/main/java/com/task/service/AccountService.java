package com.task.service;

import com.task.model.Account;
import java.util.List;
import lombok.NonNull;

public interface AccountService {

    Account getAccount(@NonNull final Integer account_id);

    void createAccount(@NonNull final Account account);

    void updateAccount(@NonNull final Account account);

    List<Account> getAllAccount();
}
