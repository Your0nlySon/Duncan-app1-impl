package baseline;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FXMLControllerTest {

    private FXMLController cont = new FXMLController();

    @Test
    void testTextValidationWorks() {
        //test if I pass a good value through validateItems(TextField descriptionBox)

        Boolean actualResult = cont.validateItems("Brush Teeth");
        Boolean expectedResult = true;

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testTextValidationFailed() {
        //test if I pass a bad value through validateItems(TextField descriptionBox)
        Boolean actualResult = cont.validateItems("");
        Boolean expectedResult = false;

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testTextEditValidationWorks() {
        //test if I pass a good value through validateEditItems(String toString)
        Boolean actualResult = cont.validateEditItems("Jo Mama");
        Boolean expectedResult = true;

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testTextEditValidationFails() {
        //test if I pass a bad value through validateEditItems(String toString)

        Boolean actualResult = cont.validateEditItems("fkdsnfkjdiofwefklndskmvdnfvjhjfiuwnm,dfnbjfahfjsdhf,mdfnbjowrhfuerwhfkjdsnfakjgbawruoghjwfjfnds,mv efjovherwufnsdkmvndsifjdskjfhjerwiofndsjkvnefbuoaerwnfkjdshfuodafjkdfbvjuaerhfuewm,dfsnvjuerhfuerwiafm,dbuiahfewkjfndfsjkbvwruofjkdnvjaebnguihnawgvjfkjsdiofjdsiofdsklvndkvnsjodavnkjdsvnkjasdnkj;lvdsv");
        Boolean expectedResult = false;

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testDateEditValidationWorks() {
        //test if I pass a good value through validateEditDate(String toString)
        Boolean actualResult = cont.validateEditDate("2003-05-18");
        Boolean expectedResult = true;

        assertEquals(expectedResult,actualResult);
    }

    @Test
    void testDateEditValidationFails() {
        //test if I pass a bad value through validateEditDate(String toString)
        Boolean actualResult = cont.validateEditDate("18-18-2048");
        Boolean expectedResult = false;

        assertEquals(expectedResult,actualResult);
    }


}