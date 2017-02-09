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
    private Long startTime;

    @Property(nameInDb = "end_time")
    private Long endTime;

    @Property(nameInDb = "pause_time")
    private Long pauseTime;

    private Integer length;

    private Integer finish;

    @Property(nameInDb = "create_time")
    private Long createTime;

    @Property(nameInDb = "task_type")
    private int taskType;

    @Generated(hash = 1337757456)
    public TomatoAlarmClock(Long id, Long startTime, Long endTime, Long pauseTime,
            Integer length, Integer finish, Long createTime, int taskType) {
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

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Long getPauseTime() {
        return pauseTime;
    }

    public void setPauseTime(Long pauseTime) {
        this.pauseTime = pauseTime;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getFinish() {
        return finish;
    }

    public void setFinish(Integer finish) {
        this.finish = finish;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    @Override
    public String toString() {
        return "TomatoAlarmClock{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", pauseTime=" + pauseTime +
                ", length=" + length +
                ", finish=" + finish +
                ", createTime=" + createTime +
                ", taskType=" + taskType +
                '}';
    }
}
