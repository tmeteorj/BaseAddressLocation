package cn.edu.tju.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import net.sf.json.JSONObject;

public class GetXY_Baidu {
	public String getLocation(String addr) {
		try {
			addr=FormatAddress.StringFilter(addr);
			URL a2 = new URL(
					"http://api.map.baidu.com/geocoder/v2/?address=天津市"
							+ addr+ "&output=json&ak=g0uled3avfTBfUwTFWSQto2g");
			BufferedReader b4 = new BufferedReader(new InputStreamReader(
					a2.openStream(), "utf-8"));
			String res = "", rd;
			while ((rd = b4.readLine()) != null) {
				res += rd;
			}
			JSONObject json = JSONObject.fromObject(res);
			JSONObject result = json.getJSONObject("result");
			JSONObject location = result.getJSONObject("location");
			String lng = location.getString("lng");
			String lat = location.getString("lat");
			return lng + "," + lat;
		} catch (IOException e) {
			return "null,null";
		}

	}
	public void getLocationAll(String path_a, String path_b, int startline,
			int endline) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(path_a), "utf-8"));
		OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(
				path_b), "utf-8");
		String temp = null;
		int total = endline - startline + 1, cnt = 0, linenum = 0;
		int lastline = -1, nuc = 0;
		Long now = 0l;
		while ((temp = br.readLine()) != null) {
			++linenum;
			if (linenum < startline)
				continue;
			else if (linenum > endline)
				break;
			if (linenum == startline) {
				now = (new Date()).getTime();
			}
			String loc = getLocation(temp);
			if (loc.equals("null,null")) {
				if (lastline == -1) {
					lastline = linenum;
				}
				nuc++;
				if (nuc >= 10) {
					fw.append("---------------" + lastline+ "---------------\r\n");
					fw.flush();
					break;
				}
			} else {
				lastline = -1;
				nuc = 0;
			}
			String ans = temp + "," + loc + "\r\n";
			fw.append(linenum + "," + ans);
			fw.flush();
			++cnt;
			System.out.printf("%d\tCompleted about %.6f %%\r\n", linenum, cnt
					* 100.0 / total);
			Long costtime = ((new Date()).getTime() - now);
			Long ave = costtime / cnt;
			Long remain = (total - cnt) * ave;
			Long hour = (remain / 1000) / 3600;
			Long min = (remain / 1000) % 3600 / 60;
			Long sec = (remain / 1000) % 60;
			System.out.println("剩余时间: " + hour + "\t小时\t" + min + "\t分钟" + sec
					+ "\t秒");
		}
		br.close();
		fw.close();
	}
}
