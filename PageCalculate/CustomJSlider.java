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
	 * ���캯�� - ��ʼ������ؼ��� ������Ĭ��ֵ
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
	 * �ı�ֵ����ʾ - �����ֶ�����ʱ�����
	 * @param newValue
	 */
	private void changeValue(int newValue) {
		this.value = slider.getValue();
		result.setText(String.format("%.2f", value * step));
	}
	
	/**
	 * �����ֶ�����ֵ - ���滬��仯
	 */
	private void initResultChangeHandler() {
		// result ��ӽ���ʧȥ���¼�����
		result.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent arg0) {
				String str = result.getText();
				try {
					// ��ʽת��Ϊ������ - ���ܴ���error
					double newDoubleValue = Double.valueOf(str);
					// תΪָ�������µ�ֵ
					int newIntValue = (int)Math.round(newDoubleValue / step);
					
					// �ж��Ƿ�Խ��
					if (newIntValue < min) newIntValue = min;
					else if (newIntValue > max) newIntValue = max;
					
					// ��ֵ�ı����뻬���޸�
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
		
		// �س�֮��ʧȥ����
		result.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					page.requestFocus();
				}
			}
		});
		// ����հ״�ʧȥ����
		page.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					page.requestFocus();
				}
			}
		});
		
	} // end initResultChangeHanlder
	
	/**
	 * ��ʼ����������ذ�ť(+/-)
	 */
	private void initBtns() {
		minus = new JLabel("��");
		plus = new JLabel("��");
		minus.setFont(new Font("΢���ź�", Font.BOLD, 23));
		plus.setFont(new Font("΢���ź�", Font.BOLD, 20));
		
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
	 * ����slider��Ӧ�ı���
	 * @param title
	 */
	public void setLabel(String title) {
		JLabel label = new JLabel(title);
		label.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		this.label = label;
	}
	
	/**
	 * ���ø�sliderԪ���Լ���Ӧ�����λ��
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
	 * ��ȡ��ǰslider��Ӧ��ֵ(��ʾֵ)
	 * @return
	 */
	public double getValue() {
		// ����ʹ��value������change�¼��з��ص��Ǿ�ֵ
		return Double.valueOf((String.format("%.2f", slider.getValue() * step)));
	}
	
	/**
	 * ��̬�ĸı���Сֵ�ķ�Χ
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
