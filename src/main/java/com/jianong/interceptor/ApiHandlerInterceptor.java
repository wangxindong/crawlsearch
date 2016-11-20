package com.jianong.interceptor;

import com.jianong.util.Const;
import com.jianong.util.MD5;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 前端api拦截器
 *
 * @author wxd
 */
@SuppressWarnings("all")
public class ApiHandlerInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        System.out.println("拦截前台");
        String method = request.getMethod();
        String path = request.getServletPath();
        String timestrap = request.getParameter("time");
        String app_id = request.getParameter("app_id");
        String sign = request.getParameter("sign");

        // 系统生成sign
        String programsign = sign(path, method);

        if (!path.matches(Const.API_INTERCEPTOR_PATH)) {
            return false;
        }

        if (StringUtils.isNotEmpty(app_id)) {
            if (!Const.APP_ID.equals(app_id)) {
                return false;
            }
        }

        if (StringUtils.isNotEmpty(sign)) {
            if (!programsign.equals(sign)) {
                return false;
            }
        }

        if (StringUtils.isNotBlank(timestrap)) {
            if (verifyDateTimeStrap(timestrap) == false) {
                return false;
            }
        }
        return true;
    }


    /**
     * 比对时间
     *
     * @param timestrap
     * @return
     */
    private boolean verifyDateTimeStrap(String timestrap) {
        long currenttime = System.currentTimeMillis();
        long tmp = Long.parseLong(timestrap);
        if (currenttime - tmp > 3000000) {
            return false;
        }
        return true;
    }

    /**
     * 截取除了sign之前的url
     *
     * @param url
     * @return
     */
    private String sign(String url, String method) {
        if ("get".equalsIgnoreCase(method)) {
            return MD5.md5(url.split("&sign")[0]);
        } else {
            //
            return MD5.md5(url);
        }
    }

}
