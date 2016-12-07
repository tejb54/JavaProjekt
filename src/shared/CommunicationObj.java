package shared;

import java.io.Serializable;

/**
 * Created by Tobias on 2016-12-06.
 */
public class CommunicationObj implements Serializable{

    //This will say what kind of object it is.
    public String header;

    //payload with the data.
    public Object payload;

    public CommunicationObj(String header, Object payload) {
        this.header = header;
        this.payload = payload;
    }
}




