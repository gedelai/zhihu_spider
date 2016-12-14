package crawl01;

import java.util.*;

public class ZhiHu {
	String question; 
	String zhihuUrl; 	
	ArrayList<String> answers=new ArrayList<String>();
	
	
	public ZhiHu(){
		question="";
		zhihuUrl="";		
		answers=new ArrayList<String>();
		
	}
	
	

	public String toString(){
		return "问题："+question+"\n链接："+zhihuUrl+"\n答案："+answers+"";
	}

}
