package cn.com.caogen.externIsystem.service;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.caogen.externIsystem.mode.HttpSendResult;
import cn.com.caogen.externIsystem.util.Base64Local;
import cn.com.caogen.externIsystem.util.GsonUtil;
import cn.com.caogen.externIsystem.util.RSAUtils;
import cn.com.caogen.externIsystem.util.SecurityRSAPay;
import cn.com.caogen.externIsystem.util.SimpleHttpsClient;


/**
 * 网关调用接口
 * @author swd1
 *
 */
@Controller
@RequestMapping("/api")
public class PayService extends BasePayService{


	public static final String CREATE_URL = "http://business.goldcoinpay.com/api/createPaymentOrder";
	public static final String FIND_URL = "http://business.goldcoinpay.com/api/findordidstute";


	private static final String CHAR_SET = "utf-8";

	static PrintWriter out = null;
	HttpServletResponse response = null;


	public static String orderPay(Map<String,String> map) {
		String htmlBuild="";
		try {
			String loginId = "491570696611168256";
			String json = GsonUtil.toJson(map);
			Map<String, String> param = getRequestMap(json, RSAUtils.serverPublic, RSAUtils.merprivkey, loginId);
			htmlBuild = buildRequest(param, CREATE_URL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return htmlBuild;
	}








	/**
	 * 异步通知解析(同步类似)
	 * @throws Exception

	 @SuppressWarnings("unchecked")
	 @RequestMapping(value = "/synMerchant")
	 */
	@RequestMapping("/asynMerchant")
	public void asynMerchant(HttpServletResponse response, HttpServletRequest request) {

		try {
			System.out.println("异步通知开始....");
			String encParam = request.getParameter("data");
			String sign = request.getParameter("sign");
			String signType = request.getParameter("signType");

			String verifyAndDecrypt = verifyAndDecrypt(encParam, sign, RSAUtils.serverPublic, RSAUtils.merprivkey);
			Map<String, String> respMap  = GsonUtil.formJson(verifyAndDecrypt, Map.class);
			for (String fields : respMap.keySet()) {
				if ("3".equals(respMap.get("orderState"))) {
					System.out.println("支付成功");
				}else {
					System.out.println("未支付");
				}
				System.out.println("fields="+fields);
			}
			outString(response, "success");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		outString(response, "false");
		return ;
	}

	/**
	 * 查询订单状态
	 * @param request
	 * @param response
	 * @throws Exception

	 @SuppressWarnings("unchecked")
	 @RequestMapping(value="/queryOrderInfo") */


	@RequestMapping("/queryOrderInfo")
	public void findordidstute(HttpServletResponse response, HttpServletRequest request) {
		StringBuffer sb = new StringBuffer();
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");

			String loginId = request.getParameter("loginId");
			String merOrdid = request.getParameter("merOrdid");

			Map<String, String> dataMap = new HashMap<String, String>();
			dataMap.put("loginId", loginId);
			dataMap.put("merOrdid", merOrdid);
			String json = GsonUtil.toJson(dataMap);
			Map<String, String> param = getRequestMap(json, RSAUtils.serverPublic, RSAUtils.merprivkey, loginId);

			SimpleHttpsClient httpClient= new SimpleHttpsClient();
			HttpSendResult result = null;
			String respTxt ="";
			try {
				result = httpClient.postRequest(FIND_URL, param, 30*000);
				respTxt = result.getResponseBody();
				if(result.getStatus() != 200){
					//请求服务器失败、商户出错处理
					return ;
				}
				//服务器没有返回数据或返回异常
				if(StringUtils.isEmpty(respTxt) || respTxt.contains("</html>")){
					//商户出错处理
					return;
				}
			}catch (Exception e) {
				e.printStackTrace();
			}



			Map<String, Object> map = GsonUtil.formJson(respTxt, Map.class);
			map = GsonUtil.formJson(String.valueOf(map.get("data")), Map.class);
			if (!"2001".equals(map.get("code"))) {
				sb.append(map.get("msg"));
				return;
			}

			map = GsonUtil.formJson(String.valueOf(map.get("data")), Map.class);
			String encParam = String.valueOf(map.get("data"));
			String sign = String.valueOf( map.get("sign"));
			String signType = String.valueOf( map.get("signType"));

			String verifyAndDecrypt = verifyAndDecrypt(encParam, sign, RSAUtils.serverPublic, RSAUtils.merprivkey);
			Map<String, String> respMap  = GsonUtil.formJson(verifyAndDecrypt, Map.class);

			sb.append("订单号：").append(merOrdid);
			if ("3".equals(respMap.get("orderState"))) {
				sb.append("支付成功,");
			}else {
				sb.append("未支付,");
			}
			sb.append("上游订单号:").append(respMap.get("payordid")).append(",金额：").append(respMap.get("tradeMoney"));



		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				out = response.getWriter();
				out.print(sb);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@Test
	public  void test() {
		String logid = "491570696611168256";
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("paymentName", "AlipayScan");
//		dataMap.put("paymentName", "OnlinePay");
		dataMap.put("sence", "1001");
		dataMap.put("tradeMoney", "100");
		dataMap.put("loginId", "491570696611168256");
		dataMap.put("merOrdid", System.currentTimeMillis()+"");
		dataMap.put("orderName", "ceshi");
		dataMap.put("asynNotificationUrl", "http://fe.com");
		dataMap.put("syncNotificationUrl",  "http://fe.com");

		String json = GsonUtil.toJson(dataMap);
		try {
			Map<String, String> param = getRequestMap(json, RSAUtils.serverPublic, RSAUtils.merprivkey, logid);
			HttpSendResult postRequest = new SimpleHttpsClient().postRequest(CREATE_URL, param, 30000);
			String resStr = postRequest.getResponseBody();
			System.out.println("resStr="+resStr );
		}catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * 建立请求，以表单HTML形式构造
	 *
	 * @param sParaTemp 请求参数数组
	 * @return 提交表单HTML文本
	 */
	public static String buildRequest(Map<String, String> sParaTemp, String serverUrl) {
		// 待请求参数数组
		List<String> keys = new ArrayList<String>(sParaTemp.keySet());
		StringBuffer sbHtml = new StringBuffer();
		sbHtml.append("<form id=\"paysubmit\" name=\"paysubmit\" action=\"" + serverUrl + "\" method=\"post\">");
		for (int i = 0; i < keys.size(); i++) {
			String name = (String) keys.get(i);
			String value = (String) sParaTemp.get(name);
			sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
		}

		sbHtml.append("<input type=\"submit\" value=\"paysubmit\" style=\"display:none;\"></form>");
		sbHtml.append("<script>document.forms['paysubmit'].submit();</script>");
		return sbHtml.toString();
	}

	/**
	 * 验签和解密
	 * @throws Exception
	 */
	public String verifyAndDecrypt(String encParam,String sign,String publicKey,String privateKey) throws Exception{
		if (StringUtils.isEmpty(encParam) || StringUtils.isEmpty(publicKey) || StringUtils.isEmpty(privateKey)
				|| StringUtils.isEmpty(sign)) {
			return null;
		}

		boolean b = SecurityRSAPay.verify(Base64Local.decode(encParam), Base64Local.decode(publicKey), Base64Local.decode(sign));
		String data = null;
		if (b) {
			data = new String(SecurityRSAPay.decryptByPrivateKey(Base64Local.decode(encParam), Base64Local.decode(privateKey)), "utf-8");
		}

		return data;

	}


	/**
	 * 组装请求参数
	 */
	public static Map<String, String> getRequestMap(String jsonParam,String serverPublic,String privateKey,String logid) throws Exception{
		if (StringUtils.isEmpty(jsonParam) || StringUtils.isEmpty(serverPublic) || StringUtils.isEmpty(privateKey)
				|| StringUtils.isEmpty(logid)) {
			return null;
		}


		byte by[] = SecurityRSAPay.encryptByPublicKey(jsonParam.getBytes(CHAR_SET), Base64Local.decode(serverPublic));
		String baseStrDec = Base64Local.encodeToString(by, true);

		//商户自己的私钥签名
		byte signBy[] = SecurityRSAPay.sign(by, Base64Local.decode(privateKey));
		String sign = Base64Local.encodeToString(signBy, true);

		//组装请求参数
		Map<String,String>  map = new HashMap<String, String>();

		map.put("data", baseStrDec);
		map.put("sign", sign);
		map.put("loginId", logid);
		map.put("signType", "RSA");

		return map;
	}
}
