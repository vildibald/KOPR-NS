import org.restlet.Component;
import org.restlet.data.Protocol;

/**
 * Created by Viliam on 3.2.2014.
 */
public class IndexerStarter {

    private static final int SERVER_PORT = 8182;

    public static void main(String[] args) {
        try {
            Component component = new Component();
            component.getServers().add(Protocol.HTTP, SERVER_PORT);
            component.getDefaultHost().attach("/indexer", new IndexerApplication());
            component.start();
        } catch (Exception e) {
            System.err.println("Server couldn't be started.");
            e.printStackTrace();
        }
    }
}
