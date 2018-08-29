import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RemoteMongoClient {
    public MongoClient mongoClient(){
            Properties prop = new Properties();
            InputStream input = null;
            try {
                input = new FileInputStream("config.properties");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        // load a properties file
            try {
                prop.load(input);
            } catch (IOException e) {
                e.printStackTrace();
            }
            MongoClientURI uri = new MongoClientURI(prop.getProperty("uri"));
            return new MongoClient(uri);
    }

}
