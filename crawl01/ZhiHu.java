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
		return "���⣺"+question+"\n���ӣ�"+zhihuUrl+"\n�𰸣�"+answers+"";
	}

}
