package com.hpc.ship.aspects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Configuration
public class LoggingAspects {

	@Pointcut("execution(* com.hpc.ship.config.*.*(..))")
	private void configurations() {
		// This method defines the pointcut for the configurations
	}

	@Before(value = "configurations()")
	public void logBeforeConfigurations(JoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();
		log.info("Initializing: [{}.{}]", signature.getDeclaringType().getSimpleName(), signature.getName());
	}

	@Pointcut("execution(* com.hpc.ship.controllers.*.*(..))")
	private void controllers() {
		// This method defines the pointcut for the controllers
	}

	@Before(value = "controllers()")
	public void logBeforeControllers(JoinPoint joinPoint) {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		String params = new ArrayList<>(Arrays.asList(joinPoint.getArgs())).stream().map(String::valueOf).collect(Collectors.joining(", "));
		log.info("[{}] called with arguments [{}]", request.getRequestURL(), params);
	}

	@AfterReturning(value = "controllers()")
	public void logAfterControllersReturning(JoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();
		log.info("Exiting: [{}.{}]", signature.getDeclaringType().getSimpleName(), signature.getName());
	}

	@Pointcut("execution(* com.hpc.ship.services.*.*(..))")
	private void services() {
		// This method defines the pointcut for the services
	}

	@Before(value = "services()")
	public void logBeforeServices(JoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();
		String params = new ArrayList<>(Arrays.asList(joinPoint.getArgs())).stream().map(String::valueOf).collect(Collectors.joining(", "));
		log.info("Entering: [{}.{}] with arguments [{}]", signature.getDeclaringType().getSimpleName(), signature.getName(), params);
	}

	@AfterReturning(value = "services()", returning = "result")
	public void logAfterServicesReturning(JoinPoint joinPoint, Object result) {
		Signature signature = joinPoint.getSignature();
		log.info("Exiting: [{}.{}] with return value [{}]", signature.getDeclaringType().getSimpleName(), signature.getName(), result);
	}

	@AfterThrowing(value = "controllers() || services()", throwing = "ex")
	public void logAfterMethodsThrowing(JoinPoint joinPoint, Exception ex) {
		log.error(ex.getMessage(), ex.getCause());
	}
}
