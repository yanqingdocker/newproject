package cn.com.caogen.externIsystem.util;



import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RSAUtils {
    private static final String CHAR_SET = "utf-8";
    //public static String serverPublic ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDLnUVqY3BKiSivqQHWS70x8BLglG0FdwSdViZkn07WF8TDG5Tj/cwxi1m2aRTkB5+Yyej/t6eKYCpcSacwGw/loUa+QQzvOnR/Iba9K4IVDfDhU9/sUda0TF/7+eSRdihxGtTlLHYtF3xt06orRuBg2+aDD9Onp95y5aKni8X2wwIDAQAB";
    public static String serverPublic="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDLnUVqY3BKiSivqQHWS70x8BLglG0FdwSdViZkn07WF8TDG5Tj/cwxi1m2aRTkB5+Yyej/t6eKYCpcSacwGw/loUa+QQzvOnR/Iba9K4IVDfDhU9/sUda0TF/7+eSRdihxGtTlLHYtF3xt06orRuBg2+aDD9Onp95y5aKni8X2wwIDAQAB";
    public static String merpubkey ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDoHNH6RQZxPvGjFBD1Hw/PhNeySVV4wVBoE75ypayF7CtODb1Y5tAk29sFzVaVzjNVZrGHYylZTbZAfmSNsfxn9l+s89cNeqXwl7SOY6NG3Ss+ZkJg7iN//+SSugJHgpsD+H2OdFW5QrnFs52zSZnXc74Ehcr89gZSav3/YBuV0QIDAQAB";  //public static String merprivkey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMwpuZcMVf2ATkASd+A0PcMJgInXemPNxs84IdWML0ptu+Hp7JSOK/awC9GJdiaWTrHcsKlvK2u1nscvtZH/vUzqzV3P690zBadWrOFN1I27Fj4XkEEDMEkq26ErE6X2hwaWTOc5aaYtjycG1mJYr0OFTpr0nWWsbVEIE4HPCEyfAgMBAAECgYAMu2FmiQ9nwrOejUhKhBeB7TrF3dk1FiUa0R5TCe39D0DTQtpNHVyigrIJ/C+REniP4PpnZpZnnijrtQ72ruMoJyofd5hFaVBCQWlDKb9SVbAeM2pm7D2xbfD0971Tr7hMy6DtJhY5x1DPd8+M8GwtWoWYCqZjCj6pf95rFlYZkQJBAPv/uHOOLk2W3SO8IaF77aPgXjs0SHVwLwnq0qjIe/YTbQK9NcS5hFjFy76dLblqZk+G2auizbpSJYIbrMK7ZykCQQDPZ5HV5jubOlOVf+cCUTqd+nA1BDHDpI/pCy7NUKZFmrdVcTPd1UjiF2qm7i5sNkiW/0eAkwKkBAKWcmq0aHaHAkAVc7h1BdaFPmGG6D4IrC8Xs0LyUUoVzT4D3xydx6td0FuITykjnRNaJ0Rn7qN01EzvWjBvfwV6ZgHRaJ+1WBS5AkEAvHrNcAoTXwSJUtHx+Awjbc9aSwOtybJxyYGNHa5N+/EW7IG8dbrOyhAnrt3CuDo4i2gf7XsrqiuuwlPmHl7UXwJAKRdfirqWRrkKBet/kyeoXUjCWcQzVC7i5EVZRglRnneT05OHhaeZaLYsNGDF+lsFBhqtWsGsJmXVrDh00wnsvQ==";
    public static String merprivkey="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOgc0fpFBnE+8aMUEPUfD8+E17JJVXjBUGgTvnKlrIXsK04NvVjm0CTb2wXNVpXOM1VmsYdjKVlNtkB+ZI2x/Gf2X6zz1w16pfCXtI5jo0bdKz5mQmDuI3//5JK6AkeCmwP4fY50VblCucWznbNJmddzvgSFyvz2BlJq/f9gG5XRAgMBAAECgYAVzX87zvgH8y2nb98X508I4yWPravFqALxg8lD46QNAF4g+tFqZGPw0mYEaYFUmQXhY8ARQ5NCvDX+YOD5HlrjC0f43PyzspdeuiuLIJesFcUazuH4gbbmid19MPIpM3AJMPg6BOUqrGpSF4JVfx1SlzmlKgS0UOjZbqhh0G+4cQJBAPhKNOC4FEYcbKqp1/hxhadGMfmzpOX9ioQ7Q4KnCxzese00FMVGNAkz2ZRHoSh3iml60kuV55yiwEtQTNsjGksCQQDvUgLuIMZVgvkiAqyZUinHj4ZaZNd9xs0HuFHGxRs6mgcmAlK2lHIHlEoyHXkI5a5+W/cGEI62KME4DifsNH7TAkAoK4FQlheZnEGsftwVMvCntoChYIIeP53odNogSjxPCGyK7vEpVYlXr87U4Z3eOTDojdl2JyOYwPARf0Vf8tbvAkEAkJw82wmA7xD6W7vD8c19I3ItnBeNQxpKsByHhZV7E5kZMrV6p4rhsITBpLC8f0zSeNY3WTnOEnJj9uxO4WMDMwJBAITITxOGo0dtx7yN2KPV4Alv+Mn/SVUl0Yw8dNNlKMM5vuAiObgIoiiNSEelNDZTKOsQ7856SUj0Oa18H9dvwRs=" ;

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
    public static String verifyAndDecrypt(String encParam,String sign,String publicKey,String privateKey) throws Exception{
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
