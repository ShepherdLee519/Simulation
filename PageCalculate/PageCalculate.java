import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PageCalculate {
	private JPanel pageCalculate;
	private Main main;
	private Logger logger;
	private CustomJSlider[] sliders;
	private FormulaCalculator calculator;
	
	final private int left = 20;
	final private int top = 5;
	final private String headerTitle = "地面风洞设计"; 
	final private String imageUrl = "image/page2_image.png";
	
	/**
	 * 构造函数，绑定main对象
	 * @param main
	 */
	PageCalculate(Main main) {
		this.main = main;
		this.logger = main.logger;
	}
	
	/**
	 * 组装并返回整个运行页的JPanel
	 * @return {JPanel} pageCalculate
	 */
	public JPanel getPanel() {
		pageCalculate = new JPanel();
		pageCalculate.setLayout(null);
		
		initBasePart();
		initSelectPart();
		initTablePart();
        
        return pageCalculate;
	}
	
	/**
	 * 负责组装基础的文本与图片部分的内容
	 */
	private void initBasePart() {
		// 1. 添加header
		JLabel headerLabel = new JLabel();
		headerLabel.setText(headerTitle);
		headerLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
		headerLabel.setBounds(left, top, 300, 25);
		pageCalculate.add(headerLabel);
		
		// 2.操作说明
		String stepInfo =  "<html><body width=360 style=\"font-size:14\"><p>"
			+ "请拖动滚动条选择参数值（或直接输入），确定后点击“运行”查看结果。"
			+ "输出的流动相似与成本结果均为相对指标。流动相似指标越接近1，说明相似性越好，要求不小于0.9。"
			+ "每次运行后的结果会自动保存。点击“上一步”返回“确定相似参数”，点击“下一步”继续作答。</p></body></html>";
		JButton detailBtn = new JButton("操作说明");
		detailBtn.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		detailBtn.setBounds(left + 230, top, 90, 25);
		detailBtn.setFocusable(false);
		pageCalculate.add(detailBtn);
		// 操作说明按钮
		detailBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	JOptionPane.showMessageDialog(
                    main.jf,
                    stepInfo,
                    "操作说明",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
		
		// 3. 添加图片
		JLabel picture = new JLabel();
//		ImageIcon icon = new ImageIcon(imageUrl);
		ImageIcon icon = new ImageIcon(getClass().getResource(imageUrl));
		icon.setImage(icon.getImage()
			.getScaledInstance(318, 198, Image.SCALE_SMOOTH)); // 567 × 354
		picture.setIcon(icon);
		picture.setBounds(left, top + 30, 318, 198);
		pageCalculate.add(picture);
	}
	
	/**
	 * 负责组装参数滑块的部分
	 */
	private void initSelectPart() {
		int leftBound = left + 360,
			y = top + 30; // 参数滑块的上界
		
		// 1. 添加header
		JLabel headerLabel = new JLabel();
		headerLabel.setText("参数选择:");
		headerLabel.setFont(new Font("微软雅黑", Font.BOLD, 18));
		headerLabel.setBorder(BorderFactory.createEmptyBorder(28, 0, 30, 0));
		headerLabel.setBounds(leftBound, top, 100, 20);
		pageCalculate.add(headerLabel);
		
		// 2. 添加Slider
		int sliderNum = 4;
		double[][] sliderParameter = {
			// low upper step move default
			{0.2, 1, 0.01, 0.1, 0.6},
			{3.6, 8, 0.01, 0.1, 5.8},
			{0, 100, 0.01, 1, 30},
			{-30, 30, 0.01, 1, 0}
		};
		String[] sliderLabel = {
			"模型缩比(0.2~1)", "风洞截面宽度(x~8m, x=1.2模型宽度)",
			"来流速度(0~100m/s)", "来流温度(-30~30℃，环境温度20℃)"
		};
		CustomJSlider[] sliders = new CustomJSlider[sliderNum];
		this.sliders = sliders;
		for (int i = 0; i < sliderNum; i++) {
			sliders[i] = new CustomJSlider(
				i,
				sliderParameter[i][0], sliderParameter[i][1],
				sliderParameter[i][2], sliderParameter[i][3], sliderParameter[i][4],
				main.logger
			);
			sliders[i].setLabel(sliderLabel[i]);
			sliders[i].setPosition(pageCalculate, leftBound, y);
			y += 50;
		}
		
		// 设置slider0与slider1之间的关联
		sliders[0].slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
            	final int W = 5;
            	sliders[1].changeMin(sliders[0].getValue(), 1.2 * W);
            }
        });
		
	} // end initSelectPart
	
	/**
	 * 负责组装表格显示以及按钮设置的部分
	 */
	private void initTablePart() {
		// 1. 表格区
		JLabel headerLabel = new JLabel("输出结果");
		headerLabel.setFont(new Font("微软雅黑", Font.BOLD, 17));
		headerLabel.setBounds(left, top + 235, 300, 25);
		pageCalculate.add(headerLabel);    
		
		CustomJTable cTable = new CustomJTable(pageCalculate);
		calculator = new FormulaCalculator(sliders, cTable, main); 
        pageCalculate.add(cTable.tablePanel);
		
		// 2. 按钮区
		JButton runBtn = new JButton("运行");
		JButton prevBtn = new JButton("上一步");
		JButton nextBtn = new JButton("下一步");
		runBtn.setBounds(left + 155, top + 458, 100, 26);
		prevBtn.setBounds(left + 260, top + 458, 100, 26);
		nextBtn.setBounds(left + 365, top + 458, 100, 26);
		runBtn.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		prevBtn.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		nextBtn.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		runBtn.setFocusable(false);
		prevBtn.setFocusable(false);
		nextBtn.setFocusable(false);
		pageCalculate.add(runBtn);
		pageCalculate.add(prevBtn);
		pageCalculate.add(nextBtn);
		
		// 按钮事件处理
		//// 1. 运行按钮
		runBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("runBtn is Clicked!");
                calculator.calculate();
                cTable.addCol();
            }
        });
		//// 2. 上一页按钮
		prevBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.cardLayout.previous(main.outerPanel);
                logger.log(logger.ClickBtn, logger.previousPage);
            }
        });
		//// 3. 下一页按钮
		nextBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.cardLayout.next(main.outerPanel);
                logger.log(logger.ClickBtn, logger.nextPage);
            }
        });
		
	} // end initTablePart

} // end Class pageCalculate
