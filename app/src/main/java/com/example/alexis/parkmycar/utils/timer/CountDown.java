package com.example.alexis.parkmycar.utils.timer;

public class CountDown extends CustomTimer
{
    private long startTime = 0;

    public CountDown() {
        super();
    }

    public void setStartTime(long timeSec) { this.startTime = timeSec; }

    public void addSecToStartTime(long timeSec) { this.startTime += timeSec; }

    public void addMinToStartTime(long timeMin) { this.startTime += timeMin * 60; }

    public void addHourToStartTime(long timeHour) { this.startTime += timeHour * 3600; }

    @Override
    protected void execute()
    {
        long currentSec = getSecondeByTimeSet();

        if (startTime <= currentSec)
            this.stop();
    }

    @Override
    public String getTime()
    {
        //TODO : Heures
        long sec = startTime - getSecondeByTimeSet();
        return CustomTimer.getHour(sec) + ":" + CustomTimer.getMinute(sec) + ":" + CustomTimer.getSeconde(sec);
    }
}
