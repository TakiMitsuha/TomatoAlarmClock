package me.takimitsuha.tomatoalarmclock.db;

import android.content.Context;

import java.util.List;

import me.takimitsuha.tomatoalarmclock.entity.TomatoAlarmClock;

/**
 * Created by Taki on 2017/2/7.
 */
public class DBUtil {

    private DBManager mDBManager;

    public DBUtil(Context context) {
        mDBManager = DBManager.getInstance();
        mDBManager.init(context);
    }

    /**
     * 完成对数据库表的插入操作-->并且会检测数据库是否存在,不存在自己创建,
     */
    public boolean insert(TomatoAlarmClock tomatoAlarmClock) {
        boolean flag = false;
        try {
            flag = mDBManager.getDaoSession().insert(tomatoAlarmClock) != -1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 修改指定记录
     */
    public boolean update(TomatoAlarmClock tomatoAlarmClock) {
        boolean flag = false;
        try {
            mDBManager.getDaoSession().update(tomatoAlarmClock);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除指定记录
     */
    public boolean delete(TomatoAlarmClock tomatoAlarmClock) {
        boolean flag = false;
        try {
            mDBManager.getDaoSession().delete(tomatoAlarmClock);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除所有的记录
     */
    public boolean deleteAll() {
        boolean flag = false;
        try {
            mDBManager.getDaoSession().deleteAll(TomatoAlarmClock.class);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 查询某一个表的所有记录
     */
    public List<TomatoAlarmClock> loadAll() {
        return mDBManager.getDaoSession().loadAll(TomatoAlarmClock.class);
    }

    /**
     * 按照主键查询某一个表中的单行记录
     */
    public TomatoAlarmClock loadByKey(long key) {
        return mDBManager.getDaoSession().load(TomatoAlarmClock.class, key);
    }
}
