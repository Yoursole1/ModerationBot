package main.utils;

import main.main;

import java.io.File;
import java.net.URISyntaxException;

public class Utils {

    public static String getPath() throws URISyntaxException {
        return new File(main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
    }

    public static String getDirPath() throws URISyntaxException {
        if(getOperatingSystem().toLowerCase().contains("window")){
            String[] pathSections = getPath().split(String.valueOf("\\\\".toCharArray()[0]));
            String DirPath = new String();
            for(String section : pathSections){
                if(section.contains(".jar")){
                    break;
                }
                DirPath+="/"+section;
            }
            return DirPath.replace("//","/");
        }else{
            String[] pathSections = getPath().split("/");
            String DirPath = new String();
            for(String section : pathSections){
                if(section.contains(".jar")){
                    break;
                }
                DirPath+="/"+section;
            }
            return DirPath.replace("//","/");
        }

    }

    public static String getOperatingSystem() {
        return System.getProperty("os.name");
    }
}
