package com.nflabs.peloton2.kafka.producer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class Context {
	Properties prop;

	Context( String path) throws Exception{
	    File f = new File(path);
		if (!f.exists()) {
		    throw new FileNotFoundException(path);
		}
	    prop = new Properties();
	    InputStream is = new FileInputStream(path);
	    prop.load(is);
	}

	public String getString(String key){
		return prop.getProperty(key);
	}

}
