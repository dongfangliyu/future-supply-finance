package cn.fintecher.sms.utils.template;

import java.io.StringWriter;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

/**
 * 模板工具类
 * @author gubin
 *
 */
public class TemplateUtils {

	/**
	 * 根据变量和字符模板生成具体内容
	 * @param params
	 * @param template
	 * @param templateName
	 * @return content
	 */
	public static final String buildContent(Map<String, Object> params, String template, String templateName){
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.init();
        Velocity.init();
		
        VelocityContext context = new VelocityContext(params);
        StringWriter stringWriter = new StringWriter();
        Velocity.evaluate(context, stringWriter, templateName, template);
        return stringWriter.toString();
	}
	
}
