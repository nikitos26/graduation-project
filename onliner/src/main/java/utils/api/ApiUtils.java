package utils.api;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class ApiUtils {
    public static String gettingCartId() {
        String jsonString = "";
        if ("firefox".equals(Configuration.browser)) {
            jsonString = $(By.cssSelector("#\\/id > td:nth-child(2) > span:nth-child(1) > span:nth-child(1)"))
                    .shouldBe(Condition.exist).getText().replace("\"", "");
            return jsonString;
        }
        if ("chrome".equals(Configuration.browser)) {
            jsonString = $x("//pre").shouldBe(Condition.exist).getText();
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(jsonString);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            String id = jsonObject.get("id").getAsString();
            return id;
        }
        return jsonString;
    }
}

