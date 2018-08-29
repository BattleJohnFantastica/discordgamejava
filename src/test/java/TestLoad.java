import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestLoad {
    public static void main(String[] args) {
        Properties prop = new Properties();
        InputStream input = null;
        try {

            input = new FileInputStream("config.properties");

            // load a properties file
            prop.load(input);

        } catch (IOException ex){
            ex.printStackTrace();
        } finally {
            System.out.println(prop.getProperty("uri"));
        }
    }
}
