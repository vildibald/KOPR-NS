import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import java.util.UUID;

/**
 * Created by Viliam on 3.2.2014.
 */
public class IndexerApplication extends Application {

    private DocumentMap<UUID,Document> documents = DocumentMap.getInstance();
    private DocumentMapDao documentMapDao = DocumentMapDao.getInstance();

    @Override
    public Restlet createInboundRoot() {
        documentMapDao.loadFromDisk(documents);
        Router router = new Router(getContext());
        router.attach("/documents", DocumentsResource.class);
        router.attach("/documents/byString/{string}", DocumentByStringResource.class);
        router.attach("/documents/byId/{uuid}", DocumentByIdResource.class);
        router.attach("/documents/byIds/{uuids}", DocumentByIdsResource.class);
        return router;
    }
}
