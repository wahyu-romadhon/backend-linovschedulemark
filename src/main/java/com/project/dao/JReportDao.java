package com.project.dao;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.project.LinovScheduleMark.SimpleReportFiller;

@Repository("JReportDao")
public class JReportDao extends ParentDao {

	@Transactional
	public SimpleReportFiller getNotifPayroll(String userCode) {
		Calendar cal = Calendar.getInstance();
		int tanggal = cal.get(Calendar.DATE);
		SimpleReportFiller simpleReportFiller = new SimpleReportFiller();
		simpleReportFiller.setEntityManager(super.entityManager);
		simpleReportFiller.setReportFileName("coba_notif.jrxml");
		simpleReportFiller.compileReport();
		Map<String, Object> parameters = new HashMap<>();
//		String user = "USER-03";
		parameters.put("p_user", userCode);
		parameters.put("p_tanggal", tanggal);
		parameters.put("p_sort", " GROUP BY u.user_name, c.company_name, t.task_code, t.task_description,\r\n"
				+ "	t.start_date, t.finish_date, td.date_realization, td.notes, td.status ORDER BY user_name, company_name, task_code");
		simpleReportFiller.setParameters(parameters);
		simpleReportFiller.fillReport();
		return simpleReportFiller;
	}
	
	@Transactional
	public SimpleReportFiller getReportByPayroll(String userCode) {
		String user = "USER-03";
		String period = "2019-MEI";
		
		SimpleReportFiller simpleReportFillerPayroll = new SimpleReportFiller();
		simpleReportFillerPayroll.setEntityManager(super.entityManager);
        simpleReportFillerPayroll.setReportFileName("coba_payroll.jrxml");
        simpleReportFillerPayroll.compileReport();
        
        Map<String, Object> parametersPayroll = new HashMap<>(); 
        parametersPayroll.put("p_user", user);
        parametersPayroll.put("p_period", period);
        parametersPayroll.put("p_sortPayroll", " GROUP BY th.year_month, u.user_code, u.user_name, c.company_name, t.task_code, t.task_description,\r\n" + 
        		"	t.start_date, t.finish_date, td.date_realization, td.notes, td.status ORDER BY th.year_month, u.user_code");

        simpleReportFillerPayroll.setParameters(parametersPayroll);
        simpleReportFillerPayroll.fillReport();
        return simpleReportFillerPayroll;
	}
	
	@Transactional
	public SimpleReportFiller getReportByCompany() {
		String client = "CMP-03";
		String user = "USER-03";
		String period = "2019-MEI";
		
		SimpleReportFiller simpleReportFillerClient = new SimpleReportFiller();
		simpleReportFillerClient.setEntityManager(super.entityManager);
        simpleReportFillerClient.setReportFileName("coba_client.jrxml");
        simpleReportFillerClient.compileReport();
        
        Map<String, Object> parametersClient = new HashMap<>();       
        parametersClient.put("p_client", client);
        parametersClient.put("p_user", user);
        parametersClient.put("p_period", period);
        parametersClient.put("p_sortCompany", " GROUP BY th.year_month, u.user_code, c.company_code, c.company_name, t.task_code, t.task_description,\r\n" + 
        		"	t.start_date, t.finish_date, td.date_realization, td.notes, td.status ORDER BY th.year_month, u.user_code, c.company_code ASC");
        
        
        simpleReportFillerClient.setParameters(parametersClient);
        simpleReportFillerClient.fillReport();
        return simpleReportFillerClient;
	}
	
	@Transactional
	public SimpleReportFiller getReportByPeriod() {
		String period = "2019-MEI";
		
        SimpleReportFiller simpleReportFillerPeriod = new SimpleReportFiller();
		simpleReportFillerPeriod.setEntityManager(super.entityManager);
        simpleReportFillerPeriod.setReportFileName("coba.jrxml");
        simpleReportFillerPeriod.compileReport();
        
        Map<String, Object> parametersPeriod = new HashMap<>(); 
        parametersPeriod.put("p_period", period);
        parametersPeriod.put("p_sortPeriod", " GROUP BY th.year_month, u.user_code, u.user_name, c.company_name, t.task_code, t.task_description,\r\n" + 
        		"	t.start_date, t.finish_date, td.date_realization, td.notes, td.status ORDER BY th.year_month, u.user_code ASC");
        
        simpleReportFillerPeriod.setParameters(parametersPeriod);
        simpleReportFillerPeriod.fillReport();
        return simpleReportFillerPeriod;
	}

}
