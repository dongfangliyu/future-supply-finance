package cn.fintecher.sms.util.ucpaas.restDemo;

import cn.fintecher.sms.util.ucpaas.restDemo.client.JsonReqClient;

public class TestVoic {

	public static void main(String[] args) {
		
		
		JsonReqClient client = new JsonReqClient();
		try {
			String str = client.templateSMS("560f12323352f2e49230f3d8273314eb", "2f13254db878f2069fd1f8a6f5d2c279", "f63ed765a305411981be4378244f60c5", "4587", "13179640727", "564824");
			System.out.println(str);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
