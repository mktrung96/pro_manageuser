

package ultis;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class DatabaseProperties {
	// Khai báo thuộc tính data
	private static Map<String, String> data = new HashMap<String, String>();

	/**
	 * Đọc file properties và bỏ vào trong map
	 */
	static {
		// Khởi tạo đối tượng Properties
		Properties properties = new Properties();
		try {
			// Đọc file và lưu vào Properties
			properties.load(new InputStreamReader(
					Thread.currentThread().getContextClassLoader().getResourceAsStream("database.properties"),
					"UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Lấy giá trị key và value trong Properties để ghi vào Map data
		Enumeration<String> enumeration = (Enumeration<String>) properties.propertyNames();
		while (enumeration.hasMoreElements()) {
			String key = (String) enumeration.nextElement();
			data.put(key, properties.getProperty(key));
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
		// kiểm tra nếu key có trong data thì lấy giá trị value
		if (data.containsKey(key)) {
			value = (String) data.get(key);
		}
		// trả về giá trị value với lấy
		return value;
	}
}
