import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PageDescription {
	private JPanel pageDescription;
	private Main main;
	private Logger logger;
	
	final private int left = 40;
	final private int top = 10;
	
	/**
	 * ���캯�� - ��main����
	 * @param main
	 */
	PageDescription(Main main) {
		this.main = main;
		this.logger = main.logger;
	}
	
	/**
	 * ��װ������������һҳ��JPanel
	 * @return {JPanel} pageOne
	 */
	public JPanel getPanel() {
		pageDescription = new JPanel();
		pageDescription.setLayout(null);
		
		initDescription();
		initStartBtn();
        
        return pageDescription;
	}
	
	/**
	 * ��װdescriptionҳ������˵������
	 */
	private void initDescription() {
		// 1. ���� ��������
		JLabel title = new JLabel("��������");
		title.setFont(new Font("΢���ź�", Font.PLAIN, 30));
		title.setBounds(left + 240, top, 200, 40);
		pageDescription.add(title);
		
		// 2. ����
		String taskStr = "<html><body>С������һ�ܷɻ��Թ̶��ٶ��ڸ߿շ��У���ز�������ֵ����ҳ�Ҳ��б���"
			+ "	���������ԭ����Ƶ���ģ��ʵ��ģ��÷���״̬�µ��������ԣ�"
			+ "�ڱ�֤���������Ե�ͬʱ��������ʵ��ɱ�������ݷ�����: </body></html>";
		String[] tasks = {
		    "��������Ϊ���ŵ�ģ�����ȡ��綴����ߴ硢�����ٶ��������¶Ȳ���",
		    "������������ʵ��ɱ���Ӱ�����",
		    "�ܽ��ܹ�����ģ��ʵ��ɱ������˼·"
		};
		
		JLabel task = new JLabel(taskStr);
		task.setFont(new Font("΢���ź�", Font.BOLD, 19));
		task.setBounds(left, top + 35, 600, 130);
		pageDescription.add(task);
		
		int y = top + 150;
		for (int i = 0; i < tasks.length; i++) {
			JLabel label = new JLabel("(" + (i + 1) + ")" + tasks[i]);
			label.setFont(new Font("΢���ź�", Font.PLAIN, 18));
			label.setBounds(left, y, 600, 30);
			y += 30;
			pageDescription.add(label);
		}
		
		// 3. ҳ����Ϣ
		String pageStr = "����ģ������а�������ҳ��:";
		String[] pages = {
			"ȷ�����Ʋ���ģ�����",
			"����綴���ģ�����",
			"��С�������е���������"
		};
		
		JLabel page = new JLabel(pageStr);
		page.setFont(new Font("΢���ź�", Font.BOLD, 19));
		page.setBounds(left, top + 230, 600, 80);
		pageDescription.add(page);
		
		y = top + 295;
		for (int i = 0; i < pages.length; i++) {
			JLabel label = new JLabel("(" + (i + 1) + ")" + pages[i]);
			label.setFont(new Font("΢���ź�", Font.PLAIN, 18));
			label.setBounds(left, y, 600, 30);
			y += 30;
			pageDescription.add(label);
		}
		
	} // end initDescription
	
	/**
	 * ��װ��ʼ��ť�����¼����������
	 */
	private void initStartBtn() {
		// ��װstart��ť
		JButton startBtn = new JButton("��ʼ");
		startBtn.setFont(new Font("΢���ź�", Font.PLAIN, 20));
		startBtn.setBounds(left + 270, top + 400, 90, 45);
		startBtn.setFocusable(false);
		pageDescription.add(startBtn);
		
		// start��ť���¼�����
		startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	logger.log(logger.ClickBtn, logger.nextPage);
            	main.cardLayout.next(main.outerPanel);
            }
        });
	}

} // end Class PageDescription
