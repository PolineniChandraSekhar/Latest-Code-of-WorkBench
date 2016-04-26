package com.avalon.workbench.beans.concurrntReport;

import java.util.List;

public class Parameters {
	String column_seq_num,form_left_prompt,enabled_flag,required_flag,display_flag,flex_value_set_id,flex_value_set_name,Default_Value,
	       valueType,value_set_type;//ValueType is meaning of column in DB.
	List<String> flex_Values, table_VS;
	

	public String getFlex_value_set_name() {
		return flex_value_set_name;
	}

	public List<String> getTable_VS() {
		return table_VS;
	}

	public void setTable_VS(List<String> table_VS) {
		this.table_VS = table_VS;
	}

	public List<String> getFlex_Values() {
		return flex_Values;
	}

	public void setFlex_Values(List<String> flex_Values) {
		this.flex_Values = flex_Values;
	}

	public String getValue_set_type() {
		return value_set_type;
	}

	public void setValue_set_type(String value_set_type) {
		this.value_set_type = value_set_type;
	}

	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	

	public String getColumn_seq_num() {
		return column_seq_num;
	}

	public void setColumn_seq_num(String column_seq_num) {
		this.column_seq_num = column_seq_num;
	}

	public String getEnabled_flag() {
		return enabled_flag;
	}

	public void setEnabled_flag(String enabled_flag) {
		this.enabled_flag = enabled_flag;
	}

	public String getRequired_flag() {
		return required_flag;
	}

	public void setRequired_flag(String required_flag) {
		this.required_flag = required_flag;
	}

	public String getDisplay_flag() {
		return display_flag;
	}

	public void setDisplay_flag(String display_flag) {
		this.display_flag = display_flag;
	}

	public String getFlex_value_set_id() {
		return flex_value_set_id;
	}

	public void setFlex_value_set_id(String flex_value_set_id) {
		this.flex_value_set_id = flex_value_set_id;
	}

	public String getFlexVSName() {
		return flex_value_set_name;
	}

	public void setFlex_value_set_name(String flex_value_set_name) {
		this.flex_value_set_name = flex_value_set_name;
	}

	public String getDefault_Value() {
		return Default_Value;
	}

	public void setDefault_Value(String default_Value) {
		Default_Value = default_Value;
	}

	public String getPrompt() {
		return form_left_prompt;
	}

	public String getForm_left_prompt() {//second getter
		return form_left_prompt;
	}

	public void setForm_left_prompt(String form_left_prompt) {
		this.form_left_prompt = form_left_prompt;
	}


}
