package xl.codis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogAnalyzor {
	public static void main(String[] args) {
		File file  = new File("/Users/jyy/Documents/xunlei_mediaUID_150201");

		
		File file1  = new File("/Users/jyy/Documents/xunlei0201_request.txt");
		
		List<String> mz = analyzeLog(file);
		List<String> xl = analyzeLog(file1);
		
		
				
		/* Map<String,Boolean> mz = analyzeLog1(file);
		 Map<String,Boolean> xl = analyzeLog1(file1);
		
		System.out.println("here");
		
		int c = 0 ;
		for(Map.Entry<String, Boolean> entry: mz.entrySet()) {
			String key = entry.getKey();
			if ( !xl.containsKey(key) ) {
				c++;
			}
		}
		System.out.println("done1");
		
		int d = 0 ;
		for(Map.Entry<String, Boolean> entry: xl.entrySet()) {
			String key = entry.getKey();
			if ( !mz.containsKey(key) ) {
				d++;
			}
		}*/
		
		
		
		int c = 0;
		int i = 0;
		for(String s : mz ) {
			System.out.println(i++);
			if ( !xl.contains(s) ) {
				c++;
				System.out.println("c:"+c);
			}
		}
		
		int d = 0;
		//for(String s : xl ) {
			//if ( !mz.contains(s) ) {
				//d++;
			//}
		//}
		
		
		System.out.println(c);
		System.out.println(d);
		
		
	}
	
	private static Map<String,Boolean> analyzeLog1(File file) {
		Map<String,Boolean> result = new HashMap<String,Boolean>(20000000);
		BufferedReader bufferedReader = null ;
		try {
			FileReader fileReader = new FileReader(file);
			List<String> fileList = new ArrayList<String>();
			bufferedReader = new BufferedReader(fileReader);
			String line = null ;
			while ( (line = bufferedReader.readLine()) != null ) {
				result.put(line.trim(), true);
			}
			return result;
			//System.out.println(fileList);	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	private static List<String> analyzeLog(File file) {
		BufferedReader bufferedReader = null ;
		try {
			FileReader fileReader = new FileReader(file);
			List<String> fileList = new ArrayList<String>();
			bufferedReader = new BufferedReader(fileReader);
			String line = null ;
			while ( (line = bufferedReader.readLine()) != null ) {
				fileList.add(line.trim());
			}
			return fileList;
			//System.out.println(fileList);	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
