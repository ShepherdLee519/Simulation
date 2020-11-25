import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

public class CustomJDialog {
	public JDialog dialog;
	public JPanel panel;
	public JPanel tablePanel;
	public JButton okBtn;
	public JButton cancelBtn;
	
	/**
	 * ���캯��
	 * @param jf ��������JDialog����
	 * @param title
	 */
	CustomJDialog(JFrame jf, String title) {
		// 0. ����һ����ģ̬�Ի��� �Լ�������panel
        final JDialog dialog = new JDialog(jf, title, false);
        this.dialog = dialog;
        JPanel panel = new JPanel();
        this.panel = panel;
        JPanel tablePanel = new JPanel();
        this.tablePanel = tablePanel;
        tablePanel.setLayout(new BorderLayout());
        panel.add(tablePanel);

        // 1. �Ի���ϸ������
        dialog.setSize(350, 280);
        dialog.setResizable(true);
        dialog.setContentPane(panel);
	}
	
	/**
	 * ���������ڵı��Ĺ�����ʽ
	 * @param table
	 */
	public void setTable(JTable table) {
		 // table ��ص���ʽ����
        table.setFont(new Font(null, Font.PLAIN, 15));
        table.getTableHeader().setFont(new Font(null, Font.BOLD, 15));

        table.setRowHeight(30);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setReorderingAllowed(false); 
        table.putClientProperty("terminateEditOnFocusLost", true);
		
		tablePanel.add(table.getTableHeader(), BorderLayout.NORTH);
        tablePanel.add(table, BorderLayout.CENTER);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 100, 10, 100));
	}
	
	/**
	 * ��ʼ�������ڵĹ�����ť��������¼�
	 */
	public void initBtns() {
		// 1. ������ť����ȷ����رնԻ���
        JButton okBtn = new JButton("ȷ��");
        JButton cancelBtn = new JButton("ȡ��");
        this.okBtn = okBtn;
        this.cancelBtn = cancelBtn;
        panel.add(okBtn);
        panel.add(cancelBtn);
        
        // ���ȷ�ϰ�ť�İ�ť�¼�����
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                dialog.dispose();
            }
        });
        
        // ���ȡ����ť�İ�ť�¼�����
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        
	} // end initBtns
	
	/**
	 * ����������������֮�������� - ��ʾJDialog
	 * @param page
	 */
	public void show(JPanel page) {
		dialog.setLocationRelativeTo(page);
        dialog.setVisible(true);
	}
	
} // end Class CustomJDialog
