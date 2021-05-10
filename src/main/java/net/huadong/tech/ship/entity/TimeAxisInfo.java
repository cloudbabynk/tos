package net.huadong.tech.ship.entity;

import java.util.List;
import java.util.Map;

public class TimeAxisInfo {
    private List<Map<String, Object>> times;
    private long intervalMills;

    public List<Map<String, Object>> getTimes() {
        return times;
    }

    public void setTimes(List<Map<String, Object>> times) {
        this.times = times;
    }

    public long getIntervalMills() {
        return intervalMills;
    }

    public void setIntervalMills(long intervalMills) {
        this.intervalMills = intervalMills;
    }
}
