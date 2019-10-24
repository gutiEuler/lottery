package com.gutieuler.lottery.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.function.Function;

import com.cedarsoftware.util.io.JsonReader;
import com.cedarsoftware.util.io.JsonWriter;

/**
 * 
 * @author root
 * 
 * It generates elements of type T using two functions:
 * 	Domain Function: [seed, seed + amount] -> U
 *  Codomain Function: U -> T
 *  
 * @param <U> Intermediate type. Domain function transforms a integer [seed, seed + amount] in a T type.
 * @param <T> Final type: Codomain function transform from T to U.
 * 
 * Example: 5 even number of squared number from 10.
 * 	amount = 5;
 *  seed = 10;
 *  length = 4;
 *  padding = '0'
 *  Domain => x -> 2 + x   ==> (12, 14, 16, 18, 20)
 *  CoDomain => x -> x*x ==> (144, 196 ,256, 324,400)
 *  Formated =>  0144, 0196, 0256, 0324, 0400
 */
public class Generator<U,T> implements Serializable{

	
	private static final long serialVersionUID = 1L;

	
	//Format Options
	
	private int minLength=4;

	private char padding='-';
	
	
	
	//Generation Options
	
	private long timeout=10000; //(ms)

	private boolean repeatAllowed=false;
	
	
	
	//CoDomain definition
	
	private Function<U, T> coDomain;
	
	
	
	//Domain definition 
	
	private long seed;
	
	private Function<Long,U> domain;
	
	
	//GETTER Y SETTER
	public int getMinLength() {
		return minLength;
	}

	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}

	public char getPadding() {
		return padding;
	}

	public void setPadding(char padding) {
		this.padding = padding;
	}

	public boolean isRepeatAllowed() {
		return repeatAllowed;
	}

	public void setRepeatAllowed(boolean repeatAllowed) {
		this.repeatAllowed = repeatAllowed;
	}

	public Function<U, T> getCoDomain() {
		return coDomain;
	}

	public void setCoDomain(Function<U, T> coDomain) {
		this.coDomain = coDomain;
	}

	public long getSeed() {
		return seed;
	}

	public void setSeed(long seed) {
		this.seed = seed;
	}

	public Function<Long, U> getDomain() {
		return domain;
	}

	public void setDomain(Function<Long, U> domain) {
		this.domain = domain;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}
	
	
	public Collection<T> produces(int amount, GeneratorListener<T> generatorListener){
		
		if (generatorListener!=null)
			generatorListener.start();
		
		long cnt = seed;
		
		Collection<T> result = null;
		
		
		if (repeatAllowed)
			result= new ArrayList<>();
		else
			result = new LinkedHashSet<>();
		
		
		long elapsedTime = 0;
		
		long time = System.nanoTime();
		
		while(result.size() < amount && elapsedTime < timeout) {
			
			T value = coDomain.apply((domain.apply(cnt)));
			
			
			if (result.add(value) &&(generatorListener!=null))
				generatorListener.element(value);
			
			cnt++;
			
			elapsedTime = (long) ((System.nanoTime() - time) / Math.pow(10, 6)); //ms
		
		}
		
		
		if (generatorListener!=null)
			generatorListener.ends(result);
		
		return result; 
		
	}
	
	
	public Collection<T> produces(int amount){
		
		return produces(amount, null);
		
	}
	
	
	public void producesAsync(int amount, GeneratorListener<T> generatorListener ){
		
		Runnable producer = new Runnable() {

			@Override
			public void run() {
				
				produces(amount, generatorListener);
				
			}
			
		};
		
		Thread t = new Thread(producer,"Generator");
		
		t.start();
		
	}
	
	
	private String format (T element) {
		
		String str = element.toString();
		
		int diff = minLength - str.length();
		
		
		if (element.toString().length() < minLength) {
			
			String repeated = new String(new char[diff]).replace("\0", padding+"");
			
			str= repeated + str;
			
		}
	
		return str;
		
	}
	
	
	
	public Collection<String> save(int amount) {
		
		ArrayList<String> formattedCollection = new ArrayList<>();
		
		produces(amount).forEach(t -> formattedCollection.add(format(t)));
			
		return formattedCollection;
	}
	
	
	public void print(int amount) {
		
		System.out.println("------SEQUENCE------");
		
		save(amount).forEach(t -> System.out.println(t));
		
	}
	
	
	
	public static Generator<?,?>  generationConfigFromFile(String path) throws Exception {
		
		Generator<?,?> res=null;
		
		try(FileInputStream inputStream = new FileInputStream(path)) {  
			
			StringBuilder json = new StringBuilder();
		    
			Files.readAllLines(Paths.get(path)).forEach(line -> json.append(line));
		    
		    res = (Generator<?,?>) JsonReader.jsonToJava(json.toString());
		
		} catch (Exception e) {
			
			e.printStackTrace();
			
			throw(e);
		
		} 
		
		return res;

	}
	
	
	public void serialize(String path) throws IOException {
	
		String jsonGC = JsonWriter.objectToJson(this);
		
		Files.write(Paths.get(path), jsonGC.getBytes());
		
		
	}
	
	
}
