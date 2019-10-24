package com.gutieuler.lottery.model.functions;

import java.util.function.Function;

public class Identity implements Function<Long, Long> {

	@Override
	public Long apply(Long arg0) {
		return arg0;
	}

}
