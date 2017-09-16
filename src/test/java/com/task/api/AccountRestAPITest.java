package com.task.api;

import com.task.Task1Application;
import com.task.model.Account;
import com.task.service.AccountService;
import com.task.util.DataConflictException;
import com.task.util.DataNotFindException;

import java.math.BigDecimal;

import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Task1Application.class)
public class AccountRestAPITest {

    @Autowired
    private AccountService accountService;

    public AccountRestAPITest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @Test
    public void testAddAccount() {
        System.out.println("addAccount");
        int preAddSize = accountService.getAllAccount().size();
        Account newAccount = Account.builder()
                .account_id(12)
                .customername("Alysha Martin")
                .currency("USA")
                .amount(new BigDecimal(25000.0))
                .build();

        accountService.createAccount(newAccount);
        int postAddSize = accountService.getAllAccount().size();
        assertThat("Account List is being updated", postAddSize, Matchers.greaterThan(preAddSize));
    }

    @Test(expected = DataConflictException.class)
    public void testConflictOnAddAccount() {
        Account newAccount = Account.builder()
                .account_id(12)
                .customername("John Doe")
                .currency("USD")
                .amount(new BigDecimal(25000.0))
                .build();
        accountService.createAccount(newAccount);
    }

    @Test(expected = DataNotFindException.class)
    public void testNoAccountFoundException() {
        accountService.getAccount(465464);
    }

    @Test
    public void testGetAccount() {
        Account exgAccount = accountService.getAccount(1);
        assertNotNull("Find existing account by Id:", exgAccount);
    }

}
