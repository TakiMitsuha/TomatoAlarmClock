package me.takimitsuha.tomatoalarmclock.db;

import android.content.Context;

import org.greenrobot.greendao.query.QueryBuilder;

import me.takimitsuha.tomatoalarmclock.entity.DaoMaster;
import me.takimitsuha.tomatoalarmclock.entity.DaoSession;

/**
 * Created by Taki on 2017/2/7.
 */
public class DBManager {

    private static final String DB_NAME = "TomatoAlarmClock-db";//数据库名称

    private static DBManager singleInstance = null;
    private DaoMaster.OpenHelper helper;
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    private Context mContext;

    public DBManager() {
    }

    public static DBManager getInstance() {
        if (singleInstance == null) {
            synchronized (DBManager.class) {
                if (singleInstance == null) {
                    singleInstance = new DBManager();
                }
            }
        }
        return singleInstance;
    }

    public void init(Context context) {
        this.mContext = context;
    }

    /**
     * 获取DaoMaster
     *
     * @return DaoMaster
     */
    public DaoMaster getDaoMaster() {
        if (daoMaster == null) {
            try {
                helper = new DaoMaster.DevOpenHelper(mContext, DB_NAME, null);
                daoMaster = new DaoMaster(helper.getWritableDatabase());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return daoMaster;
    }

    /**
     * 获取DaoSession对象
     *
     * @return DaoSession
     */
    public DaoSession getDaoSession() {
        if (daoSession == null) {
            if (daoMaster == null) {
                getDaoMaster();
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    /**
     * 打开输出日志的操作,默认的是关闭的
     */
    public void setDeBug() {
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }

    /**
     * 关闭所有的连接
     */
    public void closeConnections() {
        closeHelper();
        closeSession();
    }

    private void closeSession() {
        if (daoSession != null) {
            daoSession.clear();
            daoSession = null;
        }
    }

    private void closeHelper() {
        if (helper != null) {
            helper.close();
            helper = null;
        }
    }

}
