/**
 * 
 */
package business;


/**
 * @author alexander
 * 
 */
public final class Publisher {
    private final int id;
    private String name;
    
    public Publisher(int id, final String name) {
        assert (name != null);
        this.id = id;
        this.name = name;
    }
    
    public Publisher(final String name) {
        assert (name != null);
        this.id = 0;
        this.name = name;
    }
    
    public int getId()      { return id; }
    public String getName() { return name; }
    
    public void setName(final String name) {
        this.name = name;
    }
}
