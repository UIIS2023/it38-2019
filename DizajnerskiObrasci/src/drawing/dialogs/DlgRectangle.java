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

public class DlgRectangle extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private boolean isOk;
	private boolean isCancel;
	private Color outer;
	private Color inner;
	private JTextField txtTopLeftX;
	private JTextField txtTopLeftY;
	private JTextField txtHeight;
	private JTextField txtWidth;

	public JTextField getTxtTopLeftX() {
		return txtTopLeftX;
	}

	public void setTxtTopLeftX(JTextField txtTopLeftX) {
		this.txtTopLeftX = txtTopLeftX;
	}

	public JTextField getTxtTopLeftY() {
		return txtTopLeftY;
	}

	public void setTxtTopLeftY(JTextField txtTopLeftY) {
		this.txtTopLeftY = txtTopLeftY;
	}

	public JTextField getTxtHeight() {
		return txtHeight;
	}

	public void setTxtHeight(JTextField txtHeight) {
		this.txtHeight = txtHeight;
	}

	public JTextField getTxtWidth() {
		return txtWidth;
	}

	public void setTxtWidth(JTextField txtWidth) {
		this.txtWidth = txtWidth;
	}

	JFrame frame = new JFrame("Choose a color");

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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgRectangle dialog = new DlgRectangle(0, 0);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgRectangle(Integer x, Integer y) {
		setModal(true);
		setBounds(100, 100, 313, 390);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		JLabel lblTopLeftX = new JLabel("Top left X:");

		JLabel lblTopLeftY = new JLabel("Top left Y:");

		JLabel lblHeight = new JLabel("Height:");

		JLabel lblWidth = new JLabel("Width:");
		this.outer = Color.BLACK;
		this.inner = Color.BLACK;
		txtTopLeftX = new JTextField(x.toString());
		txtTopLeftX.setColumns(10);

		txtTopLeftY = new JTextField(y.toString());
		txtTopLeftY.setColumns(10);

		txtHeight = new JTextField();
		txtHeight.setColumns(10);

		txtWidth = new JTextField();
		txtWidth.setColumns(10);

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
				.addGroup(gl_contentPanel.createSequentialGroup().addContainerGap()
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPanel.createSequentialGroup().addComponent(lblTopLeftX).addGap(18)
										.addComponent(txtTopLeftX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPanel.createSequentialGroup()
										.addGroup(gl_contentPanel
												.createParallelGroup(Alignment.LEADING).addComponent(lblTopLeftY)
												.addComponent(lblHeight).addComponent(lblWidth))
										.addGap(18)
										.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
												.addComponent(txtWidth, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(txtHeight, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(txtTopLeftY, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
								.addGroup(Alignment.TRAILING,
										gl_contentPanel.createSequentialGroup().addComponent(btnOuter)
												.addPreferredGap(ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
												.addComponent(btnInner)))
						.addContainerGap()));
		gl_contentPanel.setVerticalGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPanel
				.createSequentialGroup().addGap(25)
				.addGroup(gl_contentPanel
						.createParallelGroup(Alignment.BASELINE).addComponent(lblTopLeftX).addComponent(txtTopLeftX,
								GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addGroup(gl_contentPanel
						.createParallelGroup(Alignment.BASELINE).addComponent(lblTopLeftY).addComponent(txtTopLeftY,
								GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE).addComponent(lblHeight).addComponent(
						txtHeight, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE).addComponent(lblWidth).addComponent(
						txtWidth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(39).addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE).addComponent(btnInner)
						.addComponent(btnOuter))
				.addContainerGap(67, Short.MAX_VALUE)));
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (txtHeight.getText().isEmpty() || txtWidth.getText().isEmpty()
								|| txtTopLeftX.getText().isEmpty() || txtTopLeftY.getText().isEmpty()) {
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
