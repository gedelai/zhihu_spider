package crawl02;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZhiHu {
	String question;                                    //��������
	String zhihuUrl;                                    //�����Ӧ������
	String questionDesc;                                //��������
	ArrayList<String> answers=new ArrayList<String>();  //���д�
	
	
	public ZhiHu(String url){
		question="";
		zhihuUrl="";
		questionDesc="";
		answers=new ArrayList<String>();
		
		
		
		if(convertUrl(url)){
			Pattern pattern;
			Matcher matcher;
			
			String content=ZhiHuSpider.webFinder(zhihuUrl);
			
			//ƥ������:   ������ zm-editable-content">���ĸ���׼ȥ������ͬ�Գ����ı��֣�</span>
			pattern=Pattern.compile("zm-editable-content\">(.+?)</span>");
			matcher=pattern.matcher(content);
			if(matcher.find()){
				question=matcher.group(1);
			}
			
			
			//ƥ����������:   ������ zm-editable-content">���ڶԳ���𣬿�����MSCI��Ϊbenchmark�𣿻���Ӧ����libor rate?����˵�Գ����û��benchmark?</div>
			pattern=Pattern.compile("zm-editable-content\">(.+?)</div>");
			matcher=pattern.matcher(content);
			if(matcher.find()){
				questionDesc=matcher.group(1);
			}
			
			//ƥ�����д�
			pattern=Pattern.compile("zm-editable-content clearfix\">\n(.+?)\n</div>");
			matcher=pattern.matcher(content);
			while(matcher.find()){
				answers.add(matcher.group(1));
				matcher.find();
			}
			
																		
		}
	}
	
	public boolean convertUrl(String url) {
		//���磺 question/35213483/answer/2423323  תΪ https://www.zhihu.com/question/35213483
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
		return "���⣺"+question+"\n���ӣ�"+zhihuUrl+"\n�𰸣�"+answers;
	}

}
