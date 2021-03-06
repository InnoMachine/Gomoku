package gomoku.main.guiboard;

import gomoku.constants.Constants;
import gomoku.gamepanel.DialogCreator;
import gomoku.language.Language;
import gomoku.player.HumanPlayer;
import gomoku.player.Player;
import gomoku.sound.Media;
import gomoku.sound.Sound;
import gomoku.theme.Theme;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 * 
 * @author luck
 *@version 2013.3.30
 * 三人五子棋
 */
public class Threeguiboard extends DoubleGuiBoard{
	public Player player1 = new HumanPlayer("Player1");
	public Player player2 = new HumanPlayer("Player2");
	public Player player3 = new HumanPlayer("Player3");
	private static final long serialVersionUID = 1L;
	protected MouseHandle mousehandle[][] = new MouseHandle[Constants.SIZE][Constants.SIZE];
	public Threeguiboard() {
		isOK=true;
		for (int i=0;i<15;i++) {
			for (int j=0;j<15;j++) {
				mousehandle[i][j]= new MouseHandle(i,j);
			}
		}
		this.addBG();
	}	
	
	/**
	 * @override
	 */
	public void addCpu(){
		player2= new CpuPlayer("Player2");
		player3= new CpuPlayer("Player3");
		System.out.println("加了CPU");
	}
	/**
	 * 给按钮加监听
	 */
	public void AddListener() {
		for (int i=0;i<15;i++) {
			for (int j=0;j<15;j++) {
				buttonList[i][j].addMouseListener(mousehandle[i][j]);
			}
		}
	}	
	/**
	 *  去除监听
	 */
	public void removeListener() {
		for (int i=0;i<15;i++) {
			for (int j=0;j<15;j++) {
				buttonList[i][j].removeMouseListener(mousehandle[i][j]);
			}
		}
		System.out.println("Removed");
	}
	/**
	 * 
	 * @author luck
	 * @version 2013.3.30
	 * 内部类 监听
	 */
	class MouseHandle extends MouseAdapter {
		int X;
		int Y;
		public MouseHandle(int a, int b) {
			X=a;
			Y=b;
		}
		public void mouseEntered(MouseEvent e) {
			if (flag[X][Y] == 0) {
				
				((JButton) e.getSource()).setIcon(new ImageIcon(Theme.choose));
			}
		}
		public void mouseExited(MouseEvent e) { 
			if(flag[X][Y] == 0) {
				((JButton) e.getSource()).setIcon(null);
			}
		}
		
		public void mouseClicked(MouseEvent e){
			if(flag[X][Y] == 0) {
				if (color==1) {
					((JButton) e.getSource()).setIcon(new ImageIcon (Theme.black));
				}	else if (color ==2) {
					((JButton) e.getSource()).setIcon(new ImageIcon (Theme.white));

				} else {
					((JButton) e.getSource()).setIcon(new ImageIcon (Theme.red));

				}
				
				Media.playSound(Sound.move);
				check(X,Y);
				flag[X][Y]=1;
				Retractable=true;
				step++;
				new checkModelThread().start();
			}
		}

 }

	/**
	 * 
	 * @param temp
	 */
	public void SetIcon(Integer[] temp) {
		if (temp[2]!=3)
		buttonList[temp[0]][temp[1]].setIcon(new ImageIcon( temp[2]==Constants.BLACK? Theme.black:Theme.white));
		else {
			buttonList[temp[0]][temp[1]].setIcon(new ImageIcon(Theme.red));
		}
	}

	
	/**
	 * 
	 * @param a
	 * @param b
	 * 判断胜负
	 */
	
public void check(int a, int b) { 

	chessboard.set(a,b,color,activeplayer);

	isWin=(checkWin.checkWinFour(a, b, chessboard.getChessBoard()));
	
	if (isWin) {
		removeListener();
		removenewListener();
		isEnd=false;
		int pass=0;
		if (color!=3) {
			pass=DialogCreator.oneButtonDialog("Winner", color==1?Language.BLACKWIN:Language.WHITEWIN);
		} else {
			pass=DialogCreator.oneButtonDialog("Winner", Language.REDWIN);
		}
		if (pass==1 &&activeplayer==1){
			
			isPass=true;
			isEnd=true;
		}else {
			isEnd=true;
		}
	}
		
}
	public Integer[] unset() {
		Integer temp[] = chessboard.unset();
		buttonList[temp[0]][temp[1]].setIcon(null);
		flag[temp[0]][temp[1]]=0;
		if (color!=1){
		color = color-1;
		activeplayer = activeplayer-1;
		} else {
			color=3;
			activeplayer=3;
		}
		Retractable=false;
		return temp;
	}
	public void changeplayer(){
		activeplayer = activeplayer %3 +1; 
		color = color % 3 +1;
	}
}
	