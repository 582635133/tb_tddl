/*(C) 2007-2012 Alibaba Group Holding Limited.	

import java.text.MessageFormat;
import java.util.Map;

/**
 * ���ݿ�����URL���ɹ�����
 * 
 * @author qihao
 *
 */
public class TAtomConURLTools {

	private static MessageFormat MYSQL_URL_FORMAT = new MessageFormat("jdbc:mysql://{0}:{1}/{2}");

	private static MessageFormat ORACLE_URL_THIN_FORMAT = new MessageFormat("jdbc:oracle:thin:@{0}:{1}:{2}");

	private static MessageFormat ORACLE_URL_OCI_FORMAT = new MessageFormat(
			"jdbc:oracle:oci:@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST={0})(PORT={1})))(CONNECT_DATA=(SERVER=DEDICAT)(SERVICE_NAME={2})))");

	public static String getOracleConURL(String ip, String port, String sid, String conType) {
		String conUrl = null;
		if (checkPrams(ip, port, sid)) {
			if (TStringUtil.isBlank(conType)
					|| TAtomConstants.DEFAULT_ORACLE_CON_TYPE.equals(conType.toLowerCase().trim())) {
				conUrl = ORACLE_URL_OCI_FORMAT.format(new String[] { ip, port, sid });
			} else {
				conUrl = ORACLE_URL_THIN_FORMAT.format(new String[] { ip, port, sid });
			}
		}
		return conUrl;
	}

	public static String getMySqlConURL(String ip, String port, String dbName, Map<String, String> prams) {
		String conUrl = null;
		if (checkPrams(ip, port, dbName)) {
			conUrl = MYSQL_URL_FORMAT.format(new String[] { ip, port, dbName });
			if (null == prams || prams.isEmpty()) {
				prams = TAtomConstants.DEFAULT_MYSQL_CONNECTION_PROPERTIES;
			}
			StringBuilder sb = new StringBuilder();
			for (Map.Entry<String, String> entry : prams.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				if (TStringUtil.isNotBlank(key) && TStringUtil.isNotBlank(value)) {
					sb.append(key);
					sb.append("=");
					sb.append(value);
					sb.append("&");
				}
			}
			String pramStr = TStringUtil.substringBeforeLast(sb.toString(), "&");
			conUrl = conUrl + "?" + pramStr;
		}
		return conUrl;
	}

	private static boolean checkPrams(String ip, String port, String dbName) {
		boolean flag = false;
		if (TStringUtil.isNotBlank(ip) && TStringUtil.isNotBlank(port) && TStringUtil.isNotBlank(dbName)) {
			flag = true;
		}
		return flag;
	}
}