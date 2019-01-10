package cn.com.caogen.externIsystem.util;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class Config {
	
	//服务端公钥

	public static final String SERVER_PUBLIC_KEY ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCabJDz/66tGW6J0SBHI3zTqz+vB7lkBwEcSnnaNJ6mAZ64Garc4Ax9lcFV9aUI3/v/w7LRnhPRnMCHc9HeBFS66jPixlvk3cB/TYsVoxuQInTE/VmQDv+9cRlKYpemULGr6VoeOzAoEHz68g/YUZCjFBxbhTyOKutBoCorsAmQeQIDAQAB";// "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCabJDz/66tGW6J0SBHI3zTqz+vB7lkBwEcSnnaNJ6mAZ64Garc4Ax9lcFV9aUI3/v/w7LRnhPRnMCHc9HeBFS66jPixlvk3cB/TYsVoxuQInTE/VmQDv+9cRlKYpemULGr6VoeOzAoEHz68g/YUZCjFBxbhTyOKutBoCorsAmQeQIDAQAB";  //服务端公钥

	//商户私钥	
	
	public static final String PRIVATE_KEY ="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMgytsWRmCkZqMquA6c1Yb5AFOvNb0wV6oc11uKvziBuLml6yr6JHh1Nh2OHgHk6Cj98g8q08n2qTNyMtmxYsqLgvIGp2HVrnRr5/37aPy0z3utmGj4qFbRMzP5h8wQUJxvgIXbpfFID41SnaVzxeDAiqXqXu7x8CyXU0dTbYjEdAgMBAAECgYEAgjxLnldKz2H75m4xvjMB9QwnEAdC4hJX0cH6mtmXlI1Y8BGLl0wIJTPsYZkiwLo7NHXCHrp8Dbgd7FQ10GIbQqe4buiR4pXvuyfOLNlOHT1hojjfRy6YZbM+OpYhiyLNTjXwQ0UB/T7FRO6xBMhUNMbcCCU9yDYGx1kS5YK0OQECQQDiaSEF3bpJG8BxpAFBFFD7mrqDsfR0CN1XSlWRL0FViJgEaog6eLaxMOxApUEVup3HPtzB4PYaATrn7dvJliFhAkEA4lyb2hN5mgfo/Q4D77AZVm2gA7MqOUoGVmOUpl8XkD/Bpm1L9qQxMXbhSUWSM2z7vk6tV/ZvdikDY5NvjfxdPQJAEbj89sKWdGaRt9OIrZlzhV5lPy3M3tBa0xxkekriqFla3O2h4EHWuyQDOkQ1RggnlZMcIKkFWp/CBqmdzLfNwQJBAIm614mFOAQks/rlQDfi6kfQRAB8T0C0bc66oIPqcCfw4x8keYgEt3nlX4Z5sk9gDHzq2kYfSAqZQdqgpsu5tfECQE8jRINx2dCaNICQChfwZdhLvN9wcCZl5oZ8cAPgGqEIMxQc2frK8X0t0QvNJwBaJj5GqYkYxe+f+YKEypFEe34=";//"MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANgbmKB60bQG8zmTBmhmwEuML3cfK6YpfTsqjd45l6xfyX1elbJa3umXLuXGJWUt0kXdDy9HvOYihGncdfAi7bqZy91GFvRKlXm20QyO+ToKTVIOmyrOzcgZEneHzQYzmoY71n7+FEb80mdwOX1IO4zxsRhfEBfxCjIygR8hkiqNAgMBAAECgYAb8WWIypi26HcmqKYZdb/DhPrWMkeYkqDIt7XkWBoOhcwt078TcsCD/7Jmr3pCXOxlcDH4cRQxxaDNd14gkMOgMkg8bZ6q3QJnJPhuf8GsSYQpSyqeCsI/PApfVRxJ5+1lUoHQH75jLymIeukFUR+wJhvdahzp52YJPakVLdM6mQJBAPQn5kIyuaiHnJC3HB5PBC7EKuSc7gbP3hK612i5ewXZ63exrKPr9Y4SgkIie7bvrTXZ0999Susfp+uJHH3D1PsCQQDil2BFeYM1FBUd5aGlhvvwbXTDhfcDzI0XB7Vnpv+aC9mqnLyNzR26Aul0211XYjDiU/00CdP2ROjIj2XSf5gXAkAINBb+fmnSicvu6NgfBJSlZZ3zVHqC7BUQGAsIz8KcbbgFq4ibcWAsx95sjnANs3vkZU9DIRiVUdj4hwDYEO5lAkAPN10mrTdLbDLS1CXbyMnIZoFN1AQ3hBgBcOoUsNk/TgZq96WN3DVf1Ww6BbUp9hUUTdGWGgba5dvABCkxTpI3AkEAqXyqPrnbNVSz0tv4bBNSg+9/ko3bQfZXJSmfA2naB7p7/dReFpEyYrJwnRgEO/SJIa3ZYC9YkNvov0LLc82vZQ==";
	//版本号
	public static final String VERSION = "1.0.9";
	
	//商户号
	public static final String MERID = "201708242419044";
	
	//终端号
	public static final String TERID = "201708241911106";
		
	//域名
	public static final String REQDOMAIN = "https://www.zcmaopay.com";
	
	//订单查询
	public static final String QUERYURL = REQDOMAIN + "/gateway/queryPaymentRecord";
	
	//订单支付
	public static final String PAYURL = REQDOMAIN + "/gateway/orderPay";
	
	//获取二维码地址
	public static final String QRCODEURL = REQDOMAIN + "/gateway/orderPaySweepCode"; 


}
