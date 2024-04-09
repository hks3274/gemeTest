package test1;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class WordGame extends JFrame {
	static WordData wordData = new WordData();
	static ArrayList<String> arrWord = new ArrayList<String>();
	JButton btnStart;
	static JPanel pn, pn2;
	static int cnt = 5;
	static int checkCnt = 0;
	static boolean check = false, ok = false; // 시작체크
	static int level = 1;
	static JLabel lblCehckCnt;
	static JLabel lblLevel;
	static JLabel lblCount;
	static private JTextField txtWord;

	// 게임시작 클래스
	static class WordGameStart extends Thread {
		String word = new String();
		boolean running = true; // 스레드 실행 여부를 나타내는 플래그

		public WordGameStart(String word) {
			this.word = word;
		}

		@Override
		public void run() {
			int randNum = (int) (Math.random() * 500) + 50;
			JLabel newLabel = new JLabel(word);
			int position = -20;
			newLabel.setBounds(randNum, position, 100, 50);
			pn.add(newLabel);

			while (running) { // 스레드 실행 여부를 확인하여 루프를 실행
				position++;
				newLabel.setBounds(randNum, position, 100, 50);
				pn.repaint();

				if (position > 612) {
					cnt--;
					lblCount.setText("목숨:" + cnt);
					lblCount.repaint();
					if (cnt <= 0) { // cnt가 0 이하일 때 게임 오버 처리
						stopThreads(); // 모든 스레드를 종료하는 메소드 호출
					}
					break;
				}

				txtWord.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (txtWord.getText().equals(word)) {
							lblCehckCnt.repaint();
							newLabel.setVisible(false);
							newLabel.setEnabled(false);
							txtWord.setText("");
							checkCnt++;
							lblCehckCnt.setText("맞힌갯수:" + checkCnt);
							return;
						}
					}
				});

				try {
					sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		// 스레드를 중지하는 메소드
		public void stopThread() {
			running = false;
		}

	}

//모든 스레드를 중지하는 메소드
	static void stopThreads() {
		ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
		Thread[] threads = new Thread[threadGroup.activeCount()];
		threadGroup.enumerate(threads);
		for (Thread t : threads) {
			if (t instanceof WordGameStart) {
				((WordGameStart) t).stopThread(); // WordGameStart 스레드를 중지합니다.
			}
		}
		JOptionPane.showMessageDialog(null, "게임 오버! 목숨이 모두 소진되었습니다.");
	}

	// 스윙생성
	public WordGame() {
		super("타자게임");
		setSize(600, 800);
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

		txtWord = new JTextField();
		txtWord.setBounds(0, 0, 584, 56);
		pn1.add(txtWord);
		txtWord.setColumns(10);

		JPanel pn2 = new JPanel();
		pn2.setBackground(new Color(255, 255, 255));
		pn2.setBounds(0, 0, 584, 83);
		getContentPane().add(pn2);
		pn2.setLayout(null);

		btnStart = new JButton("게임시작");

		btnStart.setBackground(new Color(255, 255, 255));
		btnStart.setBounds(25, 24, 144, 34);
		pn2.add(btnStart);

		lblLevel = new JLabel("level :" + level);
		lblLevel.setFont(new Font("굴림", Font.PLAIN, 23));
		lblLevel.setBounds(195, 26, 77, 24);
		pn2.add(lblLevel);

		lblCount = new JLabel("목숨:" + cnt);
		lblCount.setFont(new Font("굴림", Font.PLAIN, 23));
		lblCount.setBounds(284, 26, 85, 24);
		pn2.add(lblCount);

		lblCehckCnt = new JLabel("맞힌갯수:" + checkCnt);
		lblCehckCnt.setFont(new Font("굴림", Font.PLAIN, 23));
		lblCehckCnt.setBounds(381, 26, 136, 24);
		pn2.add(lblCehckCnt);

		// 버튼을 누르면 시작
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				check = true;
				txtWord.requestFocus();
			}
		});

		setVisible(true);
	}

	// 메인 화면
	public static void main(String[] args) {

		WordGame wordGame = new WordGame();
		wordData.create();
		wordData.shuffle();

		int i = 0;
		int time = 2000;

		while (!check) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}

		while (true) {
			arrWord = wordData.arr;

			WordGameStart gameStart = new WordGameStart(arrWord.get(i));
			i++;
			gameStart.start();

			if (i % 20 == 0) {
				time = time - 500;
				level++;
			}

			lblLevel.setText("level :" + level);
			lblLevel.repaint();

			try {
				Thread.sleep(time);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			if (i == 59) {
				JOptionPane.showMessageDialog(null, "축하합니다. 성공하셨습니다.");
				break;
			}

		}

	}
}
