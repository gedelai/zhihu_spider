package crawl02;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZhiHu {
	String question;                                    //问题内容
	String zhihuUrl;                                    //问题对应的链接
	String questionDesc;                                //问题描述
	ArrayList<String> answers=new ArrayList<String>();  //所有答案
	
	
	public ZhiHu(String url){
		question="";
		zhihuUrl="";
		questionDesc="";
		answers=new ArrayList<String>();
		
		
		
		if(convertUrl(url)){
			Pattern pattern;
			Matcher matcher;
			
			String content=ZhiHuSpider.webFinder(zhihuUrl);
			
			//匹配问题:   范例： zm-editable-content">用哪个基准去衡量不同对冲基金的表现？</span>
			pattern=Pattern.compile("zm-editable-content\">(.+?)</span>");
			matcher=pattern.matcher(content);
			if(matcher.find()){
				question=matcher.group(1);
			}
			
			
			//匹配问题描述:   范例： zm-editable-content">对于对冲基金，可以用MSCI作为benchmark吗？还是应该用libor rate?或者说对冲基金没有benchmark?</div>
			pattern=Pattern.compile("zm-editable-content\">(.+?)</div>");
			matcher=pattern.matcher(content);
			if(matcher.find()){
				questionDesc=matcher.group(1);
			}
			
			//匹配所有答案
			pattern=Pattern.compile("zm-editable-content clearfix\">\n(.+?)\n</div>");
			matcher=pattern.matcher(content);
			while(matcher.find()){
				answers.add(matcher.group(1));
				matcher.find();
			}
			
																		
		}
	}
	
	public boolean convertUrl(String url) {
		//例如： question/35213483/answer/2423323  转为 https://www.zhihu.com/question/35213483
		Pattern pattern=Pattern.compile("question/(.+?)/answer");
		Matcher matcher=pattern.matcher(url);
		
		if(matcher.find()){
			zhihuUrl="https://www.zhihu.com/question/"+matcher.group(1);
			return true;
		}else{
			return false;
		}
		
	}

	public String toString(){
		return "问题："+question+"\n链接："+zhihuUrl+"\n答案："+answers;
	}

}
