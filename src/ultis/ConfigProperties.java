
package ultis;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Class ConfigProperties Đọc các thông tin cài đặt của chương trình
 * 
 */
@SuppressWarnings("unchecked")
public class ConfigProperties {
	// Khai báo thuộc tính data
	private static Map<String, String> config = new HashMap<String, String>();

	/**
	 * Đọc file properties và bỏ vào trong map
	 */
	static {
		// Khởi tạo đối tượng Properties
		Properties properties = new Properties();
		try {
			// Đọc file và lưu vào Properties
			properties.load(new InputStreamReader(
					Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"), "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Lấy giá trị key và value trong Properties để ghi vào Map config
		Enumeration<String> enumeration = (Enumeration<String>) properties.propertyNames();
		while (enumeration.hasMoreElements()) {
			String key = (String) enumeration.nextElement();
			config.put(key, properties.getProperty(key));
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
		// kiểm tra nếu key có trong config thì lấy giá trị value
		if (config.containsKey(key)) {
			value = (String) config.get(key);
		}
		// trả về giá trị value với lấy
		return value;
	}
}
