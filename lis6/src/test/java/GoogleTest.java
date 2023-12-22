import com.codeborne.selenide.Condition;
import org.junit.Test;

import static com.codeborne.selenide.Selenide.*;

public class GoogleTest {
    @Test
    public void TestGoogle() {
        open("https://www.google.com/");
        $x("//textarea[@name='q']").setValue("Мезенцев Дмитрий Сергеевич").pressEnter();
        $x("//div[@id ='result-stats']").shouldBe(Condition.visible);
        sleep(2000);
    }
}