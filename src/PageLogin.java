import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PageLogin {
	private JPanel pageLogin;
	private Main main;
	private Logger logger;

	JTextField inputName1, inputName2;
	JTextField inputNum1, inputNum2;
	
	final private int left = 30;
	final private int top = 150;
	
	/**
	 * 构造函数 - 绑定main对象
	 * @param main
	 */
	PageLogin(Main main) {
		this.main = main;
		this.logger = main.logger;
	}
	
	/**
	 * 组装并返回整个第一页的JPanel
	 * @return {JPanel} pageOne
	 */
	public JPanel getPanel() {
		pageLogin = new JPanel();
		pageLogin.setLayout(null);
		
		initLoginInput();
		initLoginBtn();
        
        return pageLogin;
	}
	
	/**
	 * 组装login页输入框相关内容
	 */
	private void initLoginInput() {
		// 1. 成员1 信息：
		JLabel labelName1 = new JLabel("小组成员1 姓名:");
		labelName1.setFont(new Font("微软雅黑", Font.PLAIN, 30));
		labelName1.setBounds(left, top, 230, 40);
		
		inputName1 = new JTextField(10);
		inputName1.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		inputName1.setBounds(left + 230, top, 120, 40);
		
		JLabel labelNum1 = new JLabel("学号:");
		labelNum1.setFont(new Font("微软雅黑", Font.PLAIN, 30));
		labelNum1.setBounds(left + 360, top, 100, 40);
		
		inputNum1 = new JTextField(15);
		inputNum1.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		inputNum1.setBounds(left + 440, top, 180, 40);
		
		pageLogin.add(labelName1);
		pageLogin.add(inputName1);
		pageLogin.add(labelNum1);
		pageLogin.add(inputNum1);
		
		// 2. 成员2 信息：
		int gap = 70;
		JLabel labelName2 = new JLabel("小组成员2 姓名:");
		labelName2.setFont(new Font("微软雅黑", Font.PLAIN, 30));
		labelName2.setBounds(left, top + gap, 230, 40);
		
		inputName2 = new JTextField(10);
		inputName2.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		inputName2.setBounds(left + 230, top + gap, 120, 40);
		
		JLabel labelNum2 = new JLabel("学号:");
		labelNum2.setFont(new Font("微软雅黑", Font.PLAIN, 30));
		labelNum2.setBounds(left + 360, top + gap, 100, 40);
		
		inputNum2 = new JTextField(15);
		inputNum2.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		inputNum2.setBounds(left + 440, top + gap, 180, 40);
		
		pageLogin.add(labelName2);
		pageLogin.add(inputName2);
		pageLogin.add(labelNum2);
		pageLogin.add(inputNum2);
		
	} // end initLoginInput
	
	/**
	 * 组装login页的登录按钮 及其事件处理
	 */
	private void initLoginBtn() {
		// 组装login按钮
		JButton loginBtn = new JButton("登录");
		loginBtn.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		loginBtn.setBounds(left + 270, top + 240, 90, 45);
		pageLogin.add(loginBtn);
		
		// login按钮的事件处理
		// 点击登录按钮: 检查是否输入完; 记入log; 进入下一页
		loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	// 1. 检查是否输入完，若无则显示警告
            	String name1 = inputName1.getText(),
            		   name2 = inputName2.getText();
            	String num1 = inputNum1.getText(),
            		   num2 = inputNum2.getText();
            	boolean flag = checkInput(name1, name2, num1, num2);
            	if (!flag) return;
            	
            	// 2. 记录登录信息 logger
            	logger.setUserInfo(1, name1, num1);
            	logger.setUserInfo(2, name2, num2);
            	logger.log(logger.Login, name1 + ":" + num1);
            	logger.log(logger.Login, name2 + ":" + num2);
            	
            	// 3. 进入下一页
            	main.cardLayout.next(main.outerPanel);
            }
        });
		
	} // end initLoginBtn
	
	/**
	 * 检查输入信息是否完整
	 * @param name1
	 * @param name2
	 * @param num1
	 * @param num2
	 * @return
	 */
	private boolean checkInput(String name1, String name2, String num1, String num2) {
		if (name1.equals("") || name2.equals("")) {
			showWarning("Warning", "请补全姓名!");
			return false;
		} else if (num1.equals("") || num2.equals("")) {
			showWarning("Warning", "请补全学号!");
			return false;
		} 
		return true;
	}
	
	/**
	 * 显示警告框
	 * @param title
	 * @param message
	 */
	private void showWarning(String title, String message) {
		JOptionPane.showMessageDialog(
            main.jf,
            message, title,
            JOptionPane.WARNING_MESSAGE
        );
	}
	
} // end Class PageLogin
