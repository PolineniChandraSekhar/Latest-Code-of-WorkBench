package com.avalon.workbench.repository.concurrentReport;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.avalon.workbench.beans.concurrntReport.Parameters;
import com.avalon.workbench.beans.user.User;
import com.avalon.workbench.repository.exception.WorkbenchDataAccessException;
import com.mysql.jdbc.log.Log;

@Repository("concurrentReportParamsRepositoryImpl")
public class ConcurrentReportParamsRepositoryImpl implements
		ConcurrentReportParamsRepository {
	protected static final Logger LOG_R = Logger
			.getLogger(ConcurrentReportParamsRepositoryImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Parameters> getParams(String progName)
			throws WorkbenchDataAccessException {
		try {
			/*String sql = "SELECT  fdfcuv.column_seq_num ,fdfcuv.form_left_prompt , fdfcuv.enabled_flag ,        fdfcuv.required_flag ,        fdfcuv.display_flag ,        fdfcuv.flex_value_set_id ,        ffvs.flex_value_set_name ,       Fdfcuv.Default_Value   ,Flv.meaning valueType " +//added Flv.meaning ValueType On 29/5/2015
					"   FROM        fnd_concurrent_programs fcp,        fnd_concurrent_programs_tl fcpl,        fnd_descr_flex_col_usage_vl fdfcuv,        Fnd_Flex_Value_Sets Ffvs,        Fnd_Lookup_Values Flv     WHERE        fcp.concurrent_program_id = fcpl.concurrent_program_id        AND    fcpl.user_concurrent_program_name = '"
					+ progName
					+ "'        AND    fdfcuv.descriptive_flexfield_name = '$SRS$.'                 || fcp.concurrent_program_name        AND    ffvs.flex_value_set_id = fdfcuv.flex_value_set_id        AND    flv.lookup_type(+) = 'FLEX_DEFAULT_TYPE'        AND    flv.lookup_code(+) = fdfcuv.default_type        AND    fcpl.LANGUAGE = USERENV ('LANG')        And    Flv.Language(+) = Userenv ('LANG')  AND fdfcuv.enabled_flag='Y'      ORDER BY fdfcuv.column_seq_num";
			*/
			String sql="SELECT fdfcuv.column_seq_num ,fdfcuv.form_left_prompt ,fdfcuv.enabled_flag ,fdfcuv.required_flag ,fdfcuv.display_flag ,fdfcuv.flex_value_set_id ,ffvs.flex_value_set_name , Ffvs.validation_type value_set_type,Fdfcuv.Default_Value ,"
                        +" Flv.meaning valueType FROM fnd_concurrent_programs fcp,fnd_concurrent_programs_tl fcpl,fnd_descr_flex_col_usage_vl fdfcuv,Fnd_Flex_Value_Sets Ffvs,Fnd_Lookup_Values Flv"
                        +" WHERE fcp.concurrent_program_id       = fcpl.concurrent_program_id AND fcpl.user_concurrent_program_name = '"+progName+"'  AND fdfcuv.descriptive_flexfield_name = '$SRS$.' || fcp.concurrent_program_name AND ffvs.flex_value_set_id = fdfcuv.flex_value_set_id "
                        +"AND flv.lookup_type(+)     = 'FLEX_DEFAULT_TYPE' AND flv.lookup_code(+)     = fdfcuv.default_type AND fcpl.LANGUAGE          = USERENV ('LANG') AND Flv.Language(+)        = Userenv ('LANG') AND fdfcuv.enabled_flag    ='Y' ORDER BY fdfcuv.column_seq_num";
			LOG_R.info("sql===" + sql);
			BeanPropertyRowMapper rm = new BeanPropertyRowMapper(
					Parameters.class);
			List<Parameters> result = jdbcTemplate.query(sql, rm);
			return result;
		} catch (Exception e) {
			LOG_R.error("Exception occured ::" + e);
			throw new WorkbenchDataAccessException(e);
		}
	}

	public int getParamValue(String respName) {
		
		int bgId=0;
		String bgName=null;
		String sql="SELECT"
					+"	psp.SECURITY_PROFILE_NAME "
					+"	FROM applsys.fnd_responsibility_tl tr," 
					+"	applsys.fnd_responsibility r," 
					+"	hr.per_security_profiles psp,"
					+"	apps.per_business_groups pbg,"
					+"	fnd_profile_options_vl fpovl,"
					+"	applsys.fnd_profile_option_values fpov," 
					+"	applsys.fnd_profile_options fpo, "
					+"	applsys.fnd_profile_options_tl fpotl "
					+"	WHERE r.responsibility_id = tr.responsibility_id "
					+"	AND r.application_id = tr.application_id "
					+"	AND fpov.PROFILE_OPTION_ID = fpovl.PROFILE_OPTION_ID "
					+"	AND fpov.level_value = r.responsibility_id "
					+"	AND psp.BUSINESS_GROUP_ID = pbg.BUSINESS_GROUP_ID (+) "
					+"	AND fpov.profile_option_value = TO_CHAR(psp.SECURITY_PROFILE_id)" 
					+"	AND fpov.level_id = 10003 "
					+"	AND fpo.profile_option_id = fpov.profile_option_id "
					+"	AND fpotl.profile_option_name = fpo.profile_option_name "
					+"	AND tr.responsibility_name='"+respName+"'"
					+"	AND fpotl.user_profile_option_name = 'HR: Security Profile'";
		LOG_R.info("sql===" + sql);
		List<String> bgNames=jdbcTemplate.queryForList(sql,String.class);
		LOG_R.info("BGNames-----------:"+bgNames.size());
		if(bgNames.size()>0){
		bgName=bgNames.get(0);
		}
		LOG_R.info("BG Name-------------"+bgName);
		if(bgName!=null){
		String sql2="select	business_group_id"
					+" from per_business_groups"
					+" where lower(name) like lower('"+bgName+"')";
		List<Integer> bgIds=jdbcTemplate.queryForList(sql2, Integer.class);
		LOG_R.info("sql===" + sql2);
		LOG_R.info("BG Id-------------"+bgIds.size());
		if(bgIds.size()>0){		
		bgId=bgIds.get(0);
		}
		}
		return bgId;
	}

	public List<String> getInDependentvalueSet(String flexVSName) {
		
		String sql="SELECT flex_value  FROM applsys.fnd_flex_values fv,applsys.fnd_flex_value_sets fvs  WHERE fvs.flex_value_set_id = fv.flex_value_set_id"
                    +" AND fvs.flex_value_set_name = '"+flexVSName+"'";
                    
		
		List<String> values=jdbcTemplate.queryForList(sql,String.class);
		return values;
		
		
	}

	public List<String> getDependantVS(String flexVS, String dependingOn) {
		List<String> values = new ArrayList<String>();
		try{
		String sql="SELECT fv.flex_value FROM applsys.fnd_flex_values fv,applsys.fnd_flex_value_sets fvs,FND_FLEX_VALUES_VL ffvv WHERE fvs.flex_value_set_id = fv.flex_value_set_id"+
					" and fv.flex_value_id=ffvv.flex_value_id and Fv.parent_flex_value_low='"+dependingOn+"' AND fvs.flex_value_set_name = '"+flexVS+"'";
		LOG_R.info("sql==="+sql);
		values=jdbcTemplate.queryForList(sql,String.class);
		return values;
		}
		catch(Exception e){
			LOG_R.info("Erro"+e);
			return values;
		}
	
	}

	public String getQuery(String f_vs_id) {
		String query=null;
		String sql="select value_column_name,Application_table_name,Additional_where_clause from FND_FLEX_VALIDATION_TABLES where flex_value_set_id='"+f_vs_id+ "'";
		LOG_R.info("sql queris============"+sql);
		SqlRowSet rowSet=jdbcTemplate.queryForRowSet(sql);
		while(rowSet.next()){
		String columnName= rowSet.getString(1);
		String tableName=rowSet.getString(2);
		String whereClause=rowSet.getString(3);
		LOG_R.info("Column name="+columnName+" Table Name="+tableName+" where Clausse"+whereClause);
		query="select "+columnName+" from "+tableName+" "+whereClause;
		LOG_R.info("Query is"+query);
		}
		
		
		return query;
	}

	public List<String> getTableTypeVS(String query) {
		
		List<String> values=new ArrayList<String>();
		values=jdbcTemplate.queryForList(query,String.class);
		return values;
	}
	

	}
