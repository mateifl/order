package ro.zizicu.mservice.order.data.finder;

import java.util.List;

public class FinderContext<T> {
	private Finder<T> finder;
	
	public FinderContext(Finder<T> finder) {
		this.finder = finder;
	}
	
	public  List<T> find() {
//		finder.setup();
//		finder.execute();
		return finder.getResults();
	}
}
