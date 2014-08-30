package com.devadvance.rootinspector;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import android.util.Log;

public class ExecShell {
    public ExecShell() {
    	
    }
    
    public static enum SHELL_CMD {
        check_su_binary(new String[] { "/system/xbin/which", "su" }), check_daemon_su(new String[] {"ps", "daemonsu"}), run_su(new String[] {"su"}), check_su(new String[] {"ps", "|", "grep", "su"});

        String[] command;

        SHELL_CMD(String[] command) {
            this.command = command;
        }
    }

    public ArrayList<String> executeCommand(SHELL_CMD shellCmd) {
        String line = null;
        ArrayList<String> fullResponse = new ArrayList<String>();
        Process localProcess = null;
        try {
            localProcess = Runtime.getRuntime().exec(shellCmd.command);
        } catch (Exception e) {
            return null;
        }
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                localProcess.getOutputStream()));
        BufferedReader in = new BufferedReader(new InputStreamReader(
                localProcess.getInputStream()));
        try {
            while ((line = in.readLine()) != null) {
                //Log.d("testAPI", "--> Line received: " + line);
                fullResponse.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("RootInspector", "--> Full response was: " + fullResponse);
        return fullResponse;
    }
    
    public boolean executeCommandSU(SHELL_CMD shellCmd) {
        String line = null;
        ArrayList<String> fullResponse = new ArrayList<String>();
        Process localProcess = null;
        try {
            localProcess = Runtime.getRuntime().exec(shellCmd.command);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
