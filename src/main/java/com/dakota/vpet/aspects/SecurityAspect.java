package com.dakota.vpet.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

@Aspect
@Component
public class SecurityAspect {
    
    @Around("@annotation(com.dakota.vpet.annotations.LoggedIn)")
    public Object loggedIn(ProceedingJoinPoint joinPoint) throws Throwable{
        ServerRequest request = getRequest(joinPoint);
        
        if(request != null){
            System.err.println(request.cookies().get("token"));
        }

        return joinPoint.proceed();
        
    }

    private ServerRequest getRequest(ProceedingJoinPoint jp){
        for(Object arg : jp.getArgs()){
            if(arg instanceof ServerRequest){
                return (ServerRequest) arg;
            }
        }
        return null;
    }

}
