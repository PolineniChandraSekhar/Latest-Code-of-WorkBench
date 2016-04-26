package com.avalon.workbench.services.graphs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Sample {
	public static void main(String[] args) {
		

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String full_date=dateFormat.format(date);
		System.out.println(full_date);
		
	}

}
