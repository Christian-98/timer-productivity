package it.christian.timer.engine;

import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;

/**
 * Starter to use in case of accounting datasource is not available (Common case)
 * 
 *
 * @author Christian
 */

@Singleton(name = Starter.EJB_NAME)
@Startup
@TransactionManagement(TransactionManagementType.BEAN)
public class StarterCommon extends Starter {

	/*
	 * Pojo class that calls main Starter class
	 */
	
}