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
	 * 构造函数 - 绑定main对象
	 * @param main
	 */
	PageQuestion(Main main) {
		this.main = main;
		this.logger = main.logger;
	}
	
	/**
	 * 组装并返回整个第一页的JPanel
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
	 * 组装各个问题
	 */
	private void initQuestions() {
		// 1. 标题
		JLabel title = new JLabel("请回答以下问题:");
		title.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		title.setBounds(left - 20, top, 150, 40);
		pageQuestion.add(title);
		
		String[] questions = {
			"请填写你认为最优的一组模型缩比、风洞截面尺寸、气流速度与气流温度参数",
			"请分析各参数对实验成本的影响规律",
			"总结能够降低模型实验成本的设计思路，并简要阐述理由"
		};
		
		int y = top + 55;
		textAreas = new JTextArea[questions.length];
		for (int i = 0; i < questions.length; i++) {
			// 添加问题
			JLabel label = new JLabel((i + 1) + ". " + questions[i]);
			label.setFont(new Font("微软雅黑", Font.PLAIN, 16));
			label.setBounds(left, y, 600, 30);
			
			// 添加对应的文本区域 - 置于JScrollPane之中
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
	 * 组装按钮以及相应的事件处理
	 */
	private void initBtn() {
		// 组装prevBtn按钮
		JButton prevBtn = new JButton("上一步");
		prevBtn.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		prevBtn.setBounds(left + 210, top + 410, 90, 45);
		pageQuestion.add(prevBtn);
		
		// 组装complete按钮
		JButton completeBtn = new JButton("完成");
		completeBtn.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		completeBtn.setBounds(left + 310, top + 410, 90, 45);
		pageQuestion.add(completeBtn);
		
		// prevBtn按钮的事件处理
		prevBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.cardLayout.previous(main.outerPanel);
                logger.log(logger.ClickBtn, logger.previousPage);
            }
        });

		// complete按钮的事件处理
		completeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	// 将回答记入日志
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
