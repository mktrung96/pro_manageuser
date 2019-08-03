package ultis;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Class MessageProperties Đọc các thông tin về các câu thông báo, của hệ thống
 * 
 */
public class MessageProperties {
	// Khai báo thuộc tính message
	private static Map<String, String> message = new HashMap<String, String>();

	/**
	 * Đọc file properties và bỏ vào trong map
	 */
	static {
		Properties properties = new Properties();
		try {
			// Đọc file và lưu vào Properties
			properties.load(new InputStreamReader(
					Thread.currentThread().getContextClassLoader().getResourceAsStream("message.properties"),
					"UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Lấy giá trị key và value trong Properties để ghi vào Map error
		Enumeration<String> enumeration = (Enumeration<String>) properties.propertyNames();
		while (enumeration.hasMoreElements()) {
			String key = (String) enumeration.nextElement();
			message.put(key, properties.getProperty(key));
		}
	}

	/**
	 * Hàm đọc từ trong map
	 * 
	 * @param key: key cần đọc
	 * 
	 * @return giá trị của key
	 */
	public static String getValue(String key) {
		// Khởi tạo giá trị trả về
		String value = "";
		// kiểm tra nếu key có trong message thì lấy giá trị value
		if (message.containsKey(key)) {
			value = (String) message.get(key);
		}
		// trả về giá trị value với lấy
		return value;
	}
}
