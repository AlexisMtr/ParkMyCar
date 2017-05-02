package com.example.alexis.parkmycar.utils.timer;

import java.util.List;

public class CountDown extends CustomTimer
{
    private long startTime = 0;
    private IFinishListener finishListener;

    public CountDown() {
        super();
    }

    public void setStartTime(long timeSec) { this.startTime = timeSec; }

    public void addSecToStartTime(long timeSec) { this.startTime += timeSec; }

    public void addMinToStartTime(long timeMin) { this.startTime += timeMin * 60; }

    public void addHourToStartTime(long timeHour) { this.startTime += timeHour * 3600; }

    public long remainingTime() { return this.startTime - getSecondeByTimeSet(); }

    @Override
    protected void execute()
    {
        if (this.remainingTime() <= 0)
        {
            if(this.finishListener != null)
                finishListener.onFinish(this);
            this.stop();
        }
    }

    @Override
    public String getTime()
    {
        //TODO : Heures
        long sec = this.remainingTime();
        return CustomTimer.getHour(sec) + ":" + CustomTimer.getMinute(sec) + ":" + CustomTimer.getSeconde(sec);
    }

    @Override
    public void addStepSeconde(String stepName, long seconde) throws Exception
    {
        if(this.stepMap.containsKey(stepName))
            throw new Exception("Step already extist");

        this.stepMap.put(stepName, this.startTime - seconde);
    }

    public void setOnFinishListener(IFinishListener listener) { this.finishListener = listener; }
}
