package com.gutieuler.lottery.model;

import java.io.IOException;
import java.util.function.Function;

import pl.joegreen.lambdaFromString.LambdaCreationException;
import pl.joegreen.lambdaFromString.LambdaFactory;
import pl.joegreen.lambdaFromString.TypeReference;



/**
 * 
 * @author Gutieuler
 *
 * Allow write domain and codomain function with string. 
 * It must be compiled before be used.
 *
 */
public class GeneratorLongExpression extends Generator <Long,Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	


	String lambdaDomainExpression;
	
	String lambdaCoDomainExpression;
	
	private static LambdaFactory lambdaFactory = LambdaFactory.get();

	public String getLambdaDomainExpression() {
		return lambdaDomainExpression;
	}

	public void compileDomainFunction() throws LambdaCreationException {
		this.setDomain(
				lambdaFactory.createLambda(lambdaDomainExpression, new TypeReference<Function<Long, Long>>() {}));
	}
	
	public void compileCoDomainFunction() throws LambdaCreationException{
		this.setCoDomain(
				lambdaFactory.createLambda(lambdaCoDomainExpression, new TypeReference<Function<Long, Long>>() {}));
	}
	
	
	public void setLambdaDomainExpression(String lambdaDomainExpression){
		this.lambdaDomainExpression = lambdaDomainExpression;
		
	}

	public String getLambdaCoDomainExpression() {
		return lambdaCoDomainExpression;
		
		
	}

	public void setLambdaCoDomainExpression(String lambdaCoDomainExpression) throws LambdaCreationException {
		this.lambdaCoDomainExpression = lambdaCoDomainExpression;
		
	
	}
	
	public void serialize(String path) throws IOException {
		
		//I don t wanna save compiled function from expression.
		this.setDomain(null);
		this.setCoDomain(null);
		
		super.serialize(path);
		
	}
	
	
}
