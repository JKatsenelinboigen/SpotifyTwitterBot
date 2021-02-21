import java.util.TimerTask;
import java.util.Timer;
import java.util.Calendar;
import java.util.Date;


public class ScheduledTask extends TimerTask{
    public void run(){
        
        //send a formatted tweet with todays date and the most popular songs from spotify's daily charts
        SpotifyTwitterBot.generateTweetsFromString(new Date() + "\n" + PullSpotifyData.getTweetAsString());

        //schedule another tweet for tomorrow
        scheduleTask(1);
    }
    
    //calls the run method at 12:00 (noon) a certain number of days from today. input of 0 will run it same day
    public static void scheduleTask(int daysFromToday){
        Timer timer = new Timer();
        ScheduledTask schedule = new ScheduledTask();
        

        //gets the time to tweet tomorrow, at 12pm. takes todays date, adds one, and sets the time to 12:00
        Calendar scheduledDay =  Calendar.getInstance();
        scheduledDay.add(Calendar.DAY_OF_YEAR, daysFromToday);
        scheduledDay.set(Calendar.HOUR_OF_DAY, 12);
        scheduledDay.set(Calendar.MINUTE, 00);



        //executes repeatedtly with buffer
        timer.schedule(schedule, scheduledDay.getTime()); 

    }
    public static void main(String args[]){
        
        //time.schedule(schedule,0,1000);
        scheduleTask(0);
        
    }        
}