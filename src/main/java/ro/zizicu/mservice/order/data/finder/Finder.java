package ro.zizicu.mservice.order.data.finder;

import java.util.List;

public interface Finder<T> {
	void setup();
	void execute();
//	void processResults();
	List<T> getResults();
}

