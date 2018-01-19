package logback;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import ch.qos.logback.core.FileAppender;

public class RequestRollingFileAppender extends FileAppender  
{  
    /** 
     * The date pattern. By default, the pattern is set to "'.'yyyy-MM-dd" 
     * meaning daily rollover. 
     */  
    //private static final String DATEPATTERN = "HHmm"; 
    private static final String FOLDER_DATEPATTERN = "yyyyMMdd"; 
    
    private  String orgFilename = null;

/** 
     * 间隔时间，单位：分钟 
     */  
    private int intervalTime = 1;  
  
    //SimpleDateFormat sdf = new SimpleDateFormat(DATEPATTERN);  
    
    SimpleDateFormat folder = new SimpleDateFormat(FOLDER_DATEPATTERN);  
    
    /** 
     * @return the intervalTime 
     */  
    public int getIntervalTime()  
    {  
        return intervalTime;  
    }  
  
    /** 
     * @param intervalTime 
     *            the intervalTime to set 
     */  
    public void setIntervalTime(int intervalTime)  
    {  
        this.intervalTime = intervalTime;  
    }  
    
    private boolean started = false ;

@Override
protected void subAppend(Object event){

if ( !started ) {
started = true ;
init();
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(  
                new LogTimerTask(), 1, 1000,  TimeUnit.MILLISECONDS); 
}

start();
super.subAppend(event);

}

private void init(){
    	Date now = new Date();
Calendar calender = Calendar.getInstance();
calender.setTime(now);
int hourMinutes = calender.get(Calendar.HOUR_OF_DAY) * 60 + calender.get(Calendar.MINUTE);
String fileStamp = (hourMinutes/intervalTime)  + "";
//LoggerUtil.trace(this.getClass(), "renameFile", "fileName=" + fileName, "");
     	try{
     	orgFilename = fileName;
     	String computerName = "_jyy" ;
     	StringBuilder newFileName = new StringBuilder(orgFilename.replace("yyyyMMdd", folder.format(now)).replace("HHmm", fileStamp));
    	newFileName.insert(newFileName.lastIndexOf("."), computerName);	
     	fileName = newFileName.toString();
     	String dict = fileName.substring(0, fileName.lastIndexOf("/"));
     	File file = new File(dict);
     	file.mkdirs();
     	}catch(Exception e){
     	System.out.println(e);
     	}
    }

class LogTimerTask implements Runnable  
    {  
        @Override  
        public void run()   {  
            renameFile();
            //File file = new File(fileName); 
          //  try  
           // {  
                setFile(fileName);  
           // }  
           // catch (IOException e)  
           // {  
                //errorHandler.error("setFile(" + fileName  + ", true) call failed.");  
           // }  
        }

private void renameFile() {
Date now = new Date();
Calendar calender = Calendar.getInstance();
calender.setTime(now);
int hourMinutes = calender.get(Calendar.HOUR_OF_DAY) * 60 + calender.get(Calendar.MINUTE);
String computerName = "_jyy" ;
String fileStamp = (hourMinutes/intervalTime)  + "";

        	if(orgFilename!=null){
        	StringBuilder newFileName = new StringBuilder(orgFilename.replace("yyyyMMdd", folder.format(now)).replace("HHmm", fileStamp));
        	newFileName.insert(newFileName.lastIndexOf("."), computerName);
        	System.out.println(orgFilename+"=>"+fileName+"=>"+newFileName);
        	if(!newFileName.toString().equals(fileName)){
        	//closeFile();
        	fileName = newFileName.toString();
        	}
        	}
}  
    }  

}
