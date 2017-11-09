package ro.zizicu.mservice.order.data.finder;

import java.util.List;

import ro.zizicu.mservice.order.data.impl.QueryParameter;

public interface Finder<T> {
	void setup(List<QueryParameter> parameters);
	void execute();
	List<T> getResults();
}

