package me.takimitsuha.tomatoalarmclock.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Taki on 2017/2/7.
 */
@Entity(
        nameInDb = "Tomato_Alarm_Clock"
)
public class TomatoAlarmClock {

    @Id
    private Long id;

    @Property(nameInDb = "start_time")
    private Integer startTime;

    @Property(nameInDb = "end_time")
    private Integer endTime;

    @Property(nameInDb = "pause_time")
    private Integer pauseTime;

    private Integer length;

    private boolean finish;

    @Property(nameInDb = "create_time")
    private Integer createTime;

    @Property(nameInDb = "task_type")
    private int taskType;

    @Generated(hash = 412579590)
    public TomatoAlarmClock(Long id, Integer startTime, Integer endTime,
            Integer pauseTime, Integer length, boolean finish, Integer createTime,
            int taskType) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.pauseTime = pauseTime;
        this.length = length;
        this.finish = finish;
        this.createTime = createTime;
        this.taskType = taskType;
    }

    @Generated(hash = 567983189)
    public TomatoAlarmClock() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public Integer getPauseTime() {
        return pauseTime;
    }

    public void setPauseTime(Integer pauseTime) {
        this.pauseTime = pauseTime;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public boolean getFinish() {
        return this.finish;
    }
}
