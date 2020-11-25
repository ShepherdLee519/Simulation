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
	final private String headerTitle = "����綴���"; 
	final private String imageUrl = "image/page2_image.png";
	
	/**
	 * ���캯������main����
	 * @param main
	 */
	PageCalculate(Main main) {
		this.main = main;
		this.logger = main.logger;
	}
	
	/**
	 * ��װ��������������ҳ��JPanel
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
	 * ������װ�������ı���ͼƬ���ֵ�����
	 */
	private void initBasePart() {
		// 1. ���header
		JLabel headerLabel = new JLabel();
		headerLabel.setText(headerTitle);
		headerLabel.setFont(new Font("΢���ź�", Font.BOLD, 20));
		headerLabel.setBounds(left, top, 300, 25);
		pageCalculate.add(headerLabel);
		
		// 2.����˵��
		String stepInfo =  "<html><body width=360 style=\"font-size:14\"><p>"
			+ "���϶�������ѡ�����ֵ����ֱ�����룩��ȷ�����������С��鿴�����"
			+ "���������������ɱ������Ϊ���ָ�ꡣ��������ָ��Խ�ӽ�1��˵��������Խ�ã�Ҫ��С��0.9��"
			+ "ÿ�����к�Ľ�����Զ����档�������һ�������ء�ȷ�����Ʋ��������������һ������������</p></body></html>";
		JButton detailBtn = new JButton("����˵��");
		detailBtn.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		detailBtn.setBounds(left + 230, top, 90, 25);
		detailBtn.setFocusable(false);
		pageCalculate.add(detailBtn);
		// ����˵����ť
		detailBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	JOptionPane.showMessageDialog(
                    main.jf,
                    stepInfo,
                    "����˵��",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
		
		// 3. ���ͼƬ
		JLabel picture = new JLabel();
//		ImageIcon icon = new ImageIcon(imageUrl);
		ImageIcon icon = new ImageIcon(getClass().getResource(imageUrl));
		icon.setImage(icon.getImage()
			.getScaledInstance(318, 198, Image.SCALE_SMOOTH)); // 567 �� 354
		picture.setIcon(icon);
		picture.setBounds(left, top + 30, 318, 198);
		pageCalculate.add(picture);
	}
	
	/**
	 * ������װ��������Ĳ���
	 */
	private void initSelectPart() {
		int leftBound = left + 360,
			y = top + 30; // ����������Ͻ�
		
		// 1. ���header
		JLabel headerLabel = new JLabel();
		headerLabel.setText("����ѡ��:");
		headerLabel.setFont(new Font("΢���ź�", Font.BOLD, 18));
		headerLabel.setBorder(BorderFactory.createEmptyBorder(28, 0, 30, 0));
		headerLabel.setBounds(leftBound, top, 100, 20);
		pageCalculate.add(headerLabel);
		
		// 2. ���Slider
		int sliderNum = 4;
		double[][] sliderParameter = {
			// low upper step move default
			{0.2, 1, 0.01, 0.1, 0.6},
			{3.6, 8, 0.01, 0.1, 5.8},
			{0, 100, 0.01, 1, 30},
			{-30, 30, 0.01, 1, 0}
		};
		String[] sliderLabel = {
			"ģ������(0.2~1)", "�綴������(x~8m, x=1.2ģ�Ϳ��)",
			"�����ٶ�(0~100m/s)", "�����¶�(-30~30�棬�����¶�20��)"
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
		
		// ����slider0��slider1֮��Ĺ���
		sliders[0].slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
            	final int W = 5;
            	sliders[1].changeMin(sliders[0].getValue(), 1.2 * W);
            }
        });
		
	} // end initSelectPart
	
	/**
	 * ������װ�����ʾ�Լ���ť���õĲ���
	 */
	private void initTablePart() {
		// 1. �����
		JLabel headerLabel = new JLabel("������");
		headerLabel.setFont(new Font("΢���ź�", Font.BOLD, 17));
		headerLabel.setBounds(left, top + 235, 300, 25);
		pageCalculate.add(headerLabel);    
		
		CustomJTable cTable = new CustomJTable(pageCalculate);
		calculator = new FormulaCalculator(sliders, cTable, main); 
        pageCalculate.add(cTable.tablePanel);
		
		// 2. ��ť��
		JButton runBtn = new JButton("����");
		JButton prevBtn = new JButton("��һ��");
		JButton nextBtn = new JButton("��һ��");
		runBtn.setBounds(left + 155, top + 458, 100, 26);
		prevBtn.setBounds(left + 260, top + 458, 100, 26);
		nextBtn.setBounds(left + 365, top + 458, 100, 26);
		runBtn.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		prevBtn.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		nextBtn.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		runBtn.setFocusable(false);
		prevBtn.setFocusable(false);
		nextBtn.setFocusable(false);
		pageCalculate.add(runBtn);
		pageCalculate.add(prevBtn);
		pageCalculate.add(nextBtn);
		
		// ��ť�¼�����
		//// 1. ���а�ť
		runBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("runBtn is Clicked!");
                calculator.calculate();
                cTable.addCol();
            }
        });
		//// 2. ��һҳ��ť
		prevBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.cardLayout.previous(main.outerPanel);
                logger.log(logger.ClickBtn, logger.previousPage);
            }
        });
		//// 3. ��һҳ��ť
		nextBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.cardLayout.next(main.outerPanel);
                logger.log(logger.ClickBtn, logger.nextPage);
            }
        });
		
	} // end initTablePart

} // end Class pageCalculate
