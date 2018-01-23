import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Created by Viliam on 3.2.2014.
 */
public class DocumentByStringResource extends ServerResource {

    private String string;
    private DocumentMapDao documentMapDao = DocumentMapDao.getInstance();

    @Override
    protected void doInit() throws ResourceException {
        string = (String) getRequestAttributes().get("string");
        System.out.println(string);
    }

    @Get("json")
    public List<String> findDocumentsByString() {
        UUID[] uuids = documentMapDao.findInDocuments(string);
        List<String> stringUuids = new ArrayList<String>(uuids.length);
        for (int i = 0; i < uuids.length; i++) {
            stringUuids.add(uuids[i].toString());
            System.out.println(uuids[i].toString());
        }
        return stringUuids;
    }
}
