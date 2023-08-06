package com.yoi.feign.config;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoi.config.JwtUtils;
import com.yoi.entity.ReturnInfo;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 游弋
 * @create 2023-07-28 1:18
 * JWT验证拦截器
 */
@Component
@Slf4j
@Validated
public class JWTInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        //跨域请求会首先发一个option请求，直接返回正常状态并通过拦截器
        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        StringBuffer requestURL = request.getRequestURL();
        System.out.println(requestURL);
        Map<String, Object> map = new HashMap<>();
        ReturnInfo<Object> objectReturnInfo = new ReturnInfo<>();
        //令牌建议是放在请求头中，获取请求头中令牌
        String token = request.getHeader("Authorization");
            try {
                token = token.substring(7);
                System.out.println(token);//输出令牌
                JwtUtils.verify(token);//验证令牌
                return true;//放行请求     }
            } catch (SignatureVerificationException e) {
                log.warn(e.toString());
                objectReturnInfo.setMessage("无效签名");
                objectReturnInfo.setCode(404);
            } catch (TokenExpiredException e) {
                log.warn(e.toString());
                objectReturnInfo.setMessage("token过期");
                objectReturnInfo.setCode(401);
            } catch (AlgorithmMismatchException e) {
                log.warn(e.toString());
                objectReturnInfo.setMessage("token算法不一致");
                objectReturnInfo.setCode(401);
            } catch (Exception e) {
                log.warn(e.toString());
                objectReturnInfo.setMessage("token失效");
                objectReturnInfo.setCode(500);
            }
        //转化成json，response使用的是Jackson
            String json = new ObjectMapper().writeValueAsString(objectReturnInfo);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().print(json);
            return false;
        }
    }

