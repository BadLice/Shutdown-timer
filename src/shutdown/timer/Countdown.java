
package shutdown.timer;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Countdown {
   
    static ArrayList<Long> interval= new ArrayList();
    Timer t;
    int index;
    static int temp;
    boolean cancelled=true;
    TimerTask tt;
    
    public Countdown()
    {
       index=interval.size();
       interval.add(0l);
    }
    
    public void start(long interval)
    {
       setInterval(interval);
       temp = index;
       t = new Timer();
       tt = new TimerTask()
       { 
           int i = temp;
           @Override
           public synchronized void run()
           {
              Countdown.interval.set(i,Countdown.interval.get(i)-1);
              //when countdowm is finished
              if(Countdown.interval.get(index)==0)
              {
                  ShutdownTimer.exec();
              }
           }
       };
       
       t.scheduleAtFixedRate(tt, 0, 1000);
       cancelled=false;
    }
    
    public void stop()
    {
        if(!cancelled)
        {
            t.cancel();
            tt.cancel();
            cancelled=true;
           
        }
    }

    public long getInterval() {
        return interval.get(index);
    }

    public void setInterval(long interval) 
    {
        this.interval.set(index,interval);
    }
       
    public static int calcInterval(String h, String m, String s)
    {
        return (intval(s)+(intval(m)*60)+(intval(h)*60*60));
    }
    
    public static int intval(String x)
    {
        return Integer.parseInt(x);
    }
    
    public int getHours()
    {
        return (int) Math.floor(interval.get(index)/60/60);
    }
    
    public int getMinutes()
    {
        return (int) (interval.get(index)/60)-(getHours()*60);
    }
     
    public int getSeconds()
    {
       return (int) (interval.get(index).longValue())-(getMinutes()*60)-(getHours()*60*60);
    }
}
/*
3600
*/