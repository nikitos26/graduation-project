package pages;

import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selectors.byCssSelector;
import static com.codeborne.selenide.Selenide.$;

@Log4j
public class DeliveryPopup {
    private final SelenideElement popup = $(byCssSelector(".auth-popup.auth-popup_primary.auth-popup_middle"));
    private final SelenideElement closeButton = popup.$(byCssSelector(".auth-popup__close"));
    private final SelenideElement inputField = popup.$(byCssSelector(".auth-input.auth-input_primary"));
    private final SelenideElement changeButton = popup.$(byCssSelector(".auth-button"));
    private final SelenideElement dropdownList = popup.$(byCssSelector(".auth-dropdown"));
    private final SelenideElement dropdownListLoadingCircle = popup.$(byCssSelector(".auth-dropdown.auth-dropdown_animated"));

    public DeliveryPopup verifyPopup() {
        this.popup.shouldBe(exist);
        this.closeButton.shouldBe(exist);
        this.changeButton.shouldBe(exist);
        log.info("Verify delivery popup complete");
        return this;
    }

    public DeliveryPopup closePopup() {
        log.info("Close delivery popup");
        this.closeButton.click();
        return this;
    }

    public DeliveryPopup inputLocation(String location) {
        log.info("Input location");
        this.inputField.shouldBe(exist).sendKeys(location);
        return this;
    }

    public DeliveryPopup clickChangeButton() {
        log.info("Apply location");
        this.changeButton.click();
        return this;
    }

    public DeliveryPopup findLocationInDropdownList(String location) {
        this.dropdownList.shouldBe(exist);
        this.dropdownListLoadingCircle.shouldNotBe(exist);
        SelenideElement locationInList = dropdownList.$(byText(location));
        log.info("Search location in dropdown list");
        if (locationInList.isDisplayed()) {
            log.info("Click on location " + location);
            locationInList.click();
            return this;
        }
        log.warn("Location didnt find");
        this.closeButton.click();
        return this;
    }

    public DeliveryPopup chooseLocation(String location) {
        inputLocation(location);
        findLocationInDropdownList(location);
        if (this.changeButton.isDisplayed()) {
            clickChangeButton();
            return this;
        }
        closePopup();
        return this;
    }
}
