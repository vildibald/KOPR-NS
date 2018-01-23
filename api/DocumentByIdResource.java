import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Viliam on 3.2.2014.
 */
public class DocumentByIdResource extends ServerResource {

    private UUID uuid;
    private ConcurrentMap<UUID, Document> documentMap = DocumentMap.getInstance();

    @Override
    protected void doInit() throws ResourceException {
        String stringId = (String) getRequestAttributes().get("uuid");
        this.uuid = UUID.fromString(stringId);
    }

    @Get("json")
    public Document findById() {
        return documentMap.get(uuid);
    }
}
