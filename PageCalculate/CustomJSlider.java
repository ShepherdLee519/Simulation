import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CustomJSlider {
	public int number;
	public Logger logger;
	public JSlider slider;
	
	private JPanel page;
	private JLabel label;
	private JTextField result;
	private JLabel minus;
	private JLabel plus;
	private double step;
	private double move;
	private int value;
	private int min, max;
	
	/**
	 * 构造函数 - 初始化与相关计算 并设置默认值
	 * @param lower
	 * @param upper
	 * @param step
	 * @param defaultValue
	 */
	CustomJSlider(int number, double lower, double upper, double step, double move, double defaultValue, Logger logger) {
		this.number = number;
		this.step = step;
		this.move = move;
		this.logger = logger;
		min = (int)(Math.round(lower / step));
		max = (int)(Math.round(upper / step)); 
		value = (int)(Math.round(defaultValue / step));
		JSlider slider = new JSlider(min, max, value);
		this.slider = slider;
		
		JTextField result = new JTextField();
		result.setText(String.format("%.2f", defaultValue));
		this.result = result;
		
		initBtns();
		slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
            	int value = slider.getValue();
            	changeValue(value);
            }
        });
		slider.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				logger.log(logger.Scrollbar, "x" + (number + 1) + ":" + 
            		String.format("%.2f", value * step));
			}
		});
	}
	
	/**
	 * 改变值的显示 - 滑块手动滑动时候调用
	 * @param newValue
	 */
	private void changeValue(int newValue) {
		this.value = slider.getValue();
		result.setText(String.format("%.2f", value * step));
	}
	
	/**
	 * 允许手动输入值 - 伴随滑块变化
	 */
	private void initResultChangeHandler() {
		// result 添加焦点失去的事件处理
		result.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent arg0) {
				String str = result.getText();
				try {
					// 格式转换为浮点数 - 可能触发error
					double newDoubleValue = Double.valueOf(str);
					// 转为指定精度下的值
					int newIntValue = (int)Math.round(newDoubleValue / step);
					
					// 判断是否越界
					if (newIntValue < min) newIntValue = min;
					else if (newIntValue > max) newIntValue = max;
					
					// 新值的保存与滑块修改
					slider.setValue(newIntValue);
					result.setText(String.format("%.2f", newIntValue * step));
					value = newIntValue;
					logger.log(logger.Scrollbar, "x" + (number + 1) + ":" + 
	            		String.format("%.2f", value * step));
				} catch(NumberFormatException e) {
					result.setText(String.format("%.2f", value * step));
				}
			}
		});
		
		// 回车之后失去焦点
		result.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					page.requestFocus();
				}
			}
		});
		// 点击空白处失去焦点
		page.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					page.requestFocus();
				}
			}
		});
		
	} // end initResultChangeHanlder
	
	/**
	 * 初始化区域内相关按钮(+/-)
	 */
	private void initBtns() {
		minus = new JLabel("－");
		plus = new JLabel("＋");
		minus.setFont(new Font("微软雅黑", Font.BOLD, 23));
		plus.setFont(new Font("微软雅黑", Font.BOLD, 20));
		
		minus.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int v = slider.getValue();
				if (v == min) return;
		        slider.setValue(v - (int)(move / step));
		        changeValue(value - (int)(move / step));
		        logger.log(logger.Scrollbar, "x" + (number + 1) + ":" + 
	            		String.format("%.2f", value * step));
			}
		});
		plus.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int v = slider.getValue();
				if (v == max) return;
		        slider.setValue(v + (int)(move / step));
		        changeValue(value + (int)(move / step));
		        logger.log(logger.Scrollbar, "x" + (number + 1) + ":" + 
	            		String.format("%.2f", value * step));
			}
		});
		
	} // end initBtns
	
	/**
	 * 设置slider对应的标题
	 * @param title
	 */
	public void setLabel(String title) {
		JLabel label = new JLabel(title);
		label.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		this.label = label;
	}
	
	/**
	 * 设置该slider元件以及相应配件的位置
	 * @param page
	 * @param left
	 * @param top
	 */
	public void setPosition(JPanel page, int left, int top) {
		label.setBounds(left, top, 300, 20);
		minus.setBounds(left, top + 22, 25, 20);
		slider.setBounds(left + 20, top + 25, 160, 20);
		plus.setBounds(left + 180, top + 22, 20, 20);
		result.setBounds(left + 210, top + 25, 50, 20);
		this.page = page;
		initResultChangeHandler();
		
		page.add(label);
		page.add(minus);
		page.add(slider);
		page.add(plus);
		page.add(result);
	}
	
	/**
	 * 获取当前slider对应的值(显示值)
	 * @return
	 */
	public double getValue() {
		// 不能使用value避免在change事件中返回的是旧值
		return Double.valueOf((String.format("%.2f", slider.getValue() * step)));
	}
	
	/**
	 * 动态的改变最小值的范围
	 * @param v
	 * @param times
	 */
	public void changeMin(double v, double times) {
		int newMin = (int)(Math.round((v * times) / step));
		if (value <= newMin) value = newMin;
		min = newMin;
		slider.setMinimum(newMin);
		slider.setValue(value);
	}
	
} // end Class CustomJSlider
