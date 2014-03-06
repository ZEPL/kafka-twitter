package com.nflabs.peloton2.kafka.producer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Reads the configuration from the file
 */
public class Configuration {
	private Properties prop;

	public Configuration(String path) throws IOException {
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
