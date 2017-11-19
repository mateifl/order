package ro.zizicu.mservice.order.data.finder;

import java.util.List;

import javax.persistence.Query;

import ro.zizicu.mservice.order.data.impl.QueryParameter;

public interface ParameterMapper<T extends Object> {
	
	public void mapParameters(Query query, T parameters);
	
}

class CustomerFindParameterMapper implements ParameterMapper<List<QueryParameter>> {

	@Override
	public void mapParameters(Query query, List<QueryParameter> parameters) {
		// TODO Auto-generated method stub
		
	}
	
}