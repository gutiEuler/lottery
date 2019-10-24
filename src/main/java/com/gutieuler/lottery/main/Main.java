package com.gutieuler.lottery.main;

import java.io.IOException;
import java.util.Collection;

import com.gutieuler.lottery.model.Generator;
import com.gutieuler.lottery.model.GeneratorListener;
import com.gutieuler.lottery.model.GeneratorLongExpression;
import com.gutieuler.lottery.model.functions.Alphabet;
import com.gutieuler.lottery.model.functions.ExpensiveFibonacci;
import com.gutieuler.lottery.model.functions.Identity;

import pl.joegreen.lambdaFromString.LambdaCreationException;

public class Main {
	
	
	
	public static GeneratorListener<Long> listener = new GeneratorListener<Long>() {

		@Override
		public void start() {
			
			System.out.println("Generator Started");
			
		}

		@Override
		public void element(Long element) {
			
			System.out.println("Element created: "+ element);
		
		}

		@Override
		public void ends(Collection<Long> elements) {
			
			System.out.println("Generator end: " + elements.size() + " created");
		}
		
	};
	
	
	
	public static void Example1_GeneratorLongExpression() throws LambdaCreationException, IOException {
		
		//Configures
		
		System.out.println("----- EJEMPLO 1 -----");
		
		GeneratorLongExpression gce = new GeneratorLongExpression();
		
		gce.setPadding('0');
		 
		gce.setMinLength(4);
		
		gce.setRepeatAllowed(true);
		
		gce.setSeed(10);
		
		gce.setLambdaDomainExpression("x -> x + 1");
		gce.compileDomainFunction();
		
		gce.setLambdaCoDomainExpression("x-> x + 1");
		gce.compileCoDomainFunction();
		
		gce.setTimeout(10000);
		
		gce.print(10);
		
		gce.serialize("gcePlus2.gen");
		
		System.out.println("----- FIN EJEMPLO 1 -----");
		System.out.println("######################");
		System.out.println("######################");
		System.out.println("######################");
	}
	
	public static void Example2_GeneratorToFile() throws IOException {
		
		 System.out.println("----- EJEMPLO 2 -----");	
		
		 Generator<Long, String> g = new Generator<>();
		    
		 g.setDomain(new Identity());
		    
		 g.setCoDomain(new Alphabet());

		 g.setPadding('-');
		 
		 g.setMinLength(4);
		 
		 g.setTimeout(10000);
		 
		 g.print(10);
		 
		 g.serialize("Alfabeto.gen");
		    
		 System.out.println("----- FIN EJEMPLO 2 -----");
			System.out.println("######################");
			System.out.println("######################");
			System.out.println("######################");
	}
	
	
	
	public static void Example3_GeneratorFromFile() throws Exception {
		
		System.out.println("-------EJEMPLO 3--------");
		
		GeneratorLongExpression gen = (GeneratorLongExpression) Generator.generationConfigFromFile("gcePlus2.gen");
		
		gen.compileDomainFunction();
		
		gen.compileCoDomainFunction();
		
		gen.print(120);
		
		System.out.println("----- FIN EJEMPLO 3 -----");
		System.out.println("######################");
		System.out.println("######################");
		System.out.println("######################");
	}
	
	
	public static void Example4_AsyncGenerator() {
		
		System.out.println("------ EJEMPLO 4 --------"); 
		
		Generator<Long, Long> g = new Generator<>();
	    
	    g.setDomain(new Identity());
	    
	    g.setCoDomain(new ExpensiveFibonacci());

	    g.setPadding('-');
		 
		g.setMinLength(4);
		 
		g.setTimeout(10000);
		
		g.produces(1000, listener);	    
	    
		System.out.println("----- FIN EJEMPLO 4 -----");
		System.out.println("######################");
		System.out.println("######################");
		System.out.println("######################");
	}
	
	
	
	
	

	public static void main(String[] args) throws Exception {
		
		
		
		Example1_GeneratorLongExpression();
		Example2_GeneratorToFile();
		Example3_GeneratorFromFile();
		Example4_AsyncGenerator();
	   
	}

}
