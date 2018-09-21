package pay_api;

public class HashCodeTest {
	private String id;
    public HashCodeTest(String id) {
        this.id = id; 
    }
    
    public String toString(){
        return this.id;
    }
    public boolean equals(Object obj) {
        if (obj instanceof HashCodeTest) {
        	HashCodeTest name = (HashCodeTest) obj;
            System.out.println("equal"+ name.id);
            return (id.equals(name.id));
        }
        return super.equals(obj);
    }
        
    public int hashCode() {
    	HashCodeTest name = (HashCodeTest) this;
        System.out.println("Hash" + name.id);
        return id.hashCode();
            
    }
}
