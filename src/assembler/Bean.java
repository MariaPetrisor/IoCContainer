package assembler;

import java.util.HashMap;
import java.util.Map;

public class Bean {
	private String id;
	private String className;
	private Map<String, String> valueMap = new HashMap<String, String>();
	private Map<String, String> refMap = new HashMap<String, String>();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public Map<String, String> getValueMap() {
		return valueMap;
	}
	public void setValueMap(Map<String, String> valueMap) {
		this.valueMap = valueMap;
	}
	public Map<String, String> getRefMap() {
		return refMap;
	}
	public void setRefMap(Map<String, String> refMap) {
		this.refMap = refMap;
	}
	public void addValueMap(String key, String value) {
		this.valueMap.put(key, value);
	}
	public void addRefMap(String key, String value) {
		this.refMap.put(key, value);
	}
	@Override
	public String toString() {
		String s = "Bean [id=" + id + ", className=" + className + "]" ;
		s += "\nvalueMap=\n";
		for (Map.Entry entry : valueMap.entrySet()) {
		    s += entry.getKey() + ", " + entry.getValue() + "\n";
		}
		s += "\nrefMap=\n";
		for (Map.Entry entry : refMap.entrySet()) {
		    s += entry.getKey() + ", " + entry.getValue() + "\n";
		}
		
		return s;
	}
	
	
}
