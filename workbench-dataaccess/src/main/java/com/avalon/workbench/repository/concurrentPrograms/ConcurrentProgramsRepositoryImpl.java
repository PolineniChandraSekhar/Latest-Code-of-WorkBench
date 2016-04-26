package com.avalon.workbench.repository.concurrentPrograms;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.avalon.workbench.beans.concurrntPrograms.CPDetails;
import com.avalon.workbench.beans.concurrntPrograms.ConcurrentPrograms;
import com.avalon.workbench.beans.responsibilites.Responsibilites;
import com.avalon.workbench.repository.exception.WorkbenchDataAccessException;
import com.avalon.workbench.repository.user.UserRepositoryImpl;

@Repository("ConcurrentProgramsRepositoryImpl")
public class ConcurrentProgramsRepositoryImpl implements
		ConcurrentProgramsRepository {

	protected static final Logger LOG_R = Logger
			.getLogger(ConcurrentProgramsRepositoryImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<ConcurrentPrograms> getConcurrentPrograms(String uname,
			String respNmae) throws WorkbenchDataAccessException {
		// TODO Auto-generated method stub
		try {
			LOG_R.info("inside getConcurrentPrograms----");
			String sql = "select distinct fu.user_name, p.user_concurrent_program_name, a.application_name, a.application_short_name, p.concurrent_program_name, p.concurrent_program_id, p.application_id "
					+ "from fnd_concurrent_programs_vl p, fnd_application_vl a, fnd_request_group_units u, fnd_responsibility_vl r, fnd_user fu,workbench_wcp ww"
					+ " where p.srs_flag in ('Y', 'Q') and p.enabled_flag = 'Y' and request_set_flag = 'N' and ((a.application_id = u.unit_application_id "
					+ "and u.application_id = r.group_application_id and u.request_group_id = r.request_group_id and u.request_unit_type = 'A') "
					+ "or (p.application_id = u.unit_application_id and p.concurrent_program_id = u.request_unit_id and u.application_id = r.group_application_id "
					+ "and u.request_group_id = r.request_group_id and u.request_unit_type = 'P')) and p.application_id = a.application_id "
					+ "and r.responsibility_name='"
					+ respNmae
					+ "' and fu.user_name='"
					+ uname
					+ "' and ww.user_concurrent_program_name = p.user_concurrent_program_name"
					+ " order by 1,2,4";
			/*
			 * Added additional table "workbench_wcp" to the Query Added join
			 * Condition ww.concurrent_program_id = p.concurrent_program_id" for
			 * displaying only working CC Programs.
			 */
			LOG_R.info("query==" + sql);
			BeanPropertyRowMapper rm = new BeanPropertyRowMapper(
					ConcurrentPrograms.class);
			List<ConcurrentPrograms> result = jdbcTemplate.query(sql, rm);
			return result;
		} catch (Exception e) {
			LOG_R.error("Exception occured ::" + e);
			throw new WorkbenchDataAccessException(e);
		}

	}

	public List<ConcurrentPrograms> getConcurrentProgramNames(String ccpName)
			throws WorkbenchDataAccessException {
		try {
			LOG_R.info("inside getConcurrentPrograms----");
			String sql = " SELECT distinct(fcpt.user_concurrent_program_name) "
					+ " FROM fnd_Responsibility fr, fnd_responsibility_tl frt,"
					+ " fnd_request_groups frg, fnd_request_group_units frgu,"
					+ " fnd_concurrent_programs_tl fcpt "
					+ " WHERE frt.responsibility_id = fr.responsibility_id"
					+ " AND frg.request_group_id = fr.request_group_id "
					+ " AND frgu.request_group_id = frg.request_group_id"
					+ " AND fcpt.concurrent_program_id = frgu.request_unit_id"
					+ " AND frt.LANGUAGE = USERENV('LANG')"
					+ " AND fcpt.LANGUAGE = USERENV('LANG')"
					+ " AND fcpt.user_concurrent_program_name like '" + ccpName
					+ "%'";

			LOG_R.info("query==" + sql);
			BeanPropertyRowMapper rm = new BeanPropertyRowMapper(
					ConcurrentPrograms.class);
			List<ConcurrentPrograms> result = jdbcTemplate.query(sql, rm);
			return result;
		} catch (Exception e) {
			LOG_R.error("Exception occured ::" + e);
			throw new WorkbenchDataAccessException(e);
		}

	}

	/*
	 * public List<CPDetails> getCPDetails() throws WorkbenchDataAccessException
	 * {
	 * 
	 * try { LOG_R.info("inside getCPDetails----");
	 * 
	 * String sql = "SELECT DISTINCT " + "fcp.user_concurrent_program_name," +
	 * " fcr.request_id," + " TO_CHAR(fcr.request_date) as request_date," +
	 * " flv.meaning as status," + "fcr.logfile_name," + "fcr.outfile_name" +
	 * " FROM apps.fnd_concurrent_programs_vl fcp," +
	 * "  apps.fnd_concurrent_requests    fcr," +
	 * "apps.fnd_lookup_values          flv" +
	 * " WHERE fcr.concurrent_program_id = fcp.concurrent_program_id" +
	 * " AND trunc(fcr.last_update_date) = trunc(SYSDATE)" +
	 * " AND flv.lookup_code = fcr.status_code" +
	 * " AND flv.lookup_type = 'CP_STATUS_CODE'" + " AND flv.language = 'US'" +
	 * "  ORDER BY fcr.request_id DESC"; // , fcr.request_date
	 * 
	 * LOG_R.info("query==" + sql); BeanPropertyRowMapper rm = new
	 * BeanPropertyRowMapper( CPDetails.class); List<CPDetails> result =
	 * jdbcTemplate.query(sql, rm); return result; } catch (Exception e) {
	 * LOG_R.error("Exception occured ::" + e); throw new
	 * WorkbenchDataAccessException(e); }
	 * 
	 * }
	 */
	public List<CPDetails> getCPDetails(String to_date, String from_date)
			throws WorkbenchDataAccessException {

		try {
			LOG_R.info("inside getCPDetails----");

			/*
			 * String sql = "SELECT DISTINCT " +
			 * "fcp.user_concurrent_program_name," + " fcr.request_id," +
			 * " TO_CHAR(fcr.request_date) as request_date," +
			 * " flv.meaning as status," + "fcr.logfile_name," +
			 * "fcr.outfile_name" + " FROM apps.fnd_concurrent_programs_vl fcp,"
			 * + "  apps.fnd_concurrent_requests    fcr," +
			 * "apps.fnd_lookup_values          flv" +
			 * " WHERE fcr.concurrent_program_id = fcp.concurrent_program_id" +
			 * " AND trunc(fcr.last_update_date) = trunc(SYSDATE)" +
			 * " AND flv.lookup_code = fcr.status_code" +
			 * " AND flv.lookup_type = 'CP_STATUS_CODE'" +
			 * " AND flv.language = 'US'" + "  ORDER BY fcr.request_id DESC"; //
			 * , fcr.request_date
			 */
			List<CPDetails> result = new ArrayList<CPDetails>();
			if (to_date == null || to_date.equals("")) {
				Date d = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
				String todate = sdf.format(d);
				LOG_R.info("Date is " + todate);
				Calendar cal = Calendar.getInstance();
				cal.setTime(d);
				cal.add(Calendar.DATE, -7);// get 7th days date from current Date
				d = cal.getTime();
				String fromdate = sdf.format(d);
				LOG_R.info("Date is " + todate);
				String sql = "select * from cphistory where request_date  between '"
						+ fromdate + "' and '"+ todate +  "' order by REQUEST_DATE desc";
				LOG_R.info("query==" + sql);
				BeanPropertyRowMapper rm = new BeanPropertyRowMapper(
						CPDetails.class);
				result = jdbcTemplate.query(sql, rm);
			} else {
				//Converting String to Sql specific Date Format
				LOG_R.info("To Date"+to_date+" From Date"+from_date);
				SimpleDateFormat sFormatter=new SimpleDateFormat("yyyy-mm-dd");
				Date todate=sFormatter.parse(to_date);
				Date fromdate=sFormatter.parse(from_date);
				SimpleDateFormat format=new SimpleDateFormat("dd-MMM-yy");
				to_date=format.format(todate);
				from_date=format.format(fromdate);
				
				String sql = "select * from cphistory where request_date  between '"
						+ from_date+ "' and '" + to_date +"' order by REQUEST_DATE desc";
				LOG_R.info("query==" + sql);
				BeanPropertyRowMapper rm = new BeanPropertyRowMapper(
						CPDetails.class);
				result = jdbcTemplate.query(sql, rm);
			}

			return result;
		} catch (Exception e) {
			LOG_R.error("Exception occured ::" + e);
			throw new WorkbenchDataAccessException(e);
		}

	}

	public List<ConcurrentPrograms> getSearchRelatedConc(String concName,
			String responsibilityName, String uname)
			throws WorkbenchDataAccessException {

		try {
			LOG_R.info("inside getConcurrentName----Concurrent Prgram Name"
					+ concName + "=========== Responsibility Name:"
					+ responsibilityName);

			/*
			 * String sql = "select distinct"+
			 * "      a.user_concurrent_program_name,"+
			 * "      appl.APPLICATION_NAME,"+
			 * "      fcp.CONCURRENT_PROGRAM_NAME,"+
			 * "      fa.application_short_name,"+
			 * "      a.CONCURRENT_PROGRAM_ID,"+ "      appl.APPLICATION_ID"+
			 * "  from"+ "       fnd_executables exe,"+
			 * "       FND_APPLICATION fa,"+ "       fnd_application_tl appl,"+
			 * "       fnd_concurrent_programs fcp,"+
			 * "       fnd_concurrent_programs_tl a,"+
			 * "       fnd_request_groups b,"+
			 * "       fnd_request_group_units c,"+
			 * "       fnd_responsibility d,"+ "       fnd_responsibility_tl e"+
			 * "  where"+ "       a.concurrent_program_id = c.request_unit_id "+
			 * "       and appl.APPLICATION_ID=exe.APPLICATION_ID"+
			 * "       and fa.APPLICATION_ID=exe.APPLICATION_ID"+
			 * "       and fcp.CONCURRENT_PROGRAM_ID=a.CONCURRENT_PROGRAM_ID"+
			 * "       and b.request_group_id = c.request_group_id"+
			 * "       and b.request_group_id = d.request_group_id"+
			 * "       and d.responsibility_id = e.responsibility_id"+
			 * "       and a.application_id = exe.application_id"+
			 * "       and exe.application_id = c.application_id"+
			 * "       and appl.application_id = e.application_id"+
			 * "       and a.user_concurrent_program_name like '%"
			 * +concName+"%'"; //"       and e.RESPONSIBILITY_NAME='"+
			 * responsibilityName+"'";
			 */

			String sql = "SELECT DISTINCT fu.user_name,"
					+ "  p.user_concurrent_program_name,"
					+ "  a.application_name,"
					+ "  a.application_short_name,"
					+ "  p.concurrent_program_name,"
					+ "  p.concurrent_program_id,"
					+ "  p.application_id"
					+ " FROM fnd_concurrent_programs_vl p,"
					+ " fnd_application_vl a,"
					+ " fnd_request_group_units u,"
					+ " fnd_responsibility_vl r, fnd_user fu , workbench_wcp ww "
					+ " WHERE p.srs_flag           IN ('Y', 'Q')"
					+ " AND p.enabled_flag          = 'Y'"
					+ " AND request_set_flag        = 'N'"
					+ " AND ((a.application_id      = u.unit_application_id"
					+ " AND u.application_id        = r.group_application_id"
					+ " AND u.request_group_id      = r.request_group_id"
					+ " AND u.request_unit_type     = 'A')"
					+ " OR (p.application_id        = u.unit_application_id"
					+ " AND p.concurrent_program_id = u.request_unit_id"
					+ " AND u.application_id        = r.group_application_id "
					+ " AND u.request_group_id      = r.request_group_id"
					+ " AND u.request_unit_type     = 'P'))"
					+ " AND p.application_id        = a.application_id "
					+ " AND r.responsibility_name   ='"
					+ responsibilityName
					+ "'"
					+ " AND fu.user_name            ='"
					+ uname
					+ "'"
					+ " AND ww.user_concurrent_program_name = p.user_concurrent_program_name"
					+ " AND Upper(p.USER_CONCURRENT_PROGRAM_NAME) LIKE '%"
					+ concName.toUpperCase() + "%'" + " ORDER BY 1,2,4";// fixing
																		// user
																		// name
			/*
			 * Added additional table "workbench_wcp" to the Query Added join
			 * Condition ww.concurrent_program_id = p.concurrent_program_id" for
			 * displaying only working CC Programs while searching CC Programs.
			 */

			LOG_R.info("query==" + sql);
			BeanPropertyRowMapper rm = new BeanPropertyRowMapper(
					ConcurrentPrograms.class);

			List<ConcurrentPrograms> result = jdbcTemplate.query(sql, rm);

			return result;
		} catch (Exception e) {
			LOG_R.error("Exception occured ::" + e);
			throw new WorkbenchDataAccessException(e);
		}

	}

	// Functionality to be added
	public List<CPDetails> getSpecificCPDetails(String progName,
			String from_date, String to_date)
			throws WorkbenchDataAccessException {
		try {
			String sql = "SELECT "
					+ "  ROUND(((f.actual_completion_date-f.actual_start_date)*24*60*60/60),2) AS process_time,"
					+ "f.actual_start_date as request_date"
					+ "  from  apps.fnd_concurrent_programs p,"
					+ "  apps.fnd_concurrent_programs_tl pt,"
					+ "  apps.fnd_concurrent_requests f"
					+ "  where f.concurrent_program_id = p.concurrent_program_id"
					+ "  and f.program_application_id = p.application_id"
					+ "  and f.concurrent_program_id = pt.concurrent_program_id"
					+ "  and f.program_application_id = pt.application_id"
					+ "  AND pt.language = USERENV('Lang')"
					+ "  and f.actual_start_date is not null"
					+ "  and  pt.USER_CONCURRENT_PROGRAM_NAME='" + progName
					+ "'" + "  order by f.actual_start_date desc";
			BeanPropertyRowMapper rm = new BeanPropertyRowMapper(
					CPDetails.class);
			List<CPDetails> result = jdbcTemplate.query(sql, rm);
			return result;
		} catch (Exception e) {
			LOG_R.error("Exception occured ::" + e);
			throw new WorkbenchDataAccessException(e);
		}

	}
}
