package cn.edu.tju.process;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Scanner;

import cn.edu.tju.util.GetXY_Baidu;
import cn.edu.tju.util.GetXY_Base;
import cn.edu.tju.util.GetXY_QQ;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

import net.sf.json.JSONObject;
public class Main {

	public static void main(String[] args) throws IOException {
		getBaseXY();
	}
	
	public static void getBaseXY()throws IOException{
		Properties prop=new Properties();
		InputStream in=new BufferedInputStream(new FileInputStream("base_location.properties"));
		prop.load(in);
		
		String lackPath=prop.getProperty("lackbasepath");
		String resultDir=prop.getProperty("resultdir");
		String errorDir=prop.getProperty("errordir");
		String appkey=prop.getProperty("appkey");
		String mnc=prop.getProperty("mnc");
		String oid=prop.getProperty("oid");
		GetXY_Base getbase=new GetXY_Base();
		getbase.locationLackBase(oid,mnc, 1, 1000, lackPath,resultDir+"/1-1000.txt", errorDir+"/1-1000.txt");
	}
	
	public static void RunOnQQMap() throws IOException{
		GetXY_QQ util=new GetXY_QQ();
		util.getLocationAll("data/110/AFDD.txt", "data/110/AFDD_XY_QQ.txt",1,10000000);
	}
	
	public static void RunOnBaiduMap()throws IOException{
		GetXY_Baidu util=new GetXY_Baidu();
		util.getLocationAll("data/110/AFDD.txt", "data/110/AFDD_XY_Baidu_.txt",1,10000000);
	}

}
