package cn.snowing.tool.mp3manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

public class Filer {
	/**
	 * �����ܱ�ݵı����ļ�,ͨ��ʹ�ü��ٵ�
	 * ������������󲿷ֵĹ���
	 * 
	 * @author 2048
	 * @version 1.0
	 * 
	 * @param url     �ļ�Ŀ¼
	 * @param input   д����ַ���
	 * @param rewrite �Ƿ���д
	 * 
	 * @return
	 * 0->����ɹ�
	 * 1->����ʧ��(Ŀ¼���ɶ�)
	 * 2->����ʧ��(�ļ�����д)
	 */
	public static int Saver(String url, String input, boolean appear) {
		File file = new File(url);
		FileWriter fileWritter;
		if(!file.exists()) {
			try {
				file.createNewFile();
				
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
				return 1;
			}
		}
		try {
			fileWritter = new FileWriter(file.getAbsolutePath(),appear);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(input+"\r\n");
			bufferWritter.close();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
			return 2;
		}
		return 0;
	}
	public static void Copy(String in, String out, boolean delSource) {
		File infile = new File(in);
		File outfile = new File(out);
		if(!in.equals(out)&&!in.isEmpty()&&!out.isEmpty()) {
			try {
				Files.copy(infile.toPath(), outfile.toPath());
				if(delSource==true) {
					infile.delete();
				}
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
	}
	public static int getLine(String url) {
		File file = new File(url);
		int line = 0;
		if(!file.exists()) {
			return 0;
		} else {
			try {
				FileInputStream fis = new FileInputStream(file);
				@SuppressWarnings("resource")
				Scanner scanner = new Scanner(fis);
				while(scanner.hasNextLine()) {
					scanner.nextLine();
					line++;
				}
				fis.close();
				return line;
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
				return 0;
			}
		}
	}
	public static String getLineString(String url, int line) {
		File file = new File(url);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int nowline = 0;
			while ((tempString = reader.readLine()) != null) {
				if (nowline!=line) {
					nowline++;
				}
				if(nowline==line){
					nowline++;
					return tempString;
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return null;
	}
	public static boolean isExists(String url) {
		File file = new File(url);
		if(!file.exists())
		{
			return false;
		}
		else {
			return true;
		}
	}
	public static void delFile(String url) {
		File file = new File(url);
		if(file.exists()) {
			file.delete();
		}
		else{
			
		}
	}
	public static void createNewFile(String url) {
		File file = new File(url);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
	}
	public static boolean isEmpty(String url) {
		File file = new File(url);
		if(file.exists()) {
			if(file.length()<=0) {
				return true;
			}
			else {
				return false;
			}
		}
		return true;
	}
	public static void createNewFlode(String url) {
		File file = new File(url);
		if(!file.exists()) {
			file.mkdir();
		}
	}
}
