package ro.zizicu.mservice.order.data.impl;

public class QueryParameter<T> {
	public final String name;
	public final Class<T> type;
	public final T value;
	
	public QueryParameter(String name, T value) {
		this.name = name;
		if(value != null)
			this.type = (Class<T>) value.getClass();
		else
			this.type = null;
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "QueryParameter [name=" + name + ", type=" + type + ", value=" + value + "]";
	}
	
	
}
