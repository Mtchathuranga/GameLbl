package utility;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class PropertyHandler {
	/** Property file location */
	private static String FILE_PATH = "config.properties";

	/** Holds property file instance */
	private static Properties instance = null;

	public Properties getInstance() {
		if (instance == null) {
			// get latest property instance
			instance = this.getProperties();
		}
		return instance;
	}

	/**
	 * load property file
	 * 
	 * @return
	 */
	private Properties getProperties() {
		Properties properties = new Properties();
		try {
			File file = new File(FILE_PATH);
			properties.load(new FileReader(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return properties;
	}

	/**
	 * read specified property from property file
	 * 
	 * @param propName
	 * @return
	 */
	public String getProperty(String propName) {
		return getInstance().getProperty(propName);
	}

	/**
	 * write new data into property file
	 * 
	 * @param prop
	 * @param val
	 */
	public void setProperty(String prop, String val) {
		instance.setProperty(prop, val);
		try {
			FileWriter fileWriter = new FileWriter(instance.toString());
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
