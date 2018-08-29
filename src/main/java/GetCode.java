import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import java.util.UUID;

public class GetCode {
    public String uniqueCode(Datastore datastore){
        Long length = 1L;
        String uuid;
        while(true){
            uuid = UUID.randomUUID().toString().substring(0, 6);
            Query<Group> query = datastore.createQuery(Group.class);
            length = query.field("code").contains(uuid).count();
            if(length == 0){
                System.out.println("duplicate not found");
                break;
            } else {
                System.out.println("duplicate found");
                this.uniqueCode(datastore);
            }
        }
        return uuid;
    }
}
