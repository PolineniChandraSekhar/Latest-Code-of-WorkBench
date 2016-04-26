package com.avalon.workbench.repository.responsibilites;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.avalon.workbench.beans.responsibilites.Responsibilites;
import com.avalon.workbench.repository.exception.WorkbenchDataAccessException;
import com.avalon.workbench.repository.user.UserRepositoryImpl;

@Repository("ResponsibilitesRepositoryImpl")
public class ResponsibilitesRepositoryImpl implements ResponsibilitesRepository {
	protected static final Logger LOG_R = Logger
			.getLogger(ResponsibilitesRepositoryImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Responsibilites> getResonsibilites(String uname)
			throws WorkbenchDataAccessException {
		// TODO Auto-generated method stub
		try {
			LOG_R.info("inside getResponsibilities----");
			String sql = "SELECT fu.user_name,"
					  +" frt.responsibility_name,"
					  +" TO_CHAR(furg.start_date) AS start_date,"
					  +" TO_CHAR(furg.end_date)   AS end_date,"
					  +" fr.responsibility_key,"
					  +" fat.application_name "
					  +" FROM fnd_user_resp_groups_direct furg,"
					  +" applsys.fnd_user fu,"
					  +" applsys.fnd_responsibility_tl frt,"
					  +" applsys.fnd_responsibility fr,"
					  +" applsys.fnd_application_tl fat,"
					  +" applsys.fnd_application fa "
					  +",workbench_wr wwr"//Added to show only working Responsbilities
					  +" WHERE furg.user_id         = fu.user_id"
					  +" AND furg.responsibility_id = frt.responsibility_id"
					  +" AND fr.responsibility_id   = frt.responsibility_id"
					  +" AND fa.application_id      = fat.application_id"
					  +" AND fr.application_id      = fat.application_id"
					  +" AND frt.language           = USERENV('LANG')"
					  +" AND UPPER(fu.user_name)    = UPPER('"+uname+"')"
					  +" and wwr.responsibility_name =frt.responsibility_name"//Added to show only working Responsbilities
					  +" AND (furg.end_date        IS NULL "
					  +" OR furg.end_date          >= TRUNC(SYSDATE)) "
					  +" ORDER BY frt.responsibility_name"; 
			// to_char(TO_DATE( SUBSTR('furg.start_date',1, 10),'YYYY-MM-DD
			// HH24:MI:SS'),'MM-DD-YYYY') as
			BeanPropertyRowMapper rm = new BeanPropertyRowMapper(
					Responsibilites.class);
			List<Responsibilites> result = jdbcTemplate.query(sql, rm);
			if (result != null && !result.isEmpty()) {
				return result;
			}
		} catch (Exception e) {
			LOG_R.error("Exception occured ::" + e);
			throw new WorkbenchDataAccessException(e);
		}
		return null;

	}

	public List<Responsibilites> getSearchRelatedResp(String responsibilityName,String userName) throws WorkbenchDataAccessException {
		
		try {
			LOG_R.info("inside getResponsibiltyName----");
			String sql ="SELECT fu.user_name,"
					  +" frt.responsibility_name,"
					  +" TO_CHAR(furg.start_date) AS start_date,"
					  +" TO_CHAR(furg.end_date)   AS end_date,"
					  +" fr.responsibility_key,"
					  +" fat.application_name "
					  +" FROM fnd_user_resp_groups_direct furg,"
					  +" applsys.fnd_user fu,"
					  +" applsys.fnd_responsibility_tl frt,"
					  +" applsys.fnd_responsibility fr,"
					  +" applsys.fnd_application_tl fat,"
					  +" applsys.fnd_application fa "
					  +",workbench_wr wwr"//Added to show only working Responsbilities
					  +" WHERE furg.user_id         = fu.user_id"
					  +" AND furg.responsibility_id = frt.responsibility_id"
					  +" AND fr.responsibility_id   = frt.responsibility_id"
					  +" AND fa.application_id      = fat.application_id"
					  +" AND fr.application_id      = fat.application_id"
					  +" AND frt.language           = USERENV('LANG')"
					  +" AND UPPER(fu.user_name)    = UPPER('"+userName+"')"
					  +" AND UPPER(frt.responsibility_name) like'%"+responsibilityName.toUpperCase()+"%'"
					  +" and wwr.responsibility_name =frt.responsibility_name"//Added to show only working Responsbilities
					  +" AND (furg.end_date        IS NULL "
					  +" OR furg.end_date          >= TRUNC(SYSDATE)) "
					  +" ORDER BY frt.responsibility_name"; 
					
/*"select distinct "+
"fu.user_name,"+
"e.responsibility_name,"+
"TO_CHAR(furg.start_date) as start_date,"+
"TO_CHAR(furg.end_date) as end_date,"+
"fat.APPLICATION_NAME "+
"from fnd_user fu,fnd_application     fa,"+
"fnd_user_resp_groups_direct furg,fnd_application_tl  fat,"+
"fnd_concurrent_programs_tl a,fnd_request_groups b,"+
"fnd_request_group_units c,fnd_responsibility d,"+
"fnd_responsibility_tl e "+
"where "+
"a.concurrent_program_id = c.request_unit_id "+
"and furg.user_id  = fu.user_id "+
"and  furg.responsibility_id = e.responsibility_id "+
"and b.request_group_id = c.request_group_id "+
"and b.request_group_id = d.request_group_id "+
"and d.responsibility_id = e.responsibility_id "+
"and fa.application_id = b.application_id "+
"and b.application_id = c.application_id "+
"and d.application_id = e.application_id "+
"and fa.application_id = fat.application_id "+
"and upper(e.responsibility_name) like '%"+responsibilityName.toUpperCase()+"%' "+
"and fu.USER_NAME='"+userName+ "'";
*/		
					LOG_R.info("query==" + sql);
			BeanPropertyRowMapper rm = new BeanPropertyRowMapper(
					Responsibilites.class);	
			
			List<Responsibilites> result = jdbcTemplate.query(sql, rm);
			//LOG_R.info("Result---------"+result.get(0).getResponsibility_name()+"Result 1=========="+result.get(1).getResponsibility_name() +" size :"+result.size());
				
		return result;
	}
		catch (Exception e) {
			LOG_R.error("Exception occured ::" + e);
			throw new WorkbenchDataAccessException(e);
		}	
		
}
}
