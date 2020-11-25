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
	 * ���캯�� - ��main����
	 * @param main
	 */
	PageLogin(Main main) {
		this.main = main;
		this.logger = main.logger;
	}
	
	/**
	 * ��װ������������һҳ��JPanel
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
	 * ��װloginҳ������������
	 */
	private void initLoginInput() {
		// 1. ��Ա1 ��Ϣ��
		JLabel labelName1 = new JLabel("С���Ա1 ����:");
		labelName1.setFont(new Font("΢���ź�", Font.PLAIN, 30));
		labelName1.setBounds(left, top, 230, 40);
		
		inputName1 = new JTextField(10);
		inputName1.setFont(new Font("΢���ź�", Font.PLAIN, 20));
		inputName1.setBounds(left + 230, top, 120, 40);
		
		JLabel labelNum1 = new JLabel("ѧ��:");
		labelNum1.setFont(new Font("΢���ź�", Font.PLAIN, 30));
		labelNum1.setBounds(left + 360, top, 100, 40);
		
		inputNum1 = new JTextField(15);
		inputNum1.setFont(new Font("΢���ź�", Font.PLAIN, 20));
		inputNum1.setBounds(left + 440, top, 180, 40);
		
		pageLogin.add(labelName1);
		pageLogin.add(inputName1);
		pageLogin.add(labelNum1);
		pageLogin.add(inputNum1);
		
		// 2. ��Ա2 ��Ϣ��
		int gap = 70;
		JLabel labelName2 = new JLabel("С���Ա2 ����:");
		labelName2.setFont(new Font("΢���ź�", Font.PLAIN, 30));
		labelName2.setBounds(left, top + gap, 230, 40);
		
		inputName2 = new JTextField(10);
		inputName2.setFont(new Font("΢���ź�", Font.PLAIN, 20));
		inputName2.setBounds(left + 230, top + gap, 120, 40);
		
		JLabel labelNum2 = new JLabel("ѧ��:");
		labelNum2.setFont(new Font("΢���ź�", Font.PLAIN, 30));
		labelNum2.setBounds(left + 360, top + gap, 100, 40);
		
		inputNum2 = new JTextField(15);
		inputNum2.setFont(new Font("΢���ź�", Font.PLAIN, 20));
		inputNum2.setBounds(left + 440, top + gap, 180, 40);
		
		pageLogin.add(labelName2);
		pageLogin.add(inputName2);
		pageLogin.add(labelNum2);
		pageLogin.add(inputNum2);
		
	} // end initLoginInput
	
	/**
	 * ��װloginҳ�ĵ�¼��ť �����¼�����
	 */
	private void initLoginBtn() {
		// ��װlogin��ť
		JButton loginBtn = new JButton("��¼");
		loginBtn.setFont(new Font("΢���ź�", Font.PLAIN, 20));
		loginBtn.setBounds(left + 270, top + 240, 90, 45);
		pageLogin.add(loginBtn);
		
		// login��ť���¼�����
		// �����¼��ť: ����Ƿ�������; ����log; ������һҳ
		loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	// 1. ����Ƿ������꣬��������ʾ����
            	String name1 = inputName1.getText(),
            		   name2 = inputName2.getText();
            	String num1 = inputNum1.getText(),
            		   num2 = inputNum2.getText();
            	boolean flag = checkInput(name1, name2, num1, num2);
            	if (!flag) return;
            	
            	// 2. ��¼��¼��Ϣ logger
            	logger.setUserInfo(1, name1, num1);
            	logger.setUserInfo(2, name2, num2);
            	logger.log(logger.Login, name1 + ":" + num1);
            	logger.log(logger.Login, name2 + ":" + num2);
            	
            	// 3. ������һҳ
            	main.cardLayout.next(main.outerPanel);
            }
        });
		
	} // end initLoginBtn
	
	/**
	 * ���������Ϣ�Ƿ�����
	 * @param name1
	 * @param name2
	 * @param num1
	 * @param num2
	 * @return
	 */
	private boolean checkInput(String name1, String name2, String num1, String num2) {
		if (name1.equals("") || name2.equals("")) {
			showWarning("Warning", "�벹ȫ����!");
			return false;
		} else if (num1.equals("") || num2.equals("")) {
			showWarning("Warning", "�벹ȫѧ��!");
			return false;
		} 
		return true;
	}
	
	/**
	 * ��ʾ�����
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
