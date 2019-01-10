package cn.com.caogen.externIsystem.service;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import cn.com.caogen.externIsystem.util.GsonUtil;

public class BasePayService {
    public void outJson(HttpServletResponse response, Object object) {
        PrintWriter out = null;
        try {
            out = response.getWriter();
            String json = GsonUtil.toJson(object);
            response.setContentType("text/html;charset=utf-8");
            out.write(json);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void outString(HttpServletResponse response, String msg) {
        PrintWriter out = null;
        try {
            out = response.getWriter();
            response.setContentType("text/html;charset=utf-8");
            out.write(msg);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
