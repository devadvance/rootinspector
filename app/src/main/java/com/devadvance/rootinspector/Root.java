package com.devadvance.rootinspector;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.devadvance.rootinspector.ExecShell.SHELL_CMD;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

public class Root {
	
	static {  
	    System.loadLibrary("native2");  
	}
//	
//	
//	
//	private native void helloLog(String logThis);
	private native boolean checkfopen(String filepath);
	private native boolean runsu(String command);
	private native boolean statfile(String filepath);
	private native boolean runls(String filepath, String filename, boolean exactMatch);
	private native boolean runpmlist(String packageName, boolean exactMatch);
	private native boolean checkifstream(String filepath);
	
	PackageManager pm;
	private ActivityManager manager;

    private static String LOG_TAG = Root.class.getName();
    
    public Root() {
    	
    }
    
    public Root(PackageManager _pm, ActivityManager _manager) {
    	this();
    	pm = _pm;
    	manager = _manager;
    }

    public boolean checkRootMethod0() {
    	Log.d(Main.TAG, "check1");
        String buildTags = android.os.Build.TAGS;
        return buildTags != null && buildTags.contains("test-keys");
    }

    public boolean checkRootMethod1() {
    	Log.d(Main.TAG, "check2");
        try {
            File file = new File("/system/app/Superuser.apk");
            return file.exists();
        } catch (Exception e) {
        	return false;
        }
    }

    public boolean checkRootMethod2() {
    	Log.d(Main.TAG, "check3");
    	ArrayList<String> result = new ExecShell().executeCommand(SHELL_CMD.check_su_binary);
    	if (result != null) {
        	return true;
    	}
        return false;
    }
    
    public boolean checkRootMethod3() {
    	Log.d(Main.TAG, "check4");
        try {
            File file = new File("/system/xbin/su");
            return file.exists();
        } catch (Exception e) {
        	return false;
        }
    }
    
    public boolean checkRootMethod4() {
    	Log.d(Main.TAG, "check5");
        try {
            File file = new File("/system/bin/su");
            return file.exists();
        } catch (Exception e) {
        	return false;
        }
    }
    
    public boolean checkRootMethod5() {
    	Log.d(Main.TAG, "check6");
		List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
		
		for (ApplicationInfo packageInfo : packages) {
			if (packageInfo.packageName.contains("supersu") || packageInfo.packageName.contains("superuser")) {
				Log.d(Main.TAG, "Installed package :" + packageInfo.packageName);
			    Log.d(Main.TAG, "Launch Activity :" + pm.getLaunchIntentForPackage(packageInfo.packageName)); 
				return true;
			}
		}
		
		return false;
    }
    
    
    public boolean checkRootMethod6() {
    	Log.d(Main.TAG, "check7");
    	boolean returnValue = false;
    	// Get currently running application processes
    	List<ActivityManager.RunningAppProcessInfo> list = manager.getRunningAppProcesses();
    	if(list != null){
    		String tempName;
    		for(int i=0;i<list.size();++i){
    			tempName = list.get(i).processName;
    			Log.d(Main.TAG, "processName: " + tempName);
    			if(tempName.contains("supersu") || tempName.contains("superuser")){
    				Log.d(Main.TAG, "found one!");
    				returnValue = true;
    			}
    		}
    	}
    	return returnValue;
    }
    
    public boolean checkRootMethod7() {
    	Log.d(Main.TAG, "check8");
    	ArrayList<String> result = new ExecShell().executeCommand(SHELL_CMD.check_daemon_su);
    	if (result != null) {
    		for (String tempString : result) {
    			if (tempString.contains("daemonsu"))
            		return true;
    		}
    	}
        return false;
    }
    
    public boolean checkRootMethod8() {
    	Log.d(Main.TAG, "check9");
    	boolean result = new ExecShell().executeCommandSU(SHELL_CMD.run_su);
    	return result;
    }
    
    public boolean checkRootMethod9() {
    	Log.d(Main.TAG, "check10");
    	ArrayList<String> result = new ExecShell().executeCommand(SHELL_CMD.check_su);
    	if (result != null) {
    		for (String tempString : result) {
    			if (tempString.endsWith("su"))
            		return true;
    		}
    	}
        return false;
    }
    
    public boolean checkRootMethod10() {
    	Log.d(Main.TAG, "check11");
    	boolean returnValue = false;
    	// Get currently running application processes
    	List<RunningServiceInfo> list = manager.getRunningServices(300);
    	if(list != null){
    		String tempName;
    		for(int i=0;i<list.size();++i){
    			tempName = list.get(i).process;
    			Log.d(Main.TAG, "process: " + tempName);
    			if(tempName.contains("supersu") || tempName.contains("superuser")){
    				Log.d(Main.TAG, "found one!");
    				returnValue = true;
    			}
    		}
    	}
    	return returnValue;
    }
    
    public boolean checkRootMethod11() {
    	Log.d(Main.TAG, "check12");
    	boolean returnValue = false;
    	// Get currently running application processes
    	List<RunningTaskInfo> list = manager.getRunningTasks(300);
    	if(list != null){
    		String tempName;
    		for(int i=0;i<list.size();++i){
    			tempName = list.get(i).baseActivity.flattenToString();
    			Log.d(Main.TAG, "baseActivity: " + tempName);
    			if(tempName.contains("supersu") || tempName.contains("superuser")){
    				Log.d(Main.TAG, "found one!");
    				returnValue = true;
    			}
    		}
    	}
    	return returnValue;
    }
    
    public boolean checkRootMethod12() {
    	Log.d(Main.TAG, "check13");
    	boolean returnValue = false;
    	// Get currently running application processes
    	 ProcessBuilder cmd;
         String result = null;

         try{
          String[] args = {"su"};
          cmd = new ProcessBuilder(args);

          Process process = cmd.start();
          Log.d(Main.TAG, "Just after ProcessBuilder.start()");
          result = "";
          InputStream in = process.getInputStream();
          Log.d(Main.TAG, "process.getInputStream()");
          returnValue = true;
          in.close();
          } catch(IOException ex){
        	  Log.d(Main.TAG, "IOException happened");
          }
    	
    	return returnValue;
    }
    
    public boolean checkRootMethod13() {
    	Log.d(Main.TAG, "check14");
    	InputStream is = null;
        ByteArrayOutputStream baos = null;
        List<String> commands = new ArrayList<String>();
        commands.add("sh");
        commands.add("-c");
        commands.add("ls -l /system/xbin/");
        ProcessBuilder pb = new ProcessBuilder(commands);
        try {
            Process prs = pb.start();
            prs.waitFor();
            is = prs.getInputStream();
            byte[] b = new byte[4096];
            int size = 0;
            baos = new ByteArrayOutputStream();
            while((size = is.read(b)) != -1){
                baos.write(b, 0, size);
            }
            String out = new String(baos.toByteArray());
            Log.d(Main.TAG, "ProcessBuilder1: " + out);
            if (out.contains("daemonsu"))
            	return true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
            try {
                if(is != null) is.close();
                if(baos != null) baos.close();
            } catch (Exception ex){}
        }
        return false;
    }
    
    public boolean checkRootMethod14() {
    	Log.d(Main.TAG, "check15");
    	InputStream is = null;
        ByteArrayOutputStream baos = null;
        List<String> commands = new ArrayList<String>();
        commands.add("ls");
        commands.add("-l");
        commands.add("/system/xbin/");
        ProcessBuilder pb = new ProcessBuilder(commands);
        try {
            Process prs = pb.start();
            is = prs.getInputStream();
            byte[] b = new byte[4096];
            int size = 0;
            baos = new ByteArrayOutputStream();
            while((size = is.read(b)) != -1){
                baos.write(b, 0, size);
            }
            String out = new String(baos.toByteArray());
            Log.d(Main.TAG, "ProcessBuilder2: " + out);
            if (out.contains("daemonsu"))
            	return true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally{
            try {
                if(is != null) is.close();
                if(baos != null) baos.close();
            } catch (Exception ex){}
        }
        return false;
    }
    
    
    
    
    public boolean checkRootMethodNative0() {
    	Log.d(Main.TAG, "checkNative0");
    	boolean returnValue = false;
    	returnValue = checkfopen("/system/xbin/su");
    	
    	
    	return returnValue;
    }
    
    public boolean checkRootMethodNative1() {
    	Log.d(Main.TAG, "checkNative1");
    	boolean returnValue = false;
    	returnValue = runsu("/system/xbin/su");
    	
    	
    	return returnValue;
    }
    
    public boolean checkRootMethodNative2() {
    	Log.d(Main.TAG, "checkNative2");
    	boolean returnValue = false;
    	returnValue = statfile("/system/xbin/daemonsu");
    	
    	return returnValue;
    }
    
    public boolean checkRootMethodNative3() {
    	Log.d(Main.TAG, "checkNative3");
    	boolean returnValue = false;
    	returnValue = runls("/system/xbin/", "su", true);
    	
    	return returnValue;
    }
    
    public boolean checkRootMethodNative4() {
    	Log.d(Main.TAG, "checkNative4");
    	boolean returnValue = false;
    	returnValue = runls("/system/bin/", "su", true);
    	
    	return returnValue;
    }
    
    public boolean checkRootMethodNative5() {
    	Log.d(Main.TAG, "checkNative5");
    	boolean returnValue = false;
    	returnValue = runls("/system/xbin/", "daemonsu", true);
    	
    	return returnValue;
    }
    
    public boolean checkRootMethodNative6() {
    	Log.d(Main.TAG, "checkNative6");
    	boolean returnValue = false;
    	returnValue = runls("/system/app/", "Superuser.apk", true);
    	
    	return returnValue;
    }
    
    public boolean checkRootMethodNative7() {
    	Log.d(Main.TAG, "checkNative7");
    	boolean returnValue = false;
    	returnValue = runls("/data/app/", "eu.chainfire.supersu", false);
    	
    	return returnValue;
    }
    
    public boolean checkRootMethodNative8() {
    	Log.d(Main.TAG, "checkNative8");
    	boolean returnValue = false;
    	returnValue = runls("/data/app/", "com.noshufou.android.su", false);
    	
    	return returnValue;
    }
    
    public boolean checkRootMethodNative9() {
    	Log.d(Main.TAG, "checkNative9");
    	boolean returnValue = false;
    	returnValue = runpmlist("eu.chainfire.supersu", true);
    	
    	return returnValue;
    }
    
    public boolean checkRootMethodNative10() {
    	Log.d(Main.TAG, "checkNative10");
    	boolean returnValue = false;
    	returnValue = runpmlist("com.noshufou.android.su", true);
    	
    	return returnValue;
    }
    
    public boolean checkRootMethodNative11() {
    	Log.d(Main.TAG, "checkNative11");
    	boolean returnValue = false;
    	returnValue = runpmlist("rootkeeper", false);
    	if (returnValue == false) {
    		returnValue = runpmlist("hidemyroot", false);
    	}
    	
    	return returnValue;
    }
    
    public boolean checkRootMethodNative12() {
    	Log.d(Main.TAG, "checkNative12");
    	boolean returnValue = false;
    	returnValue = checkifstream("/system/xbin/su");
    	
    	
    	return returnValue;
    }
    
    
}   
