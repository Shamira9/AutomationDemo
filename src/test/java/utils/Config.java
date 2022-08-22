package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
	public static String readKey(String key) {
		String value = "";
		//.‪/src/test/resources/configuration.properties‬"
		try (InputStream input = new FileInputStream("C:\\automation\\configuration.properties‬‬")) {
			//C:\Users\נוריאל\eclipse-workspace\sauceDemoProject\src\test\resources
			         
            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            value = prop.getProperty(key);
            System.out.println(key + " = " + value);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
		return value;
	}
	
	public void stam() {
		
	}

}
