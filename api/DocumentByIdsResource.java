import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;


/**
 * Created by Viliam on 3.2.2014.
 */
public class DocumentByIdsResource extends ServerResource {

    private UUID[] uuids;
    private ConcurrentMap<UUID, Document> documentMap = DocumentMap.getInstance();
    private DocumentMapDao documentMapDao = DocumentMapDao.getInstance();

    @Override
    protected void doInit() throws ResourceException, IllegalArgumentException {
        String stringIds = (String) getRequestAttributes().get("uuids");
        uuids = documentMapDao.parseArrayOfUuidsFromString(stringIds, "_");
    }

    @Get("json")
    public List<Document> findByIds() {
        List<Document> documentList = new ArrayList<Document>(uuids.length);
        for (int i = 0; i < uuids.length; i++) {
            if(documentMap.containsKey(uuids[i])){
                documentList.add(documentMap.get(uuids[i]));
            }
        }
        return documentList;
    }
}
