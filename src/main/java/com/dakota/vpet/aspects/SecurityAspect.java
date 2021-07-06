package com.dakota.vpet.aspects;

import java.lang.reflect.Method;

import com.dakota.vpet.annotations.LoggedIn;
import com.dakota.vpet.models.User;
import com.dakota.vpet.models.User.Role;
import com.dakota.vpet.utils.JWTUtil;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

@Aspect
@Component
public class SecurityAspect {
    
    @Around("loggedInHook()")
    public Object loggedIn(ProceedingJoinPoint joinPoint) throws Throwable{
        ServerRequest request = getRequest(joinPoint);
        if(request == null){
            return ServerResponse.badRequest().build();
        }

        Method method = ((MethodSignature)joinPoint.getSignature()).getMethod();
        LoggedIn annotation = method.getAnnotation(LoggedIn.class);
        Role minRole = annotation.minRole();
        
        try{
            HttpCookie token = request.cookies().getFirst("token");
            User user = JWTUtil.verifyToken(token.getValue());
            request.attributes().put("user", user);

            switch(minRole){
                case PLAYER:
                    if(user != null)
                        return joinPoint.proceed();
                case ADMIN:
                    if(user.getRole() == minRole)
                        return joinPoint.proceed();
                    else
                        return ServerResponse.status(HttpStatus.FORBIDDEN).build();
            }
        }catch(Exception e){
            e.printStackTrace();
            return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
        }
        return null; //should never reach here       
    }

    private ServerRequest getRequest(ProceedingJoinPoint jp){
        for(Object arg : jp.getArgs()){
            if(arg instanceof ServerRequest){
                return (ServerRequest) arg;
            }
        }
        return null;
    }

    @Pointcut("@annotation(com.dakota.vpet.annotations.LoggedIn)")
    private void loggedInHook(){}

}
