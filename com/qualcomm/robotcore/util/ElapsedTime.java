package com.qualcomm.robotcore.util;

import java.util.concurrent.TimeUnit;

public class ElapsedTime {
    public enum Resolution {
        SECONDS,
        MILLISECONDS
    }
    protected long nsNow() {
        return System.nanoTime();
    }
    public static final long SECOND_IN_NANO = 1000000000;
    public static final long MILLIS_IN_NANO = 1000000;
    protected long nsStartTime;
    protected final double resolution;
    public ElapsedTime() {
        reset();
        this.resolution = SECOND_IN_NANO;
    }
    public ElapsedTime(long startTime) {
        this.nsStartTime = startTime;
        this.resolution = SECOND_IN_NANO;
    }
    public ElapsedTime(Resolution resolution) {
        reset();
        switch (resolution) {
            case SECONDS:
            default:
                this.resolution = SECOND_IN_NANO;
                break;
            case MILLISECONDS:
                this.resolution = MILLIS_IN_NANO;
                break;
        }
    }
    public long now(TimeUnit unit) {
        return unit.convert(nsNow(), TimeUnit.NANOSECONDS);
    }
    public void reset() {
        nsStartTime = nsNow();
    }
    public double startTime() {
        return nsStartTime / resolution;
    }
    public long startTimeNanoseconds() {
        return this.nsStartTime;
    }
    public double time() {
        return (nsNow() - nsStartTime) / resolution;
    }
    public long time(TimeUnit unit) {
        return unit.convert(nanoseconds(), TimeUnit.NANOSECONDS);
    }
    public double seconds() {
        return nanoseconds() / ((double)(SECOND_IN_NANO));
    }
    public double milliseconds() {
        return seconds() * 1000;
    }
    public long nanoseconds() {
        return (nsNow() - nsStartTime);
    }
    public Resolution getResolution() {
        if (this.resolution == MILLIS_IN_NANO)
            return Resolution.MILLISECONDS;
        else
            return Resolution.SECONDS;
    }
    private String resolutionStr() {
        if (resolution == SECOND_IN_NANO) {
            return "seconds";
        } else if (resolution == MILLIS_IN_NANO) {
            return "milliseconds";
        } else {
            return "unknown units";
        }
    }
    @Override
    public String toString() {
        return String.format("%1.4f %s", time(), resolutionStr());
    }
}