package com.gutieuler.lottery.model.functions;

import java.util.function.Function;

public class Alphabet implements Function<Long, String> {

	
	
	
	@Override
	public String apply(Long n) {
		
		StringBuilder res = new StringBuilder();
		
		while(n > 0) {
					
			int mod = ((int) n.longValue()%23);
			
			char c = (char) ('a' + mod);
	 
			n/=23;
			
			res.append(c);
		}
		
		return res.reverse().toString();
	}

}
