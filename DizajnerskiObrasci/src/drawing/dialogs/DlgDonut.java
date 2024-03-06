package drawing.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
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

public class DlgDonut extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private boolean isOk;
	private boolean isCancel;
	private Color outer;
	private Color inner;
	private JLabel lblX;
	private JTextField txtX;
	private JTextField txtY;
	private JTextField txtInnerRadius;
	private JTextField txtOuterRadius;

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

	public Color getOuter() {
		return outer;
	}

	public void setOuter(Color outer) {
		this.outer = outer;
	}

	public Color getInner() {
		return inner;
	}

	public void setInner(Color inner) {
		this.inner = inner;
	}

	public JLabel getLblX() {
		return lblX;
	}

	public void setLblX(JLabel lblX) {
		this.lblX = lblX;
	}

	public JTextField getTxtX() {
		return txtX;
	}

	public void setTxtX(JTextField txtX) {
		this.txtX = txtX;
	}

	public JTextField getTxtY() {
		return txtY;
	}

	public void setTxtY(JTextField txtY) {
		this.txtY = txtY;
	}

	public JTextField getTxtInnerRadius() {
		return txtInnerRadius;
	}

	public void setTxtInnerRadius(JTextField txtInnerRadius) {
		this.txtInnerRadius = txtInnerRadius;
	}

	public JTextField getTxtOuterRadius() {
		return txtOuterRadius;
	}

	public void setTxtOuterRadius(JTextField txtOuterRadius) {
		this.txtOuterRadius = txtOuterRadius;
	}

	JFrame frame = new JFrame("Choose a color");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgDonut dialog = new DlgDonut(0, 0);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */

	public DlgDonut(Integer x, Integer y) {
		setBounds(100, 100, 283, 345);
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			lblX = new JLabel("Center X:");
		}
		JLabel lblY = new JLabel("Center Y:");
		JLabel lblInnerRadius = new JLabel("Inner radius:");
		JLabel lblOuterRadius = new JLabel("Outer radius:");
		txtX = new JTextField(x.toString());
		txtX.setColumns(10);
		txtY = new JTextField(y.toString());
		txtY.setColumns(10);
		this.outer = Color.BLACK;
		this.inner = Color.BLACK;
		txtInnerRadius = new JTextField();
		txtInnerRadius.setColumns(10);
		txtOuterRadius = new JTextField();
		txtOuterRadius.setColumns(10);
		JButton btnOuter = new JButton("Outer color");
		btnOuter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color tmp = new Color(outer.getRed(),outer.getGreen(),outer.getBlue());
				outer = JColorChooser.showDialog(frame, "Choose Color", frame.getBackground());
				if(outer == null) {
					outer = tmp;
				}
				btnOuter.setBackground(outer);
			}
		});

		JButton btnInner = new JButton("Inner color");
		btnInner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color tmp = new Color(inner.getRed(),inner.getGreen(),inner.getBlue());
				inner = JColorChooser.showDialog(frame, "Choose Color", frame.getBackground());
				if(inner == null) {
					inner = tmp;
				}
				btnInner.setBackground(inner);
			}
		});
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup().addContainerGap().addGroup(gl_contentPanel
						.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
								.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING).addComponent(lblX)
										.addComponent(lblY, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblInnerRadius, GroupLayout.PREFERRED_SIZE, 78,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(lblOuterRadius))
								.addGap(38)
								.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
										.addComponent(txtOuterRadius, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(txtInnerRadius, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(txtY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(txtX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPanel.createSequentialGroup().addComponent(btnOuter)
								.addPreferredGap(ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
								.addComponent(btnInner)))
						.addContainerGap(11, Short.MAX_VALUE)));
		gl_contentPanel.setVerticalGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPanel
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE).addComponent(lblX).addComponent(txtX,
						GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE).addComponent(lblY).addComponent(txtY,
						GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE).addComponent(lblInnerRadius)
						.addComponent(txtInnerRadius, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE).addComponent(lblOuterRadius)
						.addComponent(txtOuterRadius, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
				.addGap(50).addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE).addComponent(btnOuter)
						.addComponent(btnInner))
				.addContainerGap(80, Short.MAX_VALUE)));
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							if (txtX.getText().isEmpty() || txtY.getText().isEmpty() || txtInnerRadius.getText().isEmpty()
									|| txtOuterRadius.getText().isEmpty()) {
								JOptionPane.showMessageDialog(null, "All fields must be filled!");
							} else if (Integer.parseInt(txtInnerRadius.getText()) >= Integer
									.parseInt(txtOuterRadius.getText())) {
								JOptionPane.showMessageDialog(null, "Outer radius must be greater than the inner radius!");
							} else {
								isOk = true;
								setVisible(false);
							}
						} catch (HeadlessException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (NumberFormatException e1) {
							JOptionPane.showConfirmDialog(null, "Only integers can be accepted in the text fields", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
							return;
							
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
