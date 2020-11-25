
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;


public class PageFormula {
	private JPanel pageFormula;
	private Main main;
	private Logger logger;
	public Parameter parameter;
	public ResultController resultController;

	final private int left = 20;
	final private int top = 10;
	final private String headerTitle = "确定相似参数"; 
	final private String imageUrl = "image/page1_image.png";
	
	/**
	 * 构造函数 - 绑定main对象
	 * @param main
	 */
	PageFormula(Main main) {
		this.main = main;
		this.logger = main.logger;
		this.parameter = new Parameter();
		parameter.main = main;
	}
	
	/**
	 * 组装并返回整个公式设定页的JPanel
	 * @return {JPanel} pageFormula
	 */
	public JPanel getPanel() {
		pageFormula = new JPanel();
		pageFormula.setLayout(null);
		
		initRightPart();
		initLeftPart();
        
        return pageFormula;
	}
	
	/**
	 * 单独负责组装第一页中的左半部分
	 */
	private void initLeftPart() {
		// 1. 添加header
		JLabel headerLabel = new JLabel();
		headerLabel.setText(headerTitle);
		headerLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
		headerLabel.setBounds(left, top, 300, 25);
		pageFormula.add(headerLabel);
		
		// 2. 添加操作说明按钮
		String stepInfo = "<html><body width=360 style=\"font-size:14\"><p>"
			+ "请从已知参数列表中将与流动相似相关的参数拖拽至空白处，"
			+ "点击“设定幂指数”与“设定参数值”填写相关数值，得出相似准则数的表达式。"
			+ "可多次填写。确认完成后请点击“下一步”。</p></body></html>";
		JButton detailBtn = new JButton("操作说明");
		detailBtn.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		detailBtn.setBounds(left + 250, top, 90, 25);
		detailBtn.setFocusable(false);
		pageFormula.add(detailBtn);
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
			.getScaledInstance(324, 202, Image.SCALE_SMOOTH)); // 567 × 354
		picture.setIcon(icon);
		picture.setBounds(left + 15, top + 30, 324, 202);
		pageFormula.add(picture);
		
		// 4. 添加操作描述
		JLabel infoLabel = new JLabel("<html><body>从右边参数列表中选择所需的参数拖动到下框"
			+ "并组合成你认为正确的相似参数计算公式:</body></html>");
		infoLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		infoLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		infoLabel.setBounds(left + 15, top + 235, 290, 50);
		pageFormula.add(infoLabel);
		
		// 5. 作为拖曳目的的文本框
		JTextArea textArea = new JTextArea(5, 27);
		textArea.setEditable(false);
		textArea.setBounds(left, top + 300, 355, 80);
		pageFormula.add(textArea);
		
		// 6. 公式显示区域
		JLabel resultLabel = new JLabel("显示公式及结果: ");
		resultLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		JLabel result = new JLabel("");
		resultController = new ResultController(result, this);
		result.setFont(new Font("微软雅黑", Font.BOLD, 15));
		resultLabel.setBounds(left + 15, top + 390, 150, 20);
		result.setBounds(left + 140, top + 390, 250, 20);
		pageFormula.add(resultLabel);
		pageFormula.add(result);
		
		// 7. 添加按钮
		initButtons();
		
	} // end initLeftPanel
	
	/**
	 * 添加相关的按钮，并为按钮设置事件处理
	 */
	private void initButtons() {
		JButton setPowerBtn = new JButton("设定幂指数");
		JButton setParameterBtn = new JButton("设定参数值");
		JButton prevBtn = new JButton("上一步");
		JButton nextBtn = new JButton("下一步");
		setPowerBtn.setBounds(left + 90, top + 430, 120, 32);
		setParameterBtn.setBounds(left + 215, top + 430, 120, 32);
		prevBtn.setBounds(left + 340, top + 430, 100, 32);
		nextBtn.setBounds(left + 445, top + 430, 100, 32);
		setPowerBtn.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		setParameterBtn.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		prevBtn.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		nextBtn.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		setPowerBtn.setFocusable(false);
		setParameterBtn.setFocusable(false);
		prevBtn.setFocusable(false);
		nextBtn.setFocusable(false);
		pageFormula.add(setPowerBtn);
		pageFormula.add(setParameterBtn);
		pageFormula.add(prevBtn);
		pageFormula.add(nextBtn);
		
		// 按钮事件处理
		//// 1. 设定幂指数按钮
		setPowerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logger.log(logger.ClickBtn, logger.setPow);
                showSetPowerDialog();
            }
        });
		//// 2.设定参数值按钮
		setParameterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	logger.log(logger.ClickBtn, logger.setVal);
                showSetValueDialog();
            }
        });
		//// 3.上一页按钮
		prevBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        		main.cardLayout.previous(main.outerPanel);
                logger.log(logger.ClickBtn, logger.previousPage);
            }
        });
		//// 4.下一页按钮
		nextBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if (resultController.flag) {
            		main.cardLayout.next(main.outerPanel);
                    logger.log(logger.ClickBtn, logger.nextPage);
            	} else {
            		showWarning("Warning", "请先完成公式的设置与计算！");
            	}
            }
        });
		
	} // end initButtons
	
	/**
	 * 单独负责组装第一页中的右半部分
	 */
	private void initRightPart() { 
		// 1. 添加header
		JLabel headerLabel = new JLabel();
		headerLabel.setText("已知参数:");
		headerLabel.setFont(new Font("微软雅黑", Font.BOLD, 18));
		headerLabel.setBorder(BorderFactory.createEmptyBorder(28, 0, 30, 0));
		headerLabel.setBounds(left + 370, top + 5, 100, 20);
		pageFormula.add(headerLabel);
		
		// 2. 添加提示文字
//		JLabel tip = new JLabel("<html><body>(鼠标移动到参数字母上会提示对应的公式和数值)</body></html>");
//		tip.setFont(new Font("微软雅黑", Font.PLAIN, 13));
//		tip.setBounds(left + 395, top + 45, 150, 50);
//		pageFormula.add(tip);
		
		// 3. 添加list
		addList();
	} 
	
	/**
	 * 组装右半部分中的列表
	 */
	private void addList() {
		int length = parameter.names.length;
		int x = 370 + left, y = 40 + top, width = 100, height = 20;
		
		for (int i = 0; i < length; i++) {
			JLabel label = new JLabel(parameter.names[i] + ": ");
			label.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			if (i < 5) label.setFont(new Font("微软雅黑", Font.PLAIN, 14));
			label.setBounds(x, y, width, height);
			
			JLabel symbol = new JLabel(parameter.symbols[i]);
			symbol.setFont(new Font("微软雅黑", Font.BOLD, 18));
			symbol.setToolTipText("<html><body style='font-size: 13px'>" + parameter.details[i] + "</body></html>");
			symbol.setBounds(x + 80, y, 22, height);
			
			// 增加底层的不可拖动的JLabel
			JLabel fixedSymbol = new JLabel(parameter.symbols[i]);
			fixedSymbol.setFont(new Font("微软雅黑", Font.BOLD, 18));
			fixedSymbol.setToolTipText("<html><body style='font-size: 13px'>" + parameter.details[i] + "</body></html>");
			fixedSymbol.setBounds(x + 80, y, 22, height);
			
			// 为 Symbol 添加拖动的事件处理
			DragPicListener listener = new DragPicListener(symbol, this);
			listener.main = main;
			symbol.addMouseListener(listener);
			symbol.addMouseMotionListener(listener);
			
			// 增加detail说明
			JLabel detail = new JLabel(parameter.details[i]);
			detail.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			detail.setBounds(x + 105, y, 120, height);
			
			if (i == 5) {
				detail.setFont(new Font("微软雅黑", Font.PLAIN, 13));
				detail.setBounds(x + 105, y - 2, 170, height + 40);
				y += 35;
			}
			if (i == 6) {
				detail.setFont(new Font("微软雅黑", Font.PLAIN, 13));
				detail.setBounds(x + 105, y - 8, 240, height + 30);
				y += 15;
			}
			if (i == 7) {
				detail.setFont(new Font("微软雅黑", Font.PLAIN, 13));
				detail.setBounds(x + 105, y - 5, 240, height + 30);
				y += 15;
			}
			if (i == 8) {
				detail.setFont(new Font("微软雅黑", Font.PLAIN, 13));
				detail.setBounds(x + 105, y - 2, 210, height + 70);
				y += 10;
			}
	
			pageFormula.add(label);
			pageFormula.add(symbol);
			pageFormula.add(fixedSymbol);
			pageFormula.add(detail);
			
			y += 28;
		}
		
	} // end addList
	
	/**
	 * 显示设置幂指数的对话框
	 */
	private void showSetPowerDialog() {
		CustomJDialog cDialog = new CustomJDialog(main.jf, "设定幂指数");
		JDialog dialog = cDialog.dialog;
        
        // 1. 添加 table
        Object[] columnNames = {"参数", "幂指数"};
        Object[][] rowData = null;
        
        DefaultTableModel model = new DefaultTableModel(rowData, columnNames) {
			private static final long serialVersionUID = 1L;

			@Override  
            public boolean isCellEditable(int row, int column){  
				// 使参数的列不能修改
                if (column == 0) return false;
                else return true;
            }  
        };
        
        // 根据选中的参数 添加行
        for (int i = 0; i < parameter.flags.length; i++) {
        	if ( !parameter.flags[i] ) continue;
        	
    		String value = (parameter.powers[i] == 0) ?
    			"" : ((Integer)parameter.powers[i]).toString();
    		model.addRow(new Object[] {parameter.symbols[i], value});
        }
        
        JTable table = new JTable(model);
        table.getColumnModel().getColumn(0).setPreferredWidth(150);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        cDialog.setTable(table);
       
        cDialog.initBtns();
        // 点击确认按钮的事件处理
        cDialog.okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	int flag = 1; // 表示是否可以正常关闭
            	int rowNum = table.getRowCount();
            	// 更新幂指数
            	for (int i = 0; i < rowNum; i++) {
            		String symbol = (String)table.getModel().getValueAt(i, 0);
            		String value = table.getModel().getValueAt(i, 1).toString();
            		if (value.equals("")) {
            			// 漏填
            			value = "0"; 
            			flag = 0;
            		} else if ( !isInteger(value) ) {
            			// 非法格式
            			value = "0";
            			flag = -1;
            		}
            		int power = Integer.valueOf(value);
            		parameter.setParameterPower(symbol, power, false);
            	}
            	
            	if (flag == 1) {
            		dialog.dispose();
            		logger.log(logger.ClickBtn, logger.confrimPow);
            		resultController.setFormula();
            	} else {
            		String message = "";
            		if (flag == 0) message = "有幂指数漏填";
            		else if (flag == -1) message = "幂指数输入存在格式错误！";
            		showWarning("Warning", message);
            	}
            	
            } // end actionPerformed
        }); // end cDialog.okBtn.addActionListener
        // 点击取消按钮的事件处理
        cDialog.cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	logger.log(logger.ClickBtn, logger.cancelPow);
            }
        });
      
        cDialog.show(pageFormula);
        
	} // end showSetPowerDialog
	
	/**
	 * 显示设置参数值的对话框
	 */
	private void showSetValueDialog() {
		CustomJDialog cDialog = new CustomJDialog(main.jf, "设定参数值");
		JDialog dialog = cDialog.dialog;
	
        // 1. 添加 table
        Object[] columnNames = {"参数", "取值", "单位"};
        Object[][] rowData = null;
        
        DefaultTableModel model = new DefaultTableModel(rowData, columnNames) {
			private static final long serialVersionUID = 2L;

			@Override  
            public boolean isCellEditable(int row, int column){  
				// 使参数与单位的列不能修改
                if (column == 0 || column == 2) return false;
                else return true;
            }  
        };
        
        // 根据选中的参数 添加行
        for (int i = 0; i < parameter.flags.length; i++) {
        	if ( !parameter.flags[i] ) continue;
        	
    		String value = (parameter.values[i] == 0) ?
    			"" : ((Double)parameter.values[i]).toString();
    		model.addRow(new Object[] {
				parameter.symbols[i], value, parameter.units[i]});
        }
        
        JTable table = new JTable(model);
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        cDialog.setTable(table);
       
        // 点击确认按钮的事件处理
        cDialog.initBtns();
        cDialog.okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	int flag = 1; // 表示是否可以正常关闭
            	int rowNum = table.getRowCount();
            	// 更新参数值
            	for (int i = 0; i < rowNum; i++) {
            		String symbol = (String)table.getModel().getValueAt(i, 0);
            		String value = table.getModel().getValueAt(i, 1).toString();
            		if (value.equals("")) {
            			// 漏填
            			value = "0"; 
            			flag = 0;
            		}
            		double v = Double.valueOf(value);
            		parameter.setParameterValue(symbol, v, false);
            	}
            	
            	if (flag == 1) {
            		dialog.dispose();
            		resultController.setValue();
            		logger.log(logger.ClickBtn, logger.confirmVal);
            		logger.log(logger.ReRef, resultController.getValue() + "");
            	} else {
            		String message = "";
            		if (flag == 0) message = "有参数值漏填";
            		showWarning("Warning", message);
            	}
            } // end actionPerformed
        }); // end cDialog.okBtn.addActionListener
        // 点击取消按钮的事件处理
        cDialog.cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	logger.log(logger.ClickBtn, logger.cancelVal);
            }
        });
        
        cDialog.show(pageFormula);
        
	} // end showSetPowerDialog
	
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
	
	/**
	 * 判断字符串是不是代表整数
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str) {  
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");  
        return pattern.matcher(str).matches();  
	}
	
} // end Class pageFormula