package com.gutieuler.lottery.model.functions;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

public class RandomAsc implements Function<Long,Long>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Override
	public Long apply(Long t) {
		return ThreadLocalRandom.current().nextLong(t, 2*t);
	}

}
