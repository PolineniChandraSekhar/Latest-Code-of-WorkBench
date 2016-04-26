package com.avalon.workbench.repository.concurrentReport;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Repository;

import com.avalon.workbench.beans.concurrntPrograms.CPDetails;
import com.avalon.workbench.beans.concurrntReport.Inputs;
import com.avalon.workbench.beans.concurrntReport.QueryStatus;
import com.avalon.workbench.repository.exception.WorkbenchDataAccessException;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

@Repository("concurrentReportRepositoryImpl")
public class ConcurrentReportRepositoryImpl implements
		ConcurrentReportRepository {
	protected static final Logger LOG_R = Logger
			.getLogger(ConcurrentReportRepositoryImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public String generateConcurrentReport(final String respName,
			final String uname, final String shortName,
			final String concurrentName, final Inputs inputs,
			final ArrayList<String> params) throws WorkbenchDataAccessException {
		try {
			CallableStatementCreator callableStatementCreator = new CallableStatementCreator() {

				public CallableStatement createCallableStatement(Connection con)
						throws SQLException {
					CallableStatement cs = con
							.prepareCall("{call Concurrent_Prog_Exec(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
					cs.setString(1, respName);
					cs.setString(2, uname);
					if (inputs != null) {
						cs.setString(3, inputs.getApplication_Short_Name());
						cs.setString(4, inputs.getTemplate_code());
						cs.setString(5, inputs.getDefault_Language());
						cs.setString(6, inputs.getDefault_Territory());

						if (inputs.getDefault_output_type() != null) {
							// if(inputs.getDefault_output_type().equals("EXCEL")
							// ){
							// inputs.setDefault_output_type("xls");
							// LOG_R.info("Output type set to "+inputs.getDefault_output_type());
							cs.setString(7, inputs.getDefault_output_type());
							// }
						} else
							cs.setString(7, "PDF");
					} else {
						cs.setString(3, null);
						cs.setString(4, null);
						cs.setString(5, null);
						cs.setString(6, null);
						cs.setString(7, null);
					}
					cs.setString(8, shortName);
					cs.setString(9, concurrentName);
					int k = 10;
					for (int i = 0; i < 15; i++) {
						LOG_R.info("PARAM cOUNT " + k);
						if (i < params.size())
							if (params.get(i) != null) {
								// LOG_R.info("if in not null");
								cs.setString(k, params.get(i));
							} else
								cs.setString(k, null);
						else {
							// LOG_R.info("=--- Out else");
							cs.setString(k, null);
						}
						k++;
						// LOG_R.info("-=-=-=k is "+k);
					}

					// commented by Mallik @ 20-03-15
					// if (params != null) {
					// if(params.get(0)!=null)
					// cs.setString(10, params.get(0));
					// cs.setString(11, params.get(1));
					// cs.setString(12, params.get(2));
					// cs.setString(13, params.get(3));
					// cs.setString(14, params.get(4));
					// cs.setString(15, params.get(5));
					// cs.setString(16, params.get(6));
					// cs.setString(17, params.get(7));
					// cs.setString(18, params.get(8));
					// cs.setString(19, params.get(9));
					// cs.setString(20, params.get(10));
					// cs.setString(21, params.get(11));
					// cs.setString(22, params.get(12));
					// cs.setString(23, params.get(13));
					// cs.setString(24, params.get(14));
					// } else {
					// cs.setString(10, null);
					// cs.setString(11, null);
					// cs.setString(12, null);
					// cs.setString(13, null);
					// cs.setString(14, null);
					// cs.setString(15, null);
					// cs.setString(16, null);
					// cs.setString(17, null);
					// cs.setString(18, null);
					// cs.setString(19, null);
					// cs.setString(20, null);
					// cs.setString(21, null);
					// cs.setString(22, null);
					// cs.setString(23, null);
					// cs.setString(24, null);
					// }
					cs.registerOutParameter(25, Types.INTEGER);
					return cs;
				}
			};
			List param = new ArrayList();
			param.add(new SqlParameter(Types.VARCHAR));
			param.add(new SqlParameter(Types.VARCHAR));
			param.add(new SqlParameter(Types.VARCHAR));
			param.add(new SqlParameter(Types.VARCHAR));
			param.add(new SqlParameter(Types.VARCHAR));
			param.add(new SqlParameter(Types.VARCHAR));
			param.add(new SqlParameter(Types.VARCHAR));
			param.add(new SqlParameter(Types.VARCHAR));
			param.add(new SqlParameter(Types.VARCHAR));
			param.add(new SqlParameter(Types.VARCHAR));
			param.add(new SqlParameter(Types.VARCHAR));
			param.add(new SqlParameter(Types.VARCHAR));
			param.add(new SqlParameter(Types.VARCHAR));
			param.add(new SqlParameter(Types.VARCHAR));
			param.add(new SqlParameter(Types.VARCHAR));
			param.add(new SqlParameter(Types.VARCHAR));
			param.add(new SqlParameter(Types.VARCHAR));
			param.add(new SqlParameter(Types.VARCHAR));
			param.add(new SqlParameter(Types.VARCHAR));
			param.add(new SqlParameter(Types.VARCHAR));
			param.add(new SqlParameter(Types.VARCHAR));
			param.add(new SqlParameter(Types.VARCHAR));
			param.add(new SqlParameter(Types.VARCHAR));
			param.add(new SqlParameter(Types.VARCHAR));
			param.add(new SqlOutParameter("retVal", Types.INTEGER));
			String reqId = (String) jdbcTemplate
					.call(callableStatementCreator, param).get("retVal")
					.toString();
			if (reqId != null)
				return reqId;
			return "0";
		} catch (Exception e) {
			LOG_R.error("Exception occured ::" + e);
			throw new WorkbenchDataAccessException(e);
		}
	}

	public String getConcurrentReport(String fileName, String reqId)
			throws WorkbenchDataAccessException {
		// String hostname = "183.82.106.194";
		String hostname = "192.168.100.100";
		String username = "root";
		String password = "Avalon@321";
		String copyFrom;
		String copyTo;
		int fn = 0;
		if (reqId.equals("") || reqId.equals(null)) {
			copyFrom = "/oraAS/oracle/VIS/inst/apps/VIS_apps/logs/appl/conc/out/"
					// +"APPPBR_5943927_1.PDF";
					+ fileName;
			copyTo = "D:/" + fileName;
		} else {
			copyFrom = fileName;
			fn = fileName.indexOf(reqId);
			LOG_R.info("index value :" + fn + "  " + fileName.substring(fn - 1));
			copyTo = "D:/" + fileName.substring(fn - 1);
			// copyTo = "D:/" + "o5959469.out";
		}
		LOG_R.info("Copyfrom===" + copyFrom);

		JSch jsch = new JSch();
		Session session = null;
		try {
			session = jsch.getSession(username, hostname, 22);
			session.setConfig("StrictHostKeyChecking", "no");
			session.setPassword(password);
			session.connect();
			Channel channel = session.openChannel("sftp");
			channel.connect();
			LOG_R.info("inside getConcurrentReport--------Connected");
			ChannelSftp sftpChannel = (ChannelSftp) channel;
			LOG_R.info("B4 copy");
			sftpChannel.get(copyFrom, copyTo);
			LOG_R.info("Aftr Copy");
			sftpChannel.exit();
			session.disconnect();
		} catch (Exception e) {
			LOG_R.error("Exception occured ::" + e);
			throw new WorkbenchDataAccessException(e);
		} finally {
			if (reqId.equals("") || reqId.equals(null))
				return "";
			else
				return fileName.substring(fn - 1);
		}
	}

	public QueryStatus getExecutionQuery(String progName, String reqId) {
		// jdbcTemplate.execute();
		try {
			// LOG_R.info("execution time--For testing--"+progName);
			String sql1 = "SELECT  flv1.meaning \"status\",flv2.meaning ,fcr.parent_request_id"
					+ " FROM  fnd_user                    fu, "
					+ "    fnd_responsibility_tl       frt, "
					+ "   fnd_concurrent_requests     fcr, "
					+ "   fnd_concurrent_programs_tl  fcpt, "
					+ "  fnd_lookup_values           flv1,"
					+ "   fnd_lookup_values           flv2  WHERE    1=1 	  AND flv1.lookup_code           =  fcr.status_code "
					+ " AND flv1.lookup_type           =  'CP_STATUS_CODE' "
					+ "AND flv1.enabled_flag          =  'Y' "
					+ "AND flv1.view_application_id   <> 0 "
					+ "AND flv2.lookup_code           =  fcr.phase_code "
					+ " AND flv2.lookup_type           =  'CP_PHASE_CODE' "
					+ " AND flv2.enabled_flag          =  'Y' "
					+ " AND flv2.view_application_id   <> 0 "
					+ "AND fu.user_id                 =  fcr.requested_by "
					+ "AND fcr.concurrent_program_id  =  fcpt.concurrent_program_id "
					+ "AND fcr.responsibility_id      =  frt.responsibility_id "
					+ "AND frt.LANGUAGE               =  USERENV ('LANG') "
					+ "AND fcpt.LANGUAGE              =  USERENV ('LANG') "
					+ " AND fcr.request_id = '"
					+ reqId
					+ "'"
					+ "ORDER BY fcr.request_date DESC";

			// LOG_R.info("query==" + sql1);
			BeanPropertyRowMapper rm1 = new BeanPropertyRowMapper(
					QueryStatus.class);
			List<QueryStatus> result = jdbcTemplate.query(sql1, rm1);
			if (result != null && !result.isEmpty())
				return result.get(0);

			/*
			 * List<String> certs = jdbcTemplate.queryForList(sql1,
			 * String.class); if (certs.isEmpty()) { return null; } else {
			 * return certs.get(0); }
			 */
			// return null;
		} catch (Exception e) {
			LOG_R.error("Exception occured ::" + e);

		}
		return null;
	}// CLASS END

	public void saveCPHistory(String concurrentName, String reqId) {
		String sql = "select REQUEST_ID,REQUEST_DATE,STATUS_CODE status,LOGFILE_NAME,OUTFILE_NAME from fnd_concurrent_requests where REQUEST_ID="
				+ reqId;
		BeanPropertyRowMapper rm1 = new BeanPropertyRowMapper(CPDetails.class);
		List<CPDetails> result = jdbcTemplate.query(sql, rm1);
		CPDetails cpDetails = result.get(0);
		cpDetails.setUser_concurrent_program_name(concurrentName);
		cpDetails.setRequest_id(reqId);
		if (cpDetails.getStatus().equalsIgnoreCase("C")) {
			cpDetails.setStatus("Normal");
		} else if (cpDetails.getStatus().equalsIgnoreCase("E")) {
			cpDetails.setStatus("Error");
		} else if (cpDetails.getStatus().equalsIgnoreCase("G")) {
			cpDetails.setStatus("Warning");
		}
		 else if (cpDetails.getStatus().equalsIgnoreCase("I")) {
				cpDetails.setStatus("Inactive");
			}
		
		//Changing Date Format
		String date = cpDetails.getRequest_date().substring(0, 10);// Converting
																	// Date
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
		Date date2 = null;
		try {
			date2 = formatter.parse(date.substring(0, 10));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String DATE_FORMAT = "dd/MMM/yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		//End of Changing Date Format
		cpDetails.setRequest_date(sdf.format(date2));//Setting Date Value

		Object[] objValues = new Object[] {
				cpDetails.getUser_concurrent_program_name(),
				cpDetails.getRequest_id(), cpDetails.getStatus(),
				cpDetails.getRequest_date(), cpDetails.getLogfile_name(),
				cpDetails.getOutfile_name(), cpDetails.getProcess_time() };
		int[] objTypes = new int[] { Types.VARCHAR, Types.NUMERIC,
				Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR };
		String sql2 = "insert into cphistory (user_concurrent_program_name,request_id,"
				+ "status ,request_date ,"
				+ "logfile_name ,outfile_name ,"
				+ "process_time) VALUES(?,?,?,?,?,?,?)";
		int i = jdbcTemplate.update(sql2, objValues);
		LOG_R.info("Inserted" + i);

	}

} // CLASS BODY END
