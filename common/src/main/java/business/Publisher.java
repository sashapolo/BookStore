/**
 * 
 */
package business;


import java.io.Serializable;

/**
 * @author alexander
 * 
 */
public final class Publisher implements Serializable {
    private static final long serialVersionUID = 4146237754773412375L;
    private final int id;
    private String name;
    
    public Publisher(final int id, final String name) {
        assert name != null;
        this.id = id;
        this.name = name;
    }
    
    public Publisher(final String name) {
        assert name != null;
        id = 0;
        this.name = name;
    }
    
    public int getId()      { return id; }
    public String getName() { return name; }
    
    public void setName(final String name) {
        this.name = name;
    }
}
