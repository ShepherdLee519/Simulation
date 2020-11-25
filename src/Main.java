import java.awt.CardLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Main {
	public JFrame jf;
	public Logger logger = new Logger();
	public JPanel outerPanel;
	public CardLayout cardLayout;
	
	public PageLogin pageLogin;
	public PageDescription pageDescription;
	public PageFormula pageFormula;
	public PageCalculate pageCalculate;
	public PageQuestion pageQuestion;
	
	private JPanel panelLogin;
	private JPanel panelDescription;
	private JPanel panelFormula;
	private JPanel panelCalculate;
	private JPanel panelQuestion;
	
	final static String projectName = "���������������";
	
	public static void main(String[] args) {
		Main simulator = new Main();
		simulator.initCardLayout();
	}
	
	/**
	 * ��ʼ�� JFrame, ���������岼��Ϊ��Ƭ����
	 * �ڲ���Ƭ����һҳ��ڶ�ҳ - δ����������loginҳ
	 */
	private void initCardLayout() {
		jf = new JFrame(projectName);
		jf.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
//		jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		jf.setResizable(false);
        jf.setSize(720, 540);
        
        // ���ڹر��¼�����
        jf.addWindowListener(new WindowAdapter() {  
            public void windowClosing(WindowEvent e) {  
            	 int flag = JOptionPane.showConfirmDialog(jf, "ȷ�Ϲرմ��ڣ�",  
                     "����!", JOptionPane.YES_NO_OPTION,  
                     JOptionPane.INFORMATION_MESSAGE);  
                 if (JOptionPane.YES_OPTION == flag) { 
            	     logger.finish(0);
                     System.exit(0);
                 } else {  
                     return;  
                 }  
            }  
        });  
        
        // ������Ƭ���֣���Ƭ��ˮƽ����ֱ���Ϊ 10
        cardLayout = new CardLayout(10, 10);
        outerPanel = new JPanel(cardLayout);
        
        // ��ʼ��������ڲ���Ƭ(ҳ)
        pageLogin = new PageLogin(this);
        pageDescription = new PageDescription(this);
        pageFormula = new PageFormula(this);
        pageCalculate = new PageCalculate(this);
        pageQuestion = new PageQuestion(this);
        
        // ��ȡ����ҳ��Ӧ���������
        panelLogin = pageLogin.getPanel();
        panelDescription = pageDescription.getPanel();
        panelFormula = pageFormula.getPanel();
        panelCalculate = pageCalculate.getPanel();
        panelQuestion = pageQuestion.getPanel();
        
        // ��Ӹ���ҳ����岢ע���������
        outerPanel.add(panelLogin, "pageLogin");
        outerPanel.add(panelDescription, "pageDescription");
        outerPanel.add(panelFormula, "pageFormula");
        outerPanel.add(panelCalculate, "pageCalculate");
        outerPanel.add(panelQuestion, "pageQuestion");
        
        // ����ʾ��һҳ
        cardLayout.show(outerPanel, "pageLogin");
//        cardLayout.show(outerPanel, "pageDescription");
//        cardLayout.show(outerPanel, "pageFormula");
//        cardLayout.show(outerPanel, "pageCalculate");
//        cardLayout.show(outerPanel, "pageQuestion");
        
        jf.setContentPane(outerPanel);
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
        
	} // end initCardLayout
	
} // end Class Main
