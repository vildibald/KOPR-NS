import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Viliam on 3.2.2014.
 */
public class DocumentsResource extends ServerResource {

    private ConcurrentMap<UUID,Document> documentMap = DocumentMap.getInstance();
    private DocumentMapDao documentMapDao = DocumentMapDao.getInstance();
    @Post("json")
    public String addDocument(Document document) {
        UUID uuid = documentMapDao.newUUID();
        documentMapDao.saveToDisk(uuid, document);
        documentMap.putIfAbsent(uuid, document);

        return uuid.toString();
    }

    @Get("json")
    public Map<UUID, Document> mapDocuments() {
        return documentMap;
    }

}
