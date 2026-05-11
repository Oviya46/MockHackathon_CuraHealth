package com.krce.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
public class ExtentManager {
    public static ExtentReports extentReports;
    public static ExtentReports getReportObject() {
        String reportPath = "reports/ExtentReport.html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
        reporter.config().setReportName("CURA Healthcare Automation Report");
        reporter.config().setDocumentTitle("Test Execution Report");
        extentReports = new ExtentReports();
        extentReports.attachReporter(reporter);
        extentReports.setSystemInfo(
                "Tester", "Oviya");
        return extentReports;
    }
}
