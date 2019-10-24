package com.gutieuler.lottery.model.functions;

import java.util.function.Function;

public class ExpensiveFibonacci implements Function<Long, Long> {

	@Override
	public Long apply(Long t) {
		if ((t==0)||(t==1)) {
			return 1L;
		} else {
			return apply(t-1) + apply(t-2);  
		}
		
	}

}

