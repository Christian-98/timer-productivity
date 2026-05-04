package it.christian.timer.application;

import java.util.HashSet;
import java.util.Set;

import it.christian.timer.restcontroller.StatisticsControllerImpl;
import it.christian.timer.restcontroller.TimerControllerImpl;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/resources")
public class TimerApplication extends Application {
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<>();
		classes.add(TimerControllerImpl.class);
		classes.add(StatisticsControllerImpl.class);
		return classes;
	}
	
}
