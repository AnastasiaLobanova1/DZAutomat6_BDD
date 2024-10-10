package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private final SelenideElement transferButton = $("[data-test-id=action-transfer]");
    private final SelenideElement amountInputNew = $("[data-test-id=amount] input");
    private final SelenideElement fromInput = $("[data-test-id=from] input");
    private final SelenideElement transferHead = $(byText("Пополнение карты"));
    private final SelenideElement errorMessage = $("[data-test-id=error-notification] .notification__content");

    public TransferPage() {
        transferHead.shouldBe(visible);

    }

    public DashboardPage validTransfer(String amountToTransfer, DataHelper.CardInfo cardInfo) {
        makeTransfer(amountToTransfer, cardInfo);
        return new DashboardPage();
    }

    public void makeTransfer(String amountToTransfer, DataHelper.CardInfo cardInfo) {
        amountInputNew.setValue(amountToTransfer);
        fromInput.setValue(cardInfo.getCardNumber());
        $(transferButton).click();
    }

    public void errorLimit() {
        $(errorMessage).should(Condition.exactText("Выполнена попытка перевода суммы, превышающей остаток на карте списания"));

    }

    public void invalidCard() {
        $(errorMessage).should(Condition.exactText("Ошибка! Произошла ошибка"));


    }
}
