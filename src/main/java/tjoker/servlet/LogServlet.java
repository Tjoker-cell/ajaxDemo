package tjoker.servlet;

import com.google.gson.Gson;
import tjoker.pojo.Log;
import tjoker.service.LogService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

/**
 * @program: AjaxDemo
 * @description: 日志控制层
 * @author: 十字街头的守候
 * @create: 2019-10-17 17:54
 **/
public class LogServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置请求编码
        req.setCharacterEncoding("utf-8");
        //设置响应编码
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/json;charset=utf-8");
        //申明业务层方法
         LogService   logService = null;
        //申明log数组
        List<Log> logs = null;
        try {
            logService = new LogService();
            //获取前端参数
            String action=req.getParameter("action");
            System.out.println("success");
            //判断前端执行方法 查询 or 删除
            if(action.equals("allLog")){
                logs = logService.allLog();
                System.out.println(logs);

            }else if(action.equals("findLog")){
                //获取查询参数username
                String username=req.getParameter("username");
                logs = logService.findLog(username);
            }
            System.out.println(new Gson().toJson(logs));
            //定义输出流
            PrintWriter out =resp.getWriter();
            //将logs数据写入到request作用域中
            out.write(new Gson().toJson(logs));
            //强制输出缓冲区
            out.flush();
            //关闭资源
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }  
  
}
