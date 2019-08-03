package ultis;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Class MessageErrorProperties Đọc các thông tin về các câu thông báo lỗi của hệ thống
 */
public class MessageErrorProperties {
	// Khai báo thuộc tính error
		private static Map<String, String> error = new HashMap<String, String>();

		/**
		 * Đọc file properties và bỏ vào trong map
		 */
		static {
			Properties properties = new Properties();
			try {
				// Đọc file và lưu vào Properties
				properties.load(new InputStreamReader(
						Thread.currentThread().getContextClassLoader().getResourceAsStream("message_error_ja.properties"),
						"UTF-8"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			// Lấy giá trị key và value trong Properties để ghi vào Map error
			Enumeration<String> enumeration = (Enumeration<String>) properties.propertyNames();
			while (enumeration.hasMoreElements()) {
				String key = (String) enumeration.nextElement();
				error.put(key, properties.getProperty(key));
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
			// kiểm tra nếu key có trong error thì lấy giá trị value
			if (error.containsKey(key)) {
				value = (String) error.get(key);
			}
			// trả về giá trị value với lấy
			return value;
		}
}
