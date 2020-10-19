package tjoker.service;

import tjoker.dao.LogDao;
import tjoker.pojo.Log;

import java.sql.SQLException;
import java.util.List;

/**
 * @program: AjaxDemo
 * @description: 日志业务层
 * @author: 十字街头的守候
 * @create: 2019-10-17 17:55
 **/
public class LogService {
    //申明dao成对象
    LogDao ld=new LogDao();

    public LogService() throws SQLException {
    }

    public int addLog(Log lg) {
        return ld.addLog(lg);
    }
    public List<Log> allLog() {
        return ld.allLog();
    }
    public List<Log> findLog(String username) {
        return ld.findLog(username);
    }
    public int deletFile(int id) {
        return ld.deleteLog(id);
    }
}
