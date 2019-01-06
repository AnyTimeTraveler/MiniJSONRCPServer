import org.simonscode.minijsonrpc.annotations.RemoteMethod;
import org.testng.annotations.Test;

public class AnnotationTest {
    @RemoteMethod
    public void testMethod() {
        System.out.println("AnnotationTest.testMethod");
    }

    @Test
    public void test() {

    }
}
