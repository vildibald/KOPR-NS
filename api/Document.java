/**
 * Created by Viliam on 3.2.2014.
 */
public class Document {

    private String name;
    private String content;

    public Document() {
    }

    public Document(String name) {
        this.name = name;
    }

    public Document(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
