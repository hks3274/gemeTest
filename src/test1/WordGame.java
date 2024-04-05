package test1;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class WordGame extends JFrame {
	int count = 5;
	int checkNum = 0;
	int selectNum = 0;
	boolean check = false, ImageCheck = true;
	private JPanel pn1, pn2;
	JLabel lblCount;
	private JTextField txtResult;
	JLabel lblImg,lblcount;

	public class DownWord extends Thread {
		String word;

		public DownWord(String word) {
			this.word = word;
		}

		@Override
		public void run() {
			int y = 0; // 초기 y 좌표
			JLabel label = new JLabel(word);
			int randNum = (int) (Math.random() * 500) + 1;
			label.setBounds(randNum, y, 100, 20); // 단어의 초기 위치 설정
			JPanel panelReference = pn1; // pn1이 null이 되는 것을 방지하기 위해 임시로 저장
			
			if (panelReference == null) {
			    return; // pn1이 null이면 스레드 실행 중지
			} else {
			    pn1.add(label);
			    pn1.repaint(); // 추가된 라벨을 즉시 화면에 그리도록 갱신
			}
			
			
			// 단어가 아래로 이동하는 동안 반복
			while (y < 518) {
				y += 1; // y 좌표를 아래로 이동
				label.setBounds(randNum, y, 100, 20); // 단어의 위치 업데이트

				if (check && txtResult.getText().equals(word)) {
					selectNum++;
					txtResult.setText("");
					label.setEnabled(false);
					label.setVisible(false);
					lblcount.setText("count: "+selectNum);
					if (pn1 != null) {
						pn1.remove(label);
						pn1.repaint(); // 추가된 라벨을 즉시 화면에 그리도록 갱신
					}
					check = false;

					break;
				} else if (check && !txtResult.getText().equals(word)) {
					txtResult.setText("");
					check = false;

				} else if (count <= 0 && pn1 != null) {
					pn1.remove(label);
					label.setEnabled(false);
					label.setVisible(false);
				} else if (y == 518) {
					count--;
					if (pn1 != null) {
						pn1.remove(label);
					}
					lblCount.setText("남은 목숨 : " + count);
				}
				
				if(selectNum >= 15 && checkNum == 20) {
					JOptionPane.showMessageDialog(null, "축하드립니다.");
					break;
				}
				
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
					}
				
			}

		}
	}

	public WordGame() {
		super("타자연습");

		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(null);

		pn1 = new JPanel();
		pn1.setBounds(0, 0, 570, 518);
		getContentPane().add(pn1);
		pn1.setLayout(null);

		pn2 = new JPanel();
		pn2.setBorder(new LineBorder(new Color(0, 0, 0)));
		pn2.setBackground(new Color(255, 255, 255));
		pn2.setBounds(570, 0, 214, 561);
		getContentPane().add(pn2);
		pn2.setLayout(null);

		JLabel lblImg = new JLabel("");
		lblImg.setIcon(new ImageIcon(WordGame.class.getResource("/test1/images/sad.png")));
		lblImg.setBounds(0, 0, 215, 198);
		pn2.add(lblImg);

		lblCount = new JLabel("남은 목숨 : " + count);
		lblCount.setBounds(23, 293, 112, 24);
		pn2.add(lblCount);

		lblcount = new JLabel("count:" + selectNum);
		lblcount.setBounds(23, 350, 149, 24);
		pn2.add(lblcount);
		
		JLabel lblLevel = new JLabel("레벨: ");
		lblLevel.setBounds(23, 396, 149, 24);
		pn2.add(lblLevel);

		JPanel pn3 = new JPanel();
		pn3.setBounds(0, 517, 570, 44);
		getContentPane().add(pn3);
		pn3.setLayout(null);

		txtResult = new JTextField();
		txtResult.setBounds(0, 0, 570, 44);
		pn3.add(txtResult);
		txtResult.setColumns(10);

		txtResult.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				check = true;
			}
		});

		setVisible(true);
	}

	public static void main(String[] args) {
		WordData wordData = new WordData();
		WordGame wordGame = new WordGame();
		wordData.create();
		wordData.shuffle();

		while (wordGame.count != 0 && wordGame.checkNum < 20) {
			String word = wordData.arr.get(wordGame.checkNum);
			wordGame.checkNum++;

			if (wordGame.pn1 != null) {
				WordGame.DownWord dowWord = wordGame.new DownWord(word);
				dowWord.start();
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}
		}

	}

}
