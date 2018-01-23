import java.util.concurrent.ConcurrentHashMap;

public class DocumentMap<UUID,Document> extends ConcurrentHashMap<UUID,Document>{
//public class DocumentMap<K,V> extends ConcurrentHashMap<K,V>{

    private DocumentMap() {
    }

    private static class Holder{
        private static final DocumentMap INSTANCE = new DocumentMap();
    }

    public static DocumentMap getInstance(){
        return Holder.INSTANCE;
    }
}
