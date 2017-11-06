package ro.zizicu.mservice.order.data.impl;

public class QueryParameter<T> {
	public final String name;
	public final Class<T> type;
	public final Object value;
	private String operator;
	
	public QueryParameter(String name, Class<T> type, Object value) {
		this.name = name;
		this.type = type;
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "QueryParameter [name=" + name + ", type=" + type + ", value=" + value + "]";
	}
	
	
}
