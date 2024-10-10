package ru.netology.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPageV1;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataHelper.*;

public class MoneyTransferPage {
    DashboardPage dashboardPage;



    @BeforeEach
    public void setUp() {
        Configuration.timeout = 12000;
        open("http://localhost:9999/");
        var loginPage = new LoginPageV1();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = getVerificationCodeFor(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @Test
    public void shouldTransferMoneyOneToTwo() {
        int amount = 5000;
        var firstCard = getFirstCardInfo();
        var secondCard = getSecondCardInfo();
        var firstCardBalance = dashboardPage.getCardBalance(firstCard);
        var secondCardBalance = dashboardPage.getCardBalance(secondCard);
        var expectedFirstCardBalance = firstCardBalance - amount;
        var expectedSecondCardBalance = secondCardBalance + amount;
        var transfer = dashboardPage.selectCardToTransfer(secondCard);
        dashboardPage = transfer.validTransfer(String.valueOf(amount), firstCard);
        var actualFirstCardBalance = dashboardPage.getCardBalance(firstCard);
        var actualSecondCardBalance = dashboardPage.getCardBalance(secondCard);
        Assertions.assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        Assertions.assertEquals(expectedSecondCardBalance, actualSecondCardBalance);
    }
    @Test
    public void shouldTransferMoneyTwoToOne() {
        int amount = 5000;
        var firstCard = getFirstCardInfo();
        var secondCard = getSecondCardInfo();
        var firstCardBalance = dashboardPage.getCardBalance(firstCard);
        var secondCardBalance = dashboardPage.getCardBalance(secondCard);
        var expectedFirstCardBalance = firstCardBalance + amount;
        var expectedSecondCardBalance = secondCardBalance - amount;
        var transfer = dashboardPage.selectCardToTransfer(firstCard);
        dashboardPage = transfer.validTransfer(String.valueOf(amount), secondCard);
        var actualFirstCardBalance = dashboardPage.getCardBalance(firstCard);
        var actualSecondCardBalance = dashboardPage.getCardBalance(secondCard);
        Assertions.assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        Assertions.assertEquals(expectedSecondCardBalance, actualSecondCardBalance);
    }
    @Test
    public void shouldTransferMoneyOneToOne() {
        int amount = 5000;
        var firstCard = getFirstCardInfo();
        var secondCard = getSecondCardInfo();
        var firstCardBalance = dashboardPage.getCardBalance(firstCard);
        var secondCardBalance = dashboardPage.getCardBalance(secondCard);
        var transfer = dashboardPage.selectCardToTransfer(firstCard);
        dashboardPage = transfer.validTransfer(String.valueOf(amount), firstCard);
        transfer.invalidCard();
        var actualFirstCardBalance = dashboardPage.getCardBalance(firstCard);
        var actualSecondCardBalance = dashboardPage.getCardBalance(secondCard);
        Assertions.assertEquals(firstCardBalance, actualFirstCardBalance);
        Assertions.assertEquals(secondCardBalance, actualSecondCardBalance);
    }
    @Test
    public void shouldErrorTransferHighLimit() {
        int amount = 15000;
        var firstCard = getFirstCardInfo();
        var secondCard = getSecondCardInfo();
        var firstCardBalance = dashboardPage.getCardBalance(firstCard);
        var secondCardBalance = dashboardPage.getCardBalance(secondCard);
        var transfer = dashboardPage.selectCardToTransfer(firstCard);
        transfer.validTransfer(String.valueOf(amount), secondCard);
        transfer.errorLimit();
        var actualFirstCardBalance = dashboardPage.getCardBalance(firstCard);
        var actualSecondCardBalance = dashboardPage.getCardBalance(secondCard);
        Assertions.assertEquals(firstCardBalance, actualFirstCardBalance);
        Assertions.assertEquals(secondCardBalance, actualSecondCardBalance);
    }
    @Test
    public void shouldErrorTransferNullStartAmount() {
        int amount = 0700;
        var firstCard = getFirstCardInfo();
        var secondCard = getSecondCardInfo();
        var firstCardBalance = dashboardPage.getCardBalance(firstCard);
        var secondCardBalance = dashboardPage.getCardBalance(secondCard);
        var transfer = dashboardPage.selectCardToTransfer(secondCard);
        transfer.validTransfer(String.valueOf(amount), firstCard);
        transfer.errorLimit();
        var actualFirstCardBalance = dashboardPage.getCardBalance(firstCard);
        var actualSecondCardBalance = dashboardPage.getCardBalance(secondCard);
        Assertions.assertEquals(firstCardBalance, actualFirstCardBalance);
        Assertions.assertEquals(secondCardBalance, actualSecondCardBalance);
    }
}
