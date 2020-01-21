package stats.model;

import java.util.HashMap;

import java.util.Map;

public class Model {

	private Map<String, Object> beansMap = new HashMap<String, Object>();
	
	public void putBean(String key, Object value) {
		this.beansMap.put(key, value);
	}
	
	public Object getBean(String key) {
		return this.beansMap.get(key);
	}
	
}
