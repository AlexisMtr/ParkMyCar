package com.example.alexis.parkmycar.utils.timer;

import android.os.Looper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public abstract class CustomTimer
{
    //public List<CustomTimer> timers = new ArrayList<CustomTimer>();

    private CustomTimer instance = null;
    private Timer timer = null;
    private boolean isStoped = true;
    private TimeChoice timeSet = TimeChoice.SECONDE;
    private long currentTime = 0;
    private boolean triggerStopEvent = true;

    protected Map<String, Long> stepMap;

    private ITickListener tickListener = null;
    private IStepListener stepListener = null;
    private IStopListener stopListener = null;

    //private Runnable delegate = null;

    public enum TimeChoice
    {
        MINUTE(60000)
        , SECONDE(1000)
        , MILLISECONDE(1);

        public int tick;
        TimeChoice(int tick)
        {
            this.tick = tick;
        }
    }

    protected CustomTimer()
    {
        this.instance = this;
        //timers.add(this.instance);
        this.stepMap = new HashMap<String, Long>();
    }

    /**
     * Permet de d�marrer le chrono
     */
    public void start()
    {
        start(true);
    }

    /**
     * Permet de d�marrer le chrono
     * @param restart le chrono doit recommencer � z�ro ? (true|false)
     */
    public void start(boolean restart)
    {
        this.timer = new Timer(false);

        if (restart)
            this.currentTime = 0;

        this.isStoped = false;

        this.timer.schedule(new TimerTask() {

            @Override
            public void run() {

                currentTime++;

                execute();

                //Setup tickListener
                if (tickListener != null)
                    tickListener.onTick(instance);

                /*if(delegate != null)
                    delegate.run();*/

                if(stepListener != null)
                {
                    if(stepMap.containsValue(currentTime))
                    {
                        for (Map.Entry<String, Long> entry : stepMap.entrySet()) {
                            if (currentTime == entry.getValue()) {
                                stepListener.onStep(instance, entry.getKey());
                                break;
                            }
                        }
                    }
                }

                if (isStoped())
                {
                    if(stopListener != null && triggerStopEvent == true)
                        stopListener.onStop(instance);

                    cancel();
                    //RESET ?
                }

            }
        }, 0, this.timeSet.tick);
    }

    /**
     * Permet de stoper le timer
     */
    public void stop() { this.stop(true); }

    public void stop(boolean triggerStopEvent)
    {
        this.isStoped = true;
        this.triggerStopEvent = triggerStopEvent;
    }

    /**
     * Permet d'executer la t�che de l'objet
     */
    protected abstract void execute();

    /**
     * GET / SET
     */

    protected Timer getTimer() { return this.timer; }

    public boolean isStoped() { return this.isStoped; }


    public void setTimeSet(TimeChoice timeSet) { this.timeSet = timeSet; }

    public long getCurrentTime() { return this.currentTime; }

    //public void setDelegate(Runnable d) { this.delegate = d; }

    public void setOnTickListener(ITickListener listener) { this.tickListener = listener; }

    public void setOnStepListener(IStepListener listener) { this.stepListener = listener; }

    public void setOnStopListener(IStopListener listener) { this.stopListener = listener; }

    public void addStepSeconde(String stepName, long seconde) throws Exception
    {
        if(this.stepMap.containsKey(stepName))
            throw new Exception("Step already extist");

        this.stepMap.put(stepName, seconde);
    }

    public void addStepMinute(String stepName, long minute) throws Exception
    {
        addStepSeconde(stepName, minute * 60);
    }

    public void addStepHour(String stepName, long hour) throws Exception
    {
        addStepSeconde(stepName, hour * 3600);
    }

    public void removeStep(String stepName)
    {
        if(!this.stepMap.containsKey(stepName))
            return;

        this.stepMap.remove(stepName);
    }


    protected long getSecondeByTimeSet()
    {
        long seconde = -1;
        switch (this.timeSet)
        {
            case MILLISECONDE:
                seconde = currentTime / 1000;
                break;
            case MINUTE:
                seconde = currentTime * 60;
                break;
            case SECONDE:
                seconde = currentTime;
                break;
            default:
                seconde = currentTime;
                break;
        }
        return seconde;
    }

    /**
     * Permet de r�cup�rer le nombre d'heure �coul�e depuis le start (ex : 01 | 10 | 42)
     * @return le nombre d'heure au format string
     */
    protected static String getHour(long timeSec)
    {
        long hour = timeSec / 3600;

        String h = "";

        if (hour < 10)
            h += "0";

        return h + hour;

    }

    /**
     * Permet de r�cup�rer le nombre de minute �coul�e depuis le start (ex : 01 | 10 | 42)
     * @return le nombre de minute au format string
     */
    protected static String getMinute(long timeSec)
    {
        long hour = timeSec /3600;
        long minute = (timeSec - (hour * 3600)) / 60;

        String min = "";

        if (minute < 10)
            min += "0";

        return min + minute;

    }

    /**
     * Permet de r�cup�rer le nombre de seconde �coul�e depuis le start (ex : 01 | 10 | 42)
     * @return le nombre de seconde au format string
     */
    protected static String getSeconde(long timeSec)
    {
        long minute = timeSec / 60;
        long seconde = timeSec - (minute * 60);

        String sec = "";

        if (seconde < 10)
            sec += "0";

        return sec + seconde;

    }

    /**
     * Permet de r�cup�rer le temps �coul� depuis le start au format MM:SS (ex : 00:00:06 | 00:01:42 | 01:12:21)
     * @return le temps �coul� au format string
     */
    public String getHeureMinuteSecondeFormat()
    {
        return CustomTimer.getHour(this.getCurrentTime()) + ":" + CustomTimer.getMinute(this.getCurrentTime()) + ":" + CustomTimer.getSeconde(this.getCurrentTime());
    }

    public abstract String getTime();
}
