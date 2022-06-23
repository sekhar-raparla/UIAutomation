package com.auros.utils;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ReportsManagers {
	static ExtentReports extentReports;
	private static Logger logs = LogManager.getLogger(ReportsManagers.class);

	
	/**
	 * Json based ExtentReport, title report name and if any theme type changes have
	 * to be updated in JSON files
	 */
	public static ExtentReports startJsonExtentReports() {
		if (Objects.isNull(extentReports)) {
			String reportsPath = System.getProperty("user.dir") + "/reports/AllTestResults.html";
			final File CONF = new File(System.getProperty("user.dir") + "/config/spark-config.json");
			logs.info("ExtentReports" + " invoked with " + reportsPath);
			extentReports = new ExtentReports();
			ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportsPath);
			try {
				sparkReporter.loadJSONConfig(CONF);
			} catch (IOException e) {
				e.printStackTrace();
			}
			extentReports.attachReporter(sparkReporter);
		}
		return extentReports;
	}

	/**
	 * XML based ExtentReport, title report name and if any theme type changes have
	 * to be updated in XML files
	 */
	public static ExtentReports startXmlExtentReports() {
		if (Objects.isNull(extentReports)) {
			String reportsPath = System.getProperty("user.dir") + "/reports/e2eXmlReport.html";
			final File CONF = new File(System.getProperty("user.dir") + "/config/spark-config.xml");
			logs.info("ExtentReports" + " invoked with " + reportsPath);
			extentReports = new ExtentReports();
			ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportsPath);
			try {
				sparkReporter.loadXMLConfig(CONF);
			} catch (IOException e) {
				e.printStackTrace();
			}
			extentReports.attachReporter(sparkReporter);
		}
		return extentReports;
	}
}
