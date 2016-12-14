package crawl01;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZhiHuSpider {

	/**
	 * @param args
	 * @author Santiago
	 * @category 爬虫入门1.0
	 */
	public static void main(String[] args) {
		
		
		 //1， 确定要访问的链接，读取网页源代码
	    String url = "https://www.zhihu.com/explore/recommendations";	    
	    String content=webFinder(url);
		

		
	    /* 网页源代码范例：
	     * <a class="question_link" href="/question/53258688/answer/135275596" data-id="13522119">
	     *  蒸汽机是怎么输给内燃机的？
	     *   </a>
	     */
	  
	    //2，分析网页源代码，找到自己目标内容，确定正则匹配
		String pattern="question_link.+?href=\"(.+?)\".+?>\n(.+?)\n<";		
		
		
		//3,匹配content和pattern，得到结果-----result
		ArrayList<ZhiHu> result=regexFinder(content,pattern);
		
		
		//4,将匹配得到的结果写入文件或者打印到控制台
		writeResutl(result.toString());
		System.out.println(result.toString());
		
		
		
	}

	
	
	/*
	 * 正则表达式和网页内容进行匹配，返回需要的内容
	 * 
	 * . 代表任意字符，      +代表匹配一个或更多字符，          ？代表非贪婪匹配
	 * 
	 * Pattern :一个Pattern是一个正则表达式经编译后的表现模式。 
     * Matcher: 一个Matcher对象是一个状态机器，它依据Pattern对象做为匹配模式对字符串展开匹配检查。 
     *  首先一个Pattern实例订制了一个所用语法与PERL的类似的正则表达式经编译后的模式，
     *  然后一个Matcher实例在这个给定的Pattern实例的模式控制下进行字符串的匹配工作。 
	 */
	private static ArrayList<ZhiHu> regexFinder(String target, String patStr) {
		
		ArrayList<ZhiHu> resultList=new ArrayList<ZhiHu>();
		
		Pattern pattern=Pattern.compile(patStr);
		Matcher matcher=pattern.matcher(target);
		
		boolean isFind=matcher.find();

		
		while(isFind){
			
			ZhiHu zhiHu=new ZhiHu();
			zhiHu.question=matcher.group(2);
			zhiHu.zhihuUrl="https://www.zhihu.com"+matcher.group(1);
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
			// 将string转成url对象
			URL realUrl = new URL(url);
			// 初始化一个链接到那个url的连接
			URLConnection connection = realUrl.openConnection();
			// 开始实际的连接
			connection.connect();
			// 初始化 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
			// 用来临时存储抓取到的每一行的数据
			String line;
			while ((line = in.readLine()) != null)
			{
				// 遍历抓取到的每一行并将其存储到result里面
				result += line + "\n";
			}
		} catch (Exception e)
		{
			System.out.println("请求出现异常！" + e);
			e.printStackTrace();
		} // 使用finally来关闭输入流
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
		System.out.println(result);
		
		
		return result;

		
	}
	
	
	
	
	/*
	 * 爬取到的所有内容写出到文件中
	 * 
	 * 缓冲字符（BufferedWriter ）是一个字符流类来处理字符数据。不同于字节流（数据转换成字节），你可以直接写字符串，数组或字符数据保存到文件。
	 */
	private static boolean writeResutl(String result) {
		
		
		try {

			   //此路径为保存到本地文件的路径
			   File file = new File("C:\\Users\\IBM\\Desktop\\sprider.txt");

			  
			   if (!file.exists()) {
			    file.createNewFile();
			   }

			   FileWriter fw = new FileWriter(file.getAbsoluteFile());
			   BufferedWriter bw = new BufferedWriter(fw);
			   bw.write(result);
			   bw.close();

			   System.out.println("嘻嘻，写入成功！");

			  } catch (IOException e) {
			   e.printStackTrace();
			  }
		
		return true;
	}
	
	
	
}
