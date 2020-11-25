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
	 * 构造函数 - 绑定main对象
	 * @param main
	 */
	PageDescription(Main main) {
		this.main = main;
		this.logger = main.logger;
	}
	
	/**
	 * 组装并返回整个第一页的JPanel
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
	 * 组装description页的文字说明内容
	 */
	private void initDescription() {
		// 1. 标题 任务描述
		JLabel title = new JLabel("任务描述");
		title.setFont(new Font("微软雅黑", Font.PLAIN, 30));
		title.setBounds(left + 240, top, 200, 40);
		pageDescription.add(title);
		
		// 2. 任务
		String taskStr = "<html><body>小组任务：一架飞机以固定速度在高空飞行（相关参数及数值见下页右侧列表）。"
			+ "	请基于相似原理，设计地面模型实验模拟该飞行状态下的流动特性，"
			+ "在保证流动相似性的同时尽量降低实验成本。请根据仿真结果: </body></html>";
		String[] tasks = {
		    "给出你认为最优的模型缩比、风洞截面尺寸、气流速度与气流温度参数",
		    "分析各参数对实验成本的影响规律",
		    "总结能够降低模型实验成本的设计思路"
		};
		
		JLabel task = new JLabel(taskStr);
		task.setFont(new Font("微软雅黑", Font.BOLD, 19));
		task.setBounds(left, top + 35, 600, 130);
		pageDescription.add(task);
		
		int y = top + 150;
		for (int i = 0; i < tasks.length; i++) {
			JLabel label = new JLabel("(" + (i + 1) + ")" + tasks[i]);
			label.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			label.setBounds(left, y, 600, 30);
			y += 30;
			pageDescription.add(label);
		}
		
		// 3. 页面信息
		String pageStr = "本次模拟程序中包含以下页面:";
		String[] pages = {
			"确定相似参数模拟计算",
			"地面风洞设计模拟计算",
			"对小组任务中的问题作答"
		};
		
		JLabel page = new JLabel(pageStr);
		page.setFont(new Font("微软雅黑", Font.BOLD, 19));
		page.setBounds(left, top + 230, 600, 80);
		pageDescription.add(page);
		
		y = top + 295;
		for (int i = 0; i < pages.length; i++) {
			JLabel label = new JLabel("(" + (i + 1) + ")" + pages[i]);
			label.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			label.setBounds(left, y, 600, 30);
			y += 30;
			pageDescription.add(label);
		}
		
	} // end initDescription
	
	/**
	 * 组装开始按钮及其事件处理的完善
	 */
	private void initStartBtn() {
		// 组装start按钮
		JButton startBtn = new JButton("开始");
		startBtn.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		startBtn.setBounds(left + 270, top + 400, 90, 45);
		startBtn.setFocusable(false);
		pageDescription.add(startBtn);
		
		// start按钮的事件处理
		startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	logger.log(logger.ClickBtn, logger.nextPage);
            	main.cardLayout.next(main.outerPanel);
            }
        });
	}

} // end Class PageDescription
