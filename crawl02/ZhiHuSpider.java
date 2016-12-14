package crawl02;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZhiHuSpider {

	/**
	 * @param args
	 * @author Santiago
	 * @category ��������2.0�����Ӵ�
	 */
	public static void main(String[] args) {
		
		
		 //1�� ȷ��Ҫ���ʵ����ӣ���ȡ��ҳԴ����
	    String url = "https://www.zhihu.com/explore/recommendations";	    
	    String content=webFinder(url);
		

		
	    /* ��ҳԴ���뷶����
	     * <a class="question_link" href="/question/53258688/answer/135275596" data-id="13522119">
	     *  ����������ô�����ȼ���ģ�
	     *   </a>
	     */
	  
	   
		//String pattern="question_link.+?href=\"(.+?)\".+?>\n(.+?)\n<";
		//String patternTitle="question_link.+?>\n(.+?)\n<";		
		
	    
	    //2��������ҳԴ���룬�ҵ��Լ�Ŀ�����ݣ�ȷ������ƥ��
		String patStr="question_link.+?href=\"(.+?)\"";
		
		//3,ƥ��content��pattern���õ����-----result
		ArrayList<ZhiHu> recoms=regexFinder(content,patStr);
		
		//4,��ƥ��õ��Ľ��д���ļ����ߴ�ӡ������̨
		writeResutl(recoms.toString());
		System.out.println(recoms.toString());
		
	}

	
	
	/*
	 * ������ʽ����ҳ���ݽ���ƥ�䣬������Ҫ������
	 *    . ���������ַ���      +����ƥ��һ��������ַ���          �������̰��ƥ��
	 */
	private static ArrayList<ZhiHu> regexFinder(String target, String patStr) {
		// TODO Auto-generated method stub
		ArrayList<ZhiHu> resultList=new ArrayList<ZhiHu>();
		Pattern pattern=Pattern.compile(patStr);
		Matcher matcher=pattern.matcher(target);
		
		boolean isFind=matcher.find();

		
		while(isFind){
			//����һ��url��Ȼ����������ӣ�����ƥ�䣬�õ����д𰸺�������
			ZhiHu zhiHu=new ZhiHu(matcher.group(1));
			
			resultList.add(zhiHu);
			
			isFind=matcher.find();
		}
		
		return resultList;
		
	}



   
	public static String webFinder(String url) {
				
		
		String result = "";
		BufferedReader in = null;
		try
		{
			
			URL realUrl = new URL(url);
			// ��ʼ��һ�����ӵ��Ǹ�url������
			URLConnection connection = realUrl.openConnection();
			// ��ʼʵ�ʵ�����
			connection.connect();
			// ��ʼ�� BufferedReader����������ȡURL����Ӧ
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
			
			String line;
			while ((line = in.readLine()) != null)
			{
				// ����ץȡ����ÿһ�в�����洢��result����
				result += line + "\n";
			}
		} catch (Exception e)
		{
			System.out.println("��������쳣��" + e);
			e.printStackTrace();
		} // ʹ��finally���ر�������
		finally
		{
			try
			{
				if (in != null)
				{
					in.close();
				}
			} catch (Exception e2)
			{
				e2.printStackTrace();
			}
		}
		
		
		return result;

		
	}
	
	
	
	
	/*
	 * ��ȡ������������д�����ļ���
	 * 
	 * �����ַ���BufferedWriter ����һ���ַ������������ַ����ݡ���ͬ���ֽ���������ת�����ֽڣ��������ֱ��д�ַ�����������ַ����ݱ��浽�ļ���
	 */
	private static boolean writeResutl(String result) {
		
		
		try {

			   //��·��Ϊ���浽�����ļ���·��
			   File file = new File("C:\\Users\\IBM\\Desktop\\sprider.txt");

			  
			   if (!file.exists()) {
			    file.createNewFile();
			   }

			   FileWriter fw = new FileWriter(file.getAbsoluteFile());
			   BufferedWriter bw = new BufferedWriter(fw);
			   bw.write(result);
			   bw.close();

			   System.out.println("������д��ɹ���");

			  } catch (IOException e) {
			   e.printStackTrace();
			  }
		
		return true;
	}
	
	
}
