/**
 * 
 */
package business;


/**
 * @author alexander
 * 
 */
public class Publisher {
    final int id_;
    final String name_;
    
    public Publisher(final int id, final String name) {
        assert (name != null);
        id_ = id;
        name_ = name;
    }
    
    public Publisher(final String name) { this(0, name); }
    
    public int getId()      { return id_; }
    public String getName() { return name_; }
}
