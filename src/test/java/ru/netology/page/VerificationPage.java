package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private SelenideElement codeFiled = $("[data-test-id=code] input");
    private SelenideElement verifyButton = $("[data-test-id = action-verify]");


    public VerificationPage() {
        codeFiled.shouldBe(visible);
    }

    public DashboardPage validVerify(DataHelper.VerificationCode verificationCode) {
        codeFiled.setValue(verificationCode.getCode());
        verifyButton.click();
        return new DashboardPage();
    }
}
