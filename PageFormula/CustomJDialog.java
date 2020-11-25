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
	 * 构造函数
	 * @param jf 用于设置JDialog参数
	 * @param title
	 */
	CustomJDialog(JFrame jf, String title) {
		// 0. 创建一个非模态对话框 以及容器的panel
        final JDialog dialog = new JDialog(jf, title, false);
        this.dialog = dialog;
        JPanel panel = new JPanel();
        this.panel = panel;
        JPanel tablePanel = new JPanel();
        this.tablePanel = tablePanel;
        tablePanel.setLayout(new BorderLayout());
        panel.add(tablePanel);

        // 1. 对话框细节设置
        dialog.setSize(350, 280);
        dialog.setResizable(true);
        dialog.setContentPane(panel);
	}
	
	/**
	 * 设置容器内的表格的公共样式
	 * @param table
	 */
	public void setTable(JTable table) {
		 // table 相关的样式调整
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
	 * 初始化容器内的公共按钮与基本的事件
	 */
	public void initBtns() {
		// 1. 创建按钮用于确认与关闭对话框
        JButton okBtn = new JButton("确认");
        JButton cancelBtn = new JButton("取消");
        this.okBtn = okBtn;
        this.cancelBtn = cancelBtn;
        panel.add(okBtn);
        panel.add(cancelBtn);
        
        // 点击确认按钮的按钮事件处理
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                dialog.dispose();
            }
        });
        
        // 点击取消按钮的按钮事件处理
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        
	} // end initBtns
	
	/**
	 * 容器添加完表格等内容之后最后调用 - 显示JDialog
	 * @param page
	 */
	public void show(JPanel page) {
		dialog.setLocationRelativeTo(page);
        dialog.setVisible(true);
	}
	
} // end Class CustomJDialog
