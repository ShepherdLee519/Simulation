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
	
	final static String projectName = "流动相似虚拟仿真";
	
	public static void main(String[] args) {
		Main simulator = new Main();
		simulator.initCardLayout();
	}
	
	/**
	 * 初始化 JFrame, 并设置整体布局为卡片布局
	 * 内部卡片即第一页与第二页 - 未来可能增加login页
	 */
	private void initCardLayout() {
		jf = new JFrame(projectName);
		jf.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
//		jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		jf.setResizable(false);
        jf.setSize(720, 540);
        
        // 窗口关闭事件处理
        jf.addWindowListener(new WindowAdapter() {  
            public void windowClosing(WindowEvent e) {  
            	 int flag = JOptionPane.showConfirmDialog(jf, "确认关闭窗口？",  
                     "警告!", JOptionPane.YES_NO_OPTION,  
                     JOptionPane.INFORMATION_MESSAGE);  
                 if (JOptionPane.YES_OPTION == flag) { 
            	     logger.finish(0);
                     System.exit(0);
                 } else {  
                     return;  
                 }  
            }  
        });  
        
        // 创建卡片布局，卡片间水平和竖直间隔为 10
        cardLayout = new CardLayout(10, 10);
        outerPanel = new JPanel(cardLayout);
        
        // 初始化并添加内部卡片(页)
        pageLogin = new PageLogin(this);
        pageDescription = new PageDescription(this);
        pageFormula = new PageFormula(this);
        pageCalculate = new PageCalculate(this);
        pageQuestion = new PageQuestion(this);
        
        // 获取各个页对应的面板内容
        panelLogin = pageLogin.getPanel();
        panelDescription = pageDescription.getPanel();
        panelFormula = pageFormula.getPanel();
        panelCalculate = pageCalculate.getPanel();
        panelQuestion = pageQuestion.getPanel();
        
        // 添加各个页的面板并注明面板名称
        outerPanel.add(panelLogin, "pageLogin");
        outerPanel.add(panelDescription, "pageDescription");
        outerPanel.add(panelFormula, "pageFormula");
        outerPanel.add(panelCalculate, "pageCalculate");
        outerPanel.add(panelQuestion, "pageQuestion");
        
        // 先显示哪一页
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
