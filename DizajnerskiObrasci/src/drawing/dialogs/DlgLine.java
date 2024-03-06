package drawing.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

public class DlgLine extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Color color;
	private boolean isOk;
	private boolean isCancel;
	JFrame frame = new JFrame("Choose a color");
	private JTextField txtStartX;
	private JTextField txtStartY;
	private JTextField txtEndX;
	private JTextField txtEndY;

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean isOk() {
		return isOk;
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}

	public boolean isCancel() {
		return isCancel;
	}

	public void setCancel(boolean isCancel) {
		this.isCancel = isCancel;
	}

	public JTextField getTxtStartX() {
		return txtStartX;
	}

	public void setTxtStartX(JTextField txtStartX) {
		this.txtStartX = txtStartX;
	}

	public JTextField getTxtStartY() {
		return txtStartY;
	}

	public void setTxtStartY(JTextField txtStartY) {
		this.txtStartY = txtStartY;
	}

	public JTextField getTxtEndX() {
		return txtEndX;
	}

	public void setTxtEndX(JTextField txtEndX) {
		this.txtEndX = txtEndX;
	}

	public JTextField getTxtEndY() {
		return txtEndY;
	}

	public void setTxtEndY(JTextField txtEndY) {
		this.txtEndY = txtEndY;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgLine dialog = new DlgLine(0, 0, 0, 0);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgLine(Integer x1, Integer y1, Integer x2, Integer y2) {
		setBounds(100, 100, 289, 316);
		setModal(true);
		this.color = Color.BLACK;
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		JLabel lblStartX = new JLabel("Start point X:");
		JLabel lblStartY = new JLabel("Start point Y:");
		JLabel lblEndX = new JLabel("End point X:");
		JLabel lblEndY = new JLabel("End point Y:");
		txtStartX = new JTextField(x1.toString());
		txtStartX.setColumns(10);
		txtStartY = new JTextField(y1.toString());
		txtStartY.setColumns(10);
		txtEndX = new JTextField(x2.toString());
		txtEndX.setColumns(10);
		txtEndY = new JTextField(y2.toString());
		txtEndY.setColumns(10);
		JButton btnColor = new JButton("Color");
		btnColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color tmp = new Color(color.getRed(),color.getGreen(),color.getBlue());
				color = JColorChooser.showDialog(frame, "Choose Color", frame.getBackground());
				if(color == null) {
					color = tmp;
				}
				btnColor.setBackground(color);
			}
		});
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPanel.createSequentialGroup().addGroup(gl_contentPanel
						.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup().addGap(22).addGroup(gl_contentPanel
								.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPanel.createSequentialGroup().addComponent(lblStartX).addGap(18)
										.addComponent(txtStartX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPanel.createSequentialGroup()
										.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
												.addComponent(lblStartY).addComponent(lblEndX).addComponent(lblEndY))
										.addGap(18)
										.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
												.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
														.addComponent(txtEndY, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addComponent(txtEndX, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
												.addComponent(txtStartY, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))))
						.addGroup(gl_contentPanel.createSequentialGroup().addGap(92).addComponent(btnColor)))
						.addContainerGap(28, Short.MAX_VALUE)));
		gl_contentPanel.setVerticalGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPanel
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtStartX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(lblStartX))
				.addGap(18)
				.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE).addComponent(lblStartY).addComponent(
						txtStartY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE).addComponent(lblEndX).addComponent(
						txtEndX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE).addComponent(lblEndY).addComponent(
						txtEndY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED, 31, Short.MAX_VALUE).addComponent(btnColor)
				.addContainerGap()));
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (txtStartX.getText().isEmpty() || txtEndY.getText().isEmpty()
								|| txtEndX.getText().isEmpty() || txtStartY.getText().isEmpty()) {
							JOptionPane.showMessageDialog(null, "All fields must be filled!");
						} else {
							isOk = true;
							setVisible(false);
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						isCancel = true;
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
