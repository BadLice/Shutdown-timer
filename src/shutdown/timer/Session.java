/*
    shutdown:
    
    -l   : Logs off the current user, this is also the defualt. -m ComputerName takes precedence.

    -s : Shuts down the local computer.

    -r   : Reboots after shutdown.

    -a   : Aborts shutdown. Ignores other parameters, except -l and ComputerName. You can only use -a during the time-out period.

    -f   : Forces running applications to close.

    -m [ \\ ComputerName ] : Specifies the computer that you want to shut down.

    -t   xx   : Sets the timer for system shutdown in xx seconds. The default is 20 seconds.

    -c   " message "   : Specifies a message to be displayed in the Message area of the System Shutdown window. You can use a maximum of 127 characters. You must enclose the message in quotation marks.

    -d [ u ][ p ] : xx : yy   : Lists the reason code for the shutdown. The following table lists the different values. 
 */
package shutdown.timer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Session
{
    
    
    public  static void lock()
    {
        exec("Rundll32.exe User32.dll,LockWorkStation");
    }
    
    public  static void sleep()
    {
        exec("rundll32.exe powrprof.dll,SetSuspendState 0,1,0");
    }
    
    public  static void hibernate()
    {
        exec("shutdown -h");
    }
    
    public static void logoff()
    {
        exec("shutdown -l");
    }
    
    public static void reboot()
    {
        exec("shutdown -r");
    }
    
    public static void shutdown()
    {
        exec("shutdown -p");
    }
    
    public static void exec(String str)
    {
        try 
        {      
            Process p = Runtime.getRuntime().exec("cmd /c "+str);
            /*BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String s = null;
            while ((s = b.readLine()) != null) 
            {
                System.out.println(s);
            }*/
        } catch (IOException ex){ System.exit(404); }
    }
}