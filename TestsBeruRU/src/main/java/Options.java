import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

public class Options {

    static public String PASSWORD = "andreyselenium";
    static public String LOGIN = "testseleniumssu@yandex.com";
    static public String ADDRESS = "http://www.beru.ru";

    @DataProvider(name = "city-data-provider")
    static public Object[][] cityParametrs(){
        return new Object[][] {
                { "Хвалынск" },
                { "Волгоград" },
                { "Москва" },
        };
    }
}
