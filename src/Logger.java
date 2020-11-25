import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
	final private String logPath = "logdata.txt";
	
	private String[] names = {"", ""};
	private String[] numbers = {"", ""};
	
	// log 事件名称
	final public String Login = "Login";
	final public String DropPara = "DropPara";
	final public String CancelPara = "CancelPara";
	final public String ClickBtn = "ClickBtn";
	final public String EditPow = "EditPow";
	final public String EditVal = "EditVal";
	final public String Scrollbar = "Scrollbar";
	final public String ReRef = "ReRef";
	final public String g = "g";
	final public String cost = "c";
	
	// log 事件内容代码
	final public String nextPage = "nextPg";
	final public String previousPage = "previousPg";
	final public String setPow = "setPow";
	final public String confrimPow = "confrimPow";
	final public String cancelPow = "cancelPow";
	final public String setVal = "setVal";
	final public String confirmVal = "confirmVal";
	final public String cancelVal = "cancelVal";
	final public String run = "run";
	final public String complete = "complete";
	
	/**
	 * 构造函数
	 */
	Logger() {
		try {
			File file = new File(logPath);
			
			if ( !file.exists() ) {
				file.createNewFile();
			}
			
			// true => write 模式
			FileWriter fileWriter = new FileWriter(file.getName());
			fileWriter.write("");
			fileWriter.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 显示日志信息
	 * @param action
	 * @param content
	 */
	public void log(String action, String content) {
		String logData = getLogContent(action, content);
		System.out.println("Log: " + logData);
		
		try {
			File file = new File(logPath);
			
			if ( !file.exists() ) {
				file.createNewFile();
			}
			
			// true => append 模式
			FileWriter fileWriter = new FileWriter(file.getName(), true);
			fileWriter.write(logData);
			fileWriter.write(System.getProperty("line.separator"));
			fileWriter.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	} // end log
	
	/**
	 * 保存登录的用户信息
	 * @param num
	 * @param name
	 * @param number
	 */
	public void setUserInfo(int num, String name, String number) {
		names[num - 1] = name;
		numbers[num - 1] = number;
	}
	
	/**
	 * 关闭log时候修改logdata文件名
	 * @param flag 0 - 非正常结束 1 - 正常结束
	 */
	public void finish(int flag) {
//		return;
		File oldfile = new File(logPath); 
		if ( !oldfile.exists() ) {
            return; // 重命名文件不存在
        }
		
		String userNames = "";
		if (names[1] != "") userNames = names[0] + "&" + names[1];
		String newFileName = "log_" + userNames + "_" + getTimeStamp() + ".txt";
		if (flag == 1) {
			newFileName = "f" + newFileName;
		}
		File newFile = new File(newFileName);
		oldfile.renameTo(newFile);
	}
	
	/**
	 * 组装日志信息并返回
	 * @param stuID
	 * @param action
	 * @param content
	 * @return
	 */
	private String getLogContent(String action, String content) {
		return getTimeStamp() + " " + action + " " + content;
	}
	
	/**
	 * 返回时间戳字符串
	 * @return
	 */
	private String getTimeStamp() {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
		Date date = new Date();
		String timeStamp = sdf.format(date);
		return timeStamp;
	}
	
} // end Class Logger
