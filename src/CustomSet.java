import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class CustomSet {

	private final List data = new LinkedList();
	
	public boolean add(Object object) {
		if (!contains(object)) {
			return data.add(object);
		}
		return false;
	}
	
	public boolean remove(Object object) {
		return data.remove(object);
	}
	
	public boolean contains(Object object) {
		return data.contains(object);
	}
	
	public Iterator iterator() {
		return data.iterator();
	}
	
}
