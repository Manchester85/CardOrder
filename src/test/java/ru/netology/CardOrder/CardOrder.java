package ru.netology.CardOrder;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;


public class CardOrder {
    private String date;

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        LocalDate dateNow = LocalDate.now();
        LocalDate datePlus3 = dateNow.plusDays(4);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        date = datePlus3.format(format);
    }

    @Test
    void shouldTestFields() {

        SelenideElement form = $("[action]");
        form.$("[data-test-id = city] input").setValue("Москва");
        form.$("[data-test-id=date] input").setValue(date);
        form.$("[data-test-id=name] input").setValue("Иван Петров");
        form.$("[data-test-id=phone] input").setValue("+79125345678");
        form.$("[data-test-id=agreement] .checkbox__box").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(Condition.exactText("Встреча успешно забронирована на " + date));
    }
}


