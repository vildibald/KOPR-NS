import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Viliam on 3.2.2014.
 */
public class DocumentMapDao {

    private static class Holder{
        private static final DocumentMapDao INSTANCE = new DocumentMapDao();
    }

    public static DocumentMapDao getInstance(){
        return Holder.INSTANCE;
    }

    public UUID[] parseArrayOfUuidsFromString(String string, String delimiter) throws IllegalArgumentException {
        String[] arrayOfStrings = string.split(delimiter);

        UUID[] array = new UUID[arrayOfStrings.length];

        for (int i = 0; i < arrayOfStrings.length; i++)
            array[i] = UUID.fromString(arrayOfStrings[i]);

        return array;
    }

    public UUID[] findInDocuments(String wordsToFind) {
        List<UUID> foundDocuments = new LinkedList<UUID>();
        String[] stringsToFind = wordsToFind.split("_");
        for(int i=0; i < stringsToFind.length;i++){
            System.out.println(stringsToFind[i]);
        }
        ConcurrentMap<UUID,Document> documentMap = DocumentMap.getInstance();
        int howManyOccurencies=-1;

        try {
            howManyOccurencies = Integer.parseInt(stringsToFind[0]);

        } catch (IllegalArgumentException e) {
        }
        //System.out.println("Occ: "+howManyOccurencies);
        howManyOccurencies= (howManyOccurencies == -1)? stringsToFind.length : howManyOccurencies;
        System.out.println("Occ: "+howManyOccurencies);

        Iterator it = documentMap.keySet().iterator();
        while (it.hasNext()) {
            UUID key = (UUID) it.next();
           // System.out.println("Key to work: "+key.toString());
            Document document = documentMap.get(key);

//value.equals("anotherstring");//May throw a Null Pointer
            //System.out.println("Doc. name to work: "+document.getName());
            if (!(document == null) && isStringInDocument(document, stringsToFind, howManyOccurencies)) { //check for null
                foundDocuments.add(key);
            }
        }

        return foundDocuments.toArray(new UUID[foundDocuments.size()]);
    }

    private boolean isStringInDocument(Document document, String[] stringToFind, int howManyOccurencies) {


        return areStringsInString(document.getContent(), stringToFind, howManyOccurencies)
                || areStringsInString(document.getName(), stringToFind, 2);
    }

    private boolean areStringsInString(String string, String[] stringsToFind, int howManyOccurencies) {
        int numberOfOccurencies = 0;
        // boolean stringsToFindAreInString = true;
        if (howManyOccurencies > stringsToFind.length)
            howManyOccurencies = stringsToFind.length;

        for (int i = 0; i < stringsToFind.length; i++) {
            if (string.contains(stringsToFind[i])) {
                ++numberOfOccurencies;
            }
        }

        return (numberOfOccurencies >= howManyOccurencies) ? true : false;
    }

    public UUID newUUID() {
        UUID uuid;
        while (true) {
            uuid = UUID.randomUUID();
            if (!DocumentMap.getInstance().containsKey(uuid))
                return uuid;
        }
    }

    public void loadFromDisk(ConcurrentMap<UUID, Document> map){
        File dir= new File("Database");

        try{
            System.out.println("Getting all files in " + dir.getCanonicalPath() + " including those in subdirectories");
            List<File> files = (List<File>) org.apache.commons.io.FileUtils.listFiles(dir, org.apache.commons.io.filefilter.TrueFileFilter.INSTANCE, org.apache.commons.io.filefilter.TrueFileFilter.INSTANCE);
            for (File file : files) {
                System.out.println("Loaded file: " + file.getCanonicalPath());
                UUID uuid = UUID.fromString(file.getParentFile().getName());

                FileInputStream fis = new FileInputStream(file);
                String content = IOUtils.toString(fis, "UTF-8");
                Document document = new Document(file.getName(), content);
                map.putIfAbsent(uuid, document);

            }
        }catch (IOException e){

        }
    }

    public void saveToDisk(UUID uuid, Document document) {
        String stringID = uuid.toString();
        String name = "Database\\" + stringID + "\\" + document.getName();
        File newFile = new File(name);
        Writer writer = null;
        try {
            // newFile.mkdirs();
            if (newFile.getParentFile().mkdir()) {
                newFile.createNewFile();
            } else {
                throw new IOException("Failed to create directory " + newFile.getParent());
            }
            writer = new BufferedWriter(new FileWriter(newFile));
            writer.write(document.getContent());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
