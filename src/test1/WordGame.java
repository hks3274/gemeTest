package test1;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;





public class WordGame extends JFrame {
	JButton btnStart;
	JPanel pn;
	ArrayList<String> arrWord =  new ArrayList<String>();
	int cnt;
	
	class WordGameStart extends Thread {
		ArrayList<String> arrWord = new ArrayList<String>();
		
		public WordGameStart(ArrayList<String> arrWord) {
			this.arrWord = arrWord;
		}
		
		@Override
		public void run() {
			int randNum = ((int)Math.random()*550 )+ 10;
			JLabel newLabel = new JLabel(arrWord.get(cnt++));
			newLabel.setBounds(randNum,82,20,20);
			pn.add(newLabel);
			
			
			while(true) {
				int y = 82;
				newLabel.setBounds(randNum,y++,20,20);
				pn.add(newLabel);
				
				try {sleep(2000);} catch (InterruptedException e) {}
			}
			
			
		}
	}
	
	
	
	public WordGame() {
		super("타자게임");
		setSize(600,800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(null);
		
		pn = new JPanel();
		pn.setBounds(0, 82, 584, 624);
		getContentPane().add(pn);
		pn.setLayout(null);
		
		JPanel pn1 = new JPanel();
		pn1.setBackground(new Color(255, 255, 255));
		pn1.setBounds(0, 705, 584, 56);
		getContentPane().add(pn1);
		pn1.setLayout(null);
		
		JLabel lblWord = new JLabel("");
		lblWord.setBackground(new Color(255, 255, 255));
		lblWord.setBounds(0, 0, 584, 56);
		pn1.add(lblWord);
		
		JPanel pn2 = new JPanel();
		pn2.setBackground(new Color(255, 255, 255));
		pn2.setBounds(0, 0, 584, 83);
		getContentPane().add(pn2);
		pn2.setLayout(null);
		
		btnStart = new JButton("게임시작");

		btnStart.setBackground(new Color(255, 255, 255));
		btnStart.setBounds(25, 24, 144, 34);
		pn2.add(btnStart);
		
		JLabel lblLevel = new JLabel("level :");
		lblLevel.setFont(new Font("굴림", Font.PLAIN, 23));
		lblLevel.setBounds(237, 26, 68, 24);
		pn2.add(lblLevel);
		
		JLabel lblCount = new JLabel("목숨:");
		lblCount.setFont(new Font("굴림", Font.PLAIN, 23));
		lblCount.setBounds(393, 26, 68, 24);
		pn2.add(lblCount);
		
		
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WordData wordData = new WordData();
				wordData.create();
				wordData.shuffle();
				arrWord = wordData.arr;
				
				WordGameStart wordGameStart = new WordGameStart(arrWord);
				wordGameStart.start();
			}
		});
		
		
		
		setVisible(true);
	}
	
	
	public static void main(String[] args) {
		new WordGame();
	}
}
