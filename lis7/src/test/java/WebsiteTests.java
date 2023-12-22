import static com.codeborne.selenide.Condition.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;


import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class WebsiteTests {
    private static ChromeDriver driver;
    private String _url = "https://www.avana.ru/";

    @BeforeClass
    public static void setUp() {
        driver = new ChromeDriver();
    }

    @AfterClass
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // О компании
    @Test
    public void TestAvana1() {
        open(_url);
        $x("//a[@href='/avana']").click();
        sleep(300);
        $x("//div[@class='vtIssue']").shouldBe(visible);
        sleep(300);
    }

    // Открытие вкладки "Каталог"
    @Test
    public void TestAvana2() throws Exception {
        open(_url);
        $x("//a[@href='/catalog']").click();
        sleep(300);
        var url = driver.getCurrentUrl();
        var expectedUrl = "https://www.avana.ru/catalog";
        if(url.equals(expectedUrl)) throw new Exception("");
        sleep(300);
    }

    // Открытие вкладки "Женское"
    @Test
    public void TestAvana3() throws Exception {
        open(_url);
        $x("//a[@href='/catalog/women']").click();
        sleep(300);
        var url = driver.getCurrentUrl();
        var expectedUrl = "https://www.avana.ru/catalog/women";
        if(url.equals(expectedUrl)) throw new Exception("");
        sleep(300);
    }

    // Открытие корзины
    @Test
    public void TestAvana4() throws Exception {
        open(_url);
        sleep(300);
        $x("//a[@href='#modalShoppingCart']").click();
        sleep(300);
        $x("//div[@id='modalShoppingCart']").shouldBe(visible);
    }

    // Проверка фильтра "Скидки"
    @Test
    public void TestAvana5() throws Exception {
        open("https://www.avana.ru/catalog/women");
        sleep(300);
        executeJavaScript("document.querySelector(\"label[for='filter_IsDiscount']\").click();");
        sleep(300);
        executeJavaScript("document.querySelector(\"li.nav-item.text-center.my-3 > button.btn.btn-primary\").click();");
        $x("//span[@class='cost-action d-inline-block text-nowrap']").shouldBe(visible);
        sleep(300);
    }

    // Проверка фильтра "Скидки"
    @Test
    public void TestAvana6() throws Exception {

        open("https://www.avana.ru/catalog/women");
        sleep(300);
        executeJavaScript("document.querySelector(\"body > div.vt-main > main > div > section > div > div.col-12.col-md-8.col-lg-9 > div.row.align-items-center.mb-3 > div.col-12.col-md-auto.d-none.d-md-block > select\").click();");
        sleep(300);
        executeJavaScript("document.querySelector(\"body > div.vt-main > main > div > section > div > div.col-12.col-md-8.col-lg-9 > div.row.align-items-center.mb-3 > div.col-12.col-md-auto.d-none.d-md-block > select > option:nth-child(2)\").click();");
        sleep(300);
        var url = driver.getCurrentUrl();
        var expectedUrl = "https://www.avana.ru/catalog/women?ListOrder=Cost";
        if(url.equals(expectedUrl)) throw new Exception("");
        sleep(300);
    }

    // Открытие карточки товара
    @Test
    public void TestAvana7() throws Exception {
        open(_url);
        $x("//a[@href='/product/6d5c9637-cb6c-41d3-a800-e3d94ec66baa']").shouldBe(visible).click();
        sleep(300);

        var url = driver.getCurrentUrl();
        var expectedUrl = "https://www.avana.ru/product/6d5c9637-cb6c-41d3-a800-e3d94ec66baa";
        if(url.equals(expectedUrl)) throw new Exception("");
        sleep(300);
    }

    // Добавление в избранное
    @Test
    public void TestAvana8() throws Exception {
        open("https://www.avana.ru/product/6d5c9637-cb6c-41d3-a800-e3d94ec66baa");
        sleep(300);
        $x("//button[@class='btn btn-outline-dark btn-block mb-2 in-wish']").shouldBe(visible).click();
        sleep(300);
        $x("//a[@href='/products/wish']").shouldBe(visible).click();
        sleep(300);
        $x("//a[@href='/product/6d5c9637-cb6c-41d3-a800-e3d94ec66baa']").shouldBe(visible);
        sleep(300);
    }

    // Открытие таблицы размеров
    @Test
    public void TestAvana9() throws Exception {
        open(_url);
        sleep(300);
        $x("//a[@href='/sizes']").shouldBe(visible).click();
        sleep(300);
        var url = driver.getCurrentUrl();
        var expectedUrl = "https://www.avana.ru/sizes";
        if(url.equals(expectedUrl)) throw new Exception("");
    }

    // все новости
    @Test
    public void TestAvana10() throws Exception {
        open(_url);
        sleep(300);
        $x("//a[@href='/page/188690d3-4f08-4097-9ed4-711421202ae2']").click();
        sleep(300);
        var url = driver.getCurrentUrl();
        var expectedUrl = "https://www.avana.ru/news";
        if(url.equals(expectedUrl)) throw new Exception("");
    }

    // новостная карточка
    @Test
    public void TestAvana11() throws Exception {
        open(_url);
        sleep(300);
        $x("//a[@href='/p/f88006850ebb4646ac6ed0d78f5dda26']").shouldBe(visible).click();
        sleep(300);
        var url = driver.getCurrentUrl();
        var expectedUrl = "https://www.avana.ru/p/f88006850ebb4646ac6ed0d78f5dda26";
        if(url.equals(expectedUrl)) throw new Exception("");
    }

    // Добавление в корзину
    @Test
    public void TestAvana12() throws Exception {
        open("https://www.avana.ru/product/6d5c9637-cb6c-41d3-a800-e3d94ec66baa");
        sleep(300);
        $x("//button[@class='btn btn-block btn-dark mb-2']").shouldBe(visible).click();
        sleep(300);
        $x("//a[@href='#modalShoppingCart']").click();
        sleep(300);
        $x("//div[@id='modalShoppingCart']").shouldBe(visible);
        sleep(300);
        $x("//a[@href='/product/6d5c9637-cb6c-41d3-a800-e3d94ec66baa']").shouldBe(visible);
        sleep(300);

    }

    // переход в корзину
    @Test
    public void TestAvana13() throws Exception {
        open("https://www.avana.ru/product/6d5c9637-cb6c-41d3-a800-e3d94ec66baa");
        sleep(300);
//        $x("//button[@class='btn btn-block btn-dark mb-2']").shouldBe(visible).click();
        sleep(300);
        $x("//a[@href='/Shop/Order/OrderCard/OrderCard']").shouldBe(visible).click();
        sleep(300);
        $x("//a[@href='/product/6d5c9637-cb6c-41d3-a800-e3d94ec66baa']").shouldBe(visible);
        sleep(300);

    }

    // удаление из корзины
    @Test
    public void TestAvana14() throws Exception {
        open("https://www.avana.ru/product/6d5c9637-cb6c-41d3-a800-e3d94ec66baa");
//        $x("//button[@class='btn btn-block btn-dark mb-2']").shouldBe(visible).click();
        sleep(300);
        $x("//a[@href='/Shop/Order/OrderCard/OrderCard']").shouldBe(visible).click();
        sleep(300);
        $x("//a[@href='/product/6d5c9637-cb6c-41d3-a800-e3d94ec66baa']").shouldBe(visible);
        sleep(300);
        $x("//div[@class='btn-delete-item']").shouldBe(visible).click();
        sleep(300);
        $x("//a[@href='/product/6d5c9637-cb6c-41d3-a800-e3d94ec66baa']").shouldBe(not(visible));
        sleep(300);

    }

    // Удаление из избранное
    @Test
    public void TestAvana15() throws Exception {
        open("https://www.avana.ru/product/6d5c9637-cb6c-41d3-a800-e3d94ec66baa");
        sleep(300);
//        $x("//button[@class='btn btn-outline-dark btn-block mb-2 in-wish']").shouldBe(visible).click();
        sleep(300);
        $x("//a[@href='/products/wish']").shouldBe(visible).click();
        sleep(300);
        $x("//a[@href='/product/6d5c9637-cb6c-41d3-a800-e3d94ec66baa']").shouldBe(visible);
        sleep(300);
        $x("//div[@class='product-img position-relative']").hover();
        sleep(300);
        $x("//button[@class='btn btn-xs btn-circle btn-white-primary shadow-lg']").shouldBe(visible).click();
        sleep(300);
        $x("//div[@class='alert alert-info']").shouldBe(visible);
    }

    // вход
    @Test
    public void TestAvana16() throws Exception {
        open(_url);
        sleep(300);
        $x("//a[@href='/login?returnUrl=%2Fcabinet']").shouldBe(visible).click();
        sleep(300);
        $x("//input[@id='Login']").setValue("mez@mail.ru");
        sleep(300);
        $x("//input[@id='Password']").setValue("mez@mail.ru");
        sleep(300);
        $x("//button[@class='btn btn-primary']").shouldBe(visible).click();
    }

    // регистрация
    @Test
    public void TestAvana17() throws Exception {
        open(_url);
        sleep(300);
        $x("//a[@href='/login?returnUrl=%2Fcabinet']").shouldBe(visible).click();
        sleep(300);
        $x("//a[@href='/cabinet/new?returnUrl=%2Fcabinet']").shouldBe(visible).click();
        sleep(300);
        $x("//input[@id='Email']").setValue("mez@mail.ru");
        sleep(300);
        $x("//input[@id='Password']").setValue("mez@mail.ru");
        sleep(300);
        $x("//button[@class='btn btn-primary']").shouldBe(visible).click();
        sleep(300);
        $x("//div[@class='alert alert-danger alert-dismissible fade show']").shouldBe(visible);
    }

    // whatsapp
    @Test
    public void TestAvana18() throws Exception {
        open(_url);
        String originalTab = getWebDriver().getWindowHandle();

        sleep(300);
        $x("//a[@href='https://wa.me/79642119057']").shouldBe(visible).click();
        sleep(300);
        switchTo().window(1);
        sleep(2000);
        var url = driver.getCurrentUrl();
        var expectedUrl = "https://api.whatsapp.com/send/?phone=79642119057&text&type=phone_number&app_absent=0";
        if(url.equals(expectedUrl)) throw new Exception("");

        sleep(300);
        closeWindow();

        sleep(300);
        switchTo().window(originalTab);
    }



    // бренд moremore
    @Test
    public void TestAvana19() throws Exception {
        open(_url);
        sleep(300);
        $x("//a[@href='/brand']").shouldBe(visible).click();
        sleep(300);
        $x("//a[@href='/brand/moremore']").shouldBe(visible).click();
        sleep(300);
        var url = driver.getCurrentUrl();
        var expectedUrl = "https://www.avana.ru/brand/moremore";
        if(url.equals(expectedUrl)) throw new Exception("");
    }

    // Поиск
    @Test
    public void TestAvana20() throws Exception {
        open(_url);
        sleep(300);
        executeJavaScript("document.querySelector(\"#navbarCollapse > div.d-none.d-lg-block > ul > li:nth-child(1) > span\").click();");
        sleep(300);
        $x("//input[@name='SearchText']").setValue("Джинсы");
        sleep(300);
        $x("//button[@type='submit']").click();
        sleep(500);
        var url = driver.getCurrentUrl();
        var expectedUrl = "https://www.avana.ru/search?SearchText=%D0%94%D0%B6%D0%B8%D0%BD%D1%81%D1%8B";
        if(url.equals(expectedUrl)) throw new Exception("");
    }
}
