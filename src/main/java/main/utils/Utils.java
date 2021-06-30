package main.utils;

import main.main;

import java.io.File;
import java.net.URISyntaxException;
import java.util.regex.Pattern;

public class Utils {

    public static String getPath() throws URISyntaxException {
        return new File(main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
    }

    public static String getDirPath() throws URISyntaxException {
        if(getOperatingSystem().toLowerCase().contains("windows 10")){
            System.out.println("\033[0;31m" + "ERROR: Starting on Operating System Mode: "+getOperatingSystem());
            System.out.print("\033[0m");
            String[] pathSections = getPath().split(Pattern.quote("\\"));
            String DirPath = new String();
            for(String section : pathSections){
                if(section.contains(".jar")){
                    break;
                }
                DirPath+="/"+section;
            }
            return DirPath.replace("//","/");
        }else if(getOperatingSystem().toLowerCase().contains("mac os x")){
            System.out.println("\033[0;32m" + "Starting on Operating System Mode: "+getOperatingSystem());
            System.out.print("\033[0m");
            String[] pathSections = getPath().split("/");
            String DirPath = new String();
            for(String section : pathSections){
                if(section.contains(".jar")){
                    break;
                }
                DirPath+="/"+section;
            }
            return DirPath.replace("//","/");
        }else{
            System.out.println("\033[0;31m" + "ERROR: Incompatible Operating System: "+getOperatingSystem());
            System.out.print("\033[0m");
            System.out.println("Stopped Bot Execution");
            System.exit(1);
        }
        return null;

    }

    public static String getOperatingSystem() {
        return System.getProperty("os.name");
    }
}
