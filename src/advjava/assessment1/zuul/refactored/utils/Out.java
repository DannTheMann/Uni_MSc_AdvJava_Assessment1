/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advjava.assessment1.zuul.refactored.utils;

import advjava.assessment1.zuul.refactored.Main;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author dja33
 */
public final class Out {

    private static final DateFormat logTimeFormat = new SimpleDateFormat("HH:mm:ss");
    private boolean printDebugMessages = false; 
    private final String LOG_DIRECTORY;
    private final File log;
    private boolean canLog;
    private BufferedWriter writer;
    public static Out out;

    private Out(String logDir){ 
        
        File dir = new File(logDir);
        
        if(!dir.exists()){
            loglnErr("Directory '" + dir + "' did not exist. Creating it now...");
            dir.mkdir();
        }
        LOG_DIRECTORY = logDir + File.separator + "log_" + new SimpleDateFormat("dd-MM-yy").format(new Date()) + ".txt";
        log = new File(LOG_DIRECTORY);
        if (!log.exists()) {
            try {
                logln("Creating new log file: " + LOG_DIRECTORY);
                log.createNewFile(); 
            } catch (IOException ioe) {
                loglnErr("Logger could not create file to write out to: " + ioe.getMessage());
                canLog = false;
                return;
            }
        }
            try {
                writer = new BufferedWriter(new FileWriter(log, true));
                writer.write(" ___________________________________________________________" + System.lineSeparator());
                writer.write("|                                                           |" + System.lineSeparator());
                writer.write("| - - Start - - - - - - - - - - - - - - - - - - - - - - - - |" + System.lineSeparator());
                writer.write("|___________________________________________________________|" + System.lineSeparator());
                writer.newLine();
            } catch (IOException ioe){
                loglnErr("Logger could not open file to write out to: " + ioe.getMessage());
                canLog = false;
                return;
            }
        
        canLog = true;
    }

    public static boolean createLogger(String dir){
        if(out != null){
            Main.game.getInterface().printlnErr("Can't create new logger, one already exists. Must be closed first.");
            return false;
        }else{
            out = new Out(dir);
            out.logln("Created new logger. '" + out.getAbsolutePath() + "'");
            return true;
        }
    }
    
    public static void close() throws IOException{
        out.logln("Closing logger.");
        if(out != null && out.writer != null){
            out.logln("Closing writer for logger.");
            out.writer.close();
        }
        if(out != null){
            out = null;
        }
        // ...
    }
    
    private static final String PREFIX = "[LOG] ";
    
    public void logln(Object obj) {
        if (isPrintingDebugMessages()) {
            if(Main.game != null){
                Main.game.getInterface().println(PREFIX + obj);
            }else{
                System.out.println(PREFIX + obj);
            }
        }
        recordToLog(obj + System.lineSeparator());
    }

    public void logln() {
        if (isPrintingDebugMessages()) {
            if(Main.game != null){
                Main.game.getInterface().println();
            }else{
                System.out.println();
            }
        }
        recordToLog(System.lineSeparator());
    }

    public void recordToLog(String str) {
        
        if (canLog) {
            
            try{
                writer.write(String.format("[%s] %s", logTimeFormat.format(new Date()), str)); 
            }catch(IOException ioe){ 
                canLog = false;
                loglnErr("Logger could not write to file: "  + ioe.getMessage());
            }
            
        }

    }

    public boolean isPrintingDebugMessages() {
        return printDebugMessages;
    }

    public void setPrintingDebugMessages(boolean print) {
        printDebugMessages = print;
    }

    private static final String ERROR_PREFIX = "[ERROR] ";
    
    public void loglnErr(Object obj) {
        if (isPrintingDebugMessages()) {
            if(Main.game != null){
                Main.game.getInterface().printlnErr(obj);
            }else{
                System.out.println(ERROR_PREFIX + obj.toString());
            }
        }
        recordToLog(ERROR_PREFIX + obj.toString());
    }
    
    public String getDirectory(){
       return log.getParent();
    }

    private String getAbsolutePath() {
        return log.getAbsolutePath();
    }

}
