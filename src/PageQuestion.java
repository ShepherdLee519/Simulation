import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class PageQuestion {
	private JPanel pageQuestion;
	private Main main;
	private Logger logger;
	private JTextArea[] textAreas;
	
	final private int left = 40;
	final private int top = 10;
	
	/**
	 * ���캯�� - ��main����
	 * @param main
	 */
	PageQuestion(Main main) {
		this.main = main;
		this.logger = main.logger;
	}
	
	/**
	 * ��װ������������һҳ��JPanel
	 * @return {JPanel} pageOne
	 */
	public JPanel getPanel() {
		pageQuestion = new JPanel();
		pageQuestion.setLayout(null);
		
		initQuestions();
		initBtn();
		
        return pageQuestion;
	}
	
	/**
	 * ��װ��������
	 */
	private void initQuestions() {
		// 1. ����
		JLabel title = new JLabel("��ش���������:");
		title.setFont(new Font("΢���ź�", Font.PLAIN, 20));
		title.setBounds(left - 20, top, 150, 40);
		pageQuestion.add(title);
		
		String[] questions = {
			"����д����Ϊ���ŵ�һ��ģ�����ȡ��綴����ߴ硢�����ٶ��������¶Ȳ���",
			"�������������ʵ��ɱ���Ӱ�����",
			"�ܽ��ܹ�����ģ��ʵ��ɱ������˼·������Ҫ��������"
		};
		
		int y = top + 55;
		textAreas = new JTextArea[questions.length];
		for (int i = 0; i < questions.length; i++) {
			// �������
			JLabel label = new JLabel((i + 1) + ". " + questions[i]);
			label.setFont(new Font("΢���ź�", Font.PLAIN, 16));
			label.setBounds(left, y, 600, 30);
			
			// ��Ӷ�Ӧ���ı����� - ����JScrollPane֮��
			textAreas[i] = new JTextArea(3, 40);
			textAreas[i].setLineWrap(true);
			JScrollPane textAreaPanel = new JScrollPane(textAreas[i]);
			textAreaPanel.setBounds(left, y + 35, 600, 70);
			
			pageQuestion.add(label);
			pageQuestion.add(textAreaPanel);
			y += 115; 
		}
		
	} // end initQuestions
	
	/**
	 * ��װ��ť�Լ���Ӧ���¼�����
	 */
	private void initBtn() {
		// ��װprevBtn��ť
		JButton prevBtn = new JButton("��һ��");
		prevBtn.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		prevBtn.setBounds(left + 210, top + 410, 90, 45);
		pageQuestion.add(prevBtn);
		
		// ��װcomplete��ť
		JButton completeBtn = new JButton("���");
		completeBtn.setFont(new Font("΢���ź�", Font.PLAIN, 20));
		completeBtn.setBounds(left + 310, top + 410, 90, 45);
		pageQuestion.add(completeBtn);
		
		// prevBtn��ť���¼�����
		prevBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.cardLayout.previous(main.outerPanel);
                logger.log(logger.ClickBtn, logger.previousPage);
            }
        });

		// complete��ť���¼�����
		completeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	// ���ش������־
            	for (int i = 0; i < textAreas.length; i++) {
            		String text = textAreas[i].getText();
            		logger.log("Q" + (i + 1), "\"" + text + "\"");
            	}
            	
            	logger.log(logger.ClickBtn, logger.complete);
            	logger.finish(1);
                main.jf.dispose();
            }
        });
		
	} // end initBtn
	
} // end Class PageQuestion
