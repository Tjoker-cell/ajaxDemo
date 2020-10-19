package tjoker.dao;


import tjoker.pojo.Log;
import tjoker.utils.JDBCUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: AjaxDemo
 * @description: 日志持久层
 * @author: 十字街头的守候
 * @create: 2019-10-17 17:52
 **/
public class LogDao {
    //声明jdbc对象
    Connection conn= JDBCUtils.getConnection();
    PreparedStatement ps=null;
    ResultSet rs=null;
    //声明变量
    List<Log> list;
    Log l;
    int index=-1;

    public LogDao() throws SQLException {
    }

    public int addLog(Log l) {
        try {
            //创建Sql命令
            String sql="insert log(user_name,log_action,user_role,log_content,user_state,user_id)  values(?,?,?,?,?,?)";
            //创建sql命令对象
            ps=conn.prepareStatement(sql);
            //            //给占位符赋值
            ps.setString(1, l.getUser_name());
            ps.setString(2, l.getLog_action());
            ps.setString(3, l.getUser_role());
            ps.setString(4, l.getLog_content());
            ps.setString(5, l.getUser_state());
            ps.setInt(6, l.getUser_id());



            //执行sql
            index=ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        //返回结果
        return index;
    }
    public List<Log> allLog() {
        list=new ArrayList<Log>();
        try {
            //创建Sql命令
            String sql="select*from log";
            //创建sql命令对象
            Statement stm=conn.createStatement();
            rs=stm.executeQuery(sql);
            while(rs.next()) {
                l=new Log();
                l.setLog_action(rs.getString("log_action"));
                l.setLog_content(rs.getString("log_content"));
                l.setLog_id(rs.getInt("log_id"));
                l.setUser_id(rs.getInt("user_id"));
                l.setUser_name(rs.getString("user_name"));
                l.setUser_role(rs.getString("user_role"));
                l.setUser_state(rs.getString("user_state"));
                l.setLog_time(rs.getDate("log_time"));
                list.add(l);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<Log> findLog(String username) {
        list=new ArrayList<Log>();
        try {
            //创建Sql命令
            String sql="select*from log where user_name=?";
            //创建sql命令对象
            ps=conn.prepareStatement(sql);
            ps.setString(1, username);
            rs=ps.executeQuery();
            while(rs.next()) {
                l=new Log();
                l.setLog_action(rs.getString("log_action"));
                l.setLog_content(rs.getString("log_content"));
                l.setLog_id(rs.getInt("log_id"));
                l.setUser_id(rs.getInt("user_id"));
                l.setUser_name(rs.getString("user_name"));
                l.setUser_role(rs.getString("user_role"));
                l.setUser_state(rs.getString("user_state"));
                l.setLog_time(rs.getDate("log_time"));
                list.add(l);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public int deleteLog(int id) {
        try {
            //创建Sql命令
            String sql="delete from log where log_id=?";
            //创建sql命令对象
            ps=conn.prepareStatement(sql);
            //给占位符赋值
            ps.setInt(1, id);

            //执行sql
            index=ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        //返回结果
        return index;
    }

}
