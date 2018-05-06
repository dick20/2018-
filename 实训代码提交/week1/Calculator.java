package calculator;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.*;
import java.awt.GridLayout;
import java.awt.event.*;

public class Calculator extends JFrame{
    private JLabel op1 = new JLabel("",JTextField.CENTER);
    private JTextField num1 = new JTextField("12");
    private JTextField num2 = new JTextField("2");
    private JLabel op3 = new JLabel("",JTextField.CENTER);

	public Calculator(){
		JFrame frame = new JFrame("Mycalculator");
		frame.setLayout(new GridLayout(2,5,5,5));

        num1.setHorizontalAlignment(JTextField.CENTER);
        num2.setHorizontalAlignment(JTextField.CENTER);
        JLabel op2 = new JLabel("=",JTextField.CENTER);
        
		frame.add(num1);
		frame.add(op1);
		frame.add(num2);
		frame.add(op2);
		frame.add(op3);
		
		JButton bu1 = new JButton("+");
		JButton bu2 = new JButton("-");
		JButton bu3 = new JButton("*");
		JButton bu4 = new JButton("/");
		JButton bu5 = new JButton("OK");
		
		frame.add(bu1);
		frame.add(bu2);
		frame.add(bu3);
		frame.add(bu4);
		frame.add(bu5);
		
		//calculate
		bu1.addActionListener(new OperationButtonListener());
		bu2.addActionListener(new OperationButtonListener());
		bu3.addActionListener(new OperationButtonListener());
		bu4.addActionListener(new OperationButtonListener());
		bu5.addActionListener(new EqualButtonListener());
		
		
		frame.setSize(400, 200);
		frame.setLocationRelativeTo(null); 			//居中操作
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	private class OperationButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			JButton temp = (JButton)e.getSource();
			op1.setText(temp.getLabel());
		}
	}
	
	private class EqualButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try{
				double number1 = Double.parseDouble(num1.getText());
				double number2 = Double.parseDouble(num2.getText());
				
				if(op1.getText().equals("+")){
					op3.setText(number1+number2+"");
				}
				else if(op1.getText().equals("-")){
					op3.setText(number1-number2+"");
				}
				else if(op1.getText().equals("*")){
					op3.setText(number1*number2+"");
				}
				else if(op1.getText().equals("/")){
					op3.setText(number1/number2+"");
				}
			}
			catch(Exception err){
				op3.setText("Wrong!");
			}
		}
	}

	public static void main(String[] args){
		Calculator c = new Calculator();
	}
}
