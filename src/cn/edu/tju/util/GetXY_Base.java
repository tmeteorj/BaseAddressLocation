package cn.edu.tju.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;

import net.sf.json.JSONObject;

public class GetXY_Base {
//http://api.gpsspg.com/bs/?oid=ÎÒ¶©ÔÄµÄoid&mcc=460&mnc=00&a=34860&b=62043&hex=10&type=&to=1&output=json
	public String getXY(String oid,String mnc,String big,String small){
		try{
			URL url = new URL("http://api.gpsspg.com/bs/?oid="+oid+"&mcc=460&mnc="+mnc+"&a="+big+"&b="+small+"&hex=10&type=&to=1&output=json");
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(),"utf-8"));
			String res="";
			String line;
			while((line=br.readLine())!=null){
				res+=line;
			}
			br.close();
			
			System.out.println(res);
			JSONObject json = JSONObject.fromObject(res);
			String status=json.getString("status");
			if(status.equals("200")){
				JSONObject result=json.getJSONObject("result");
				String lat=result.getString("lat");
				String lng=result.getString("lng");
				return lng+","+lat;
			}
			else{
				return null;
			}
		}
		catch(IOException e){
			return null;
		}
	}
	public void locationLackBase(String oid,String mnc,int startline,int endline,String inputPath,String outputPath,String errorPath)throws IOException{
		BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(
				inputPath), "utf-8"));
		OutputStreamWriter resw = new OutputStreamWriter(new FileOutputStream(
				outputPath,true), "utf-8");
		OutputStreamWriter errw = new OutputStreamWriter(new FileOutputStream(
				errorPath), "utf-8");
		String temp=null;
		int linenum=0;
		while((temp=br.readLine())!=null){
			++linenum;
			if(linenum<startline)continue;
			else if(linenum>endline)break;
			String []info=temp.split(",");
			String res=getXY(oid,mnc,info[0],info[1]);
			if(res==null){
				errw.append(temp+"\r\n");
				errw.flush();
			}
			else{
				resw.append(temp+","+res+"\r\n");
				resw.flush();
			}
		}
		br.close();
		errw.close();
		resw.close();
	}
}
