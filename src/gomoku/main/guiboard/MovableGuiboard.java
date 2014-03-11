package gomoku.main.guiboard;
import gomoku.constants.Constants;
import gomoku.sound.Media;
import gomoku.sound.Sound;
import gomoku.theme.Theme;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class MovableGuiboard extends DoubleGuiBoard{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected  int chessNumber[] = new int[3] ;
	protected int totalNumber[] = new int[3];
	protected int templocation[] = new int[2];
	protected int state=0;
	public MovableGuiboard(){
		isOK=true;
	}
	public void initialNumber() {
		chessNumber[1]=16;
		chessNumber[2]=16;
		totalNumber[1]=16;
		totalNumber[2]=16;
	}

	public void AddnewListener() {
	}	
	class newMouseHandle extends MouseAdapter {
		int X;
		int Y;
		public newMouseHandle(int a, int b ) {
			X=a;
			Y=b;
		}
		/**
		 * 监听1： 鼠标移动到相应位置显示choose图标
		 * 
		 */
		public void mouseEntered(MouseEvent e) {
			if (state==1) {
				if (flag[X][Y] == 0 && Math.abs(templocation[0]-X)+Math.abs(templocation[1]-Y)==1) {			
					((JButton) e.getSource()).setIcon(new ImageIcon(Theme.choose));
				}	
			} 
			
		}
		/**
		 * 监听2：鼠标离开图标消失
		 */
		public void mouseExited(MouseEvent e) { 
			if(flag[X][Y] == 0) {
				((JButton) e.getSource()).setIcon(null);
			}
		}
		/**
		 * 监听3： 下去
		 */
		
		public void mouseClicked(MouseEvent e){
			if (state==1) {
				if(flag[X][Y] == 0 && isnearby(X, Y)) {
					((JButton) e.getSource()).setIcon(new ImageIcon( color==Constants.BLACK? Theme.black:Theme.white));
					buttonList[templocation[0]][templocation[1]].setIcon(null);
					chessboard.delete(templocation[0], templocation[1]);
					Media.playSound(Sound.move);			
					check(X,Y);
					state=0;
					flag[X][Y]=1;
					flag[templocation[0]][templocation[1]]=0;
					chessboard.delete(templocation[0], templocation[1]);
					step++;
					new WaitThread().start();
					AddListener();
				}
				else if (flag[X][Y]==1) {
					if (color==chessboard.getcolor(X,Y)){
						buttonList[templocation[0]][templocation[1]].setIcon(new ImageIcon(color==Constants.BLACK?Theme.black:Theme.white));
						templocation[0]=X;
						templocation[1]=Y;
						buttonList[templocation[0]][templocation[1]].setIcon(new ImageIcon(color==Constants.BLACK?Theme.black_choose:Theme.white_choose));
						
					}
				}
			} else if (state==0) {
				if (flag[X][Y]==1&&color==chessboard.getcolor(X,Y)){
					templocation[0]=X;
					templocation[1]=Y;
					state=1;
					buttonList[templocation[0]][templocation[1]].setIcon(new ImageIcon(color==Constants.BLACK?Theme.black_choose:Theme.white_choose));

				}
			}
			
		}
	}
	public boolean isnearby(int X, int Y) {
		return true;
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
		/**
		 * 监听1： 鼠标移动到相应位置显示choose图标
		 * 
		 */
		public void mouseEntered(MouseEvent e) {
			if (flag[X][Y] == 0 && chessNumber[activeplayer]>0) {
				
				((JButton) e.getSource()).setIcon(new ImageIcon(Theme.choose));
			}
		}
		/**
		 * 监听2：鼠标离开图标消失
		 */
		public void mouseExited(MouseEvent e) { 
			if(flag[X][Y] == 0) {
				((JButton) e.getSource()).setIcon(null);
			}
		}
		/**
		 * 监听3： 下去
		 */
		public void mouseClicked(MouseEvent e){
			if(flag[X][Y] == 0 && chessNumber[activeplayer]>0) {
				((JButton) e.getSource()).setIcon(new ImageIcon( color==Constants.BLACK? Theme.black:Theme.white));
				Media.playSound(Sound.move);
				flag[X][Y]=1;
				step++;
				chessNumber[activeplayer]--;
				if (chessNumber[activeplayer]<=0) {
					Retractable=false;
				} else {
					Retractable=true;
				}
				check(X,Y);
				new WaitThread().start();
			}
			
		}
	}
	class WaitThread extends Thread{
		public void run(){
			try {
				sleep(100);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			CheckModel();
			if (chessNumber[activeplayer]<=0) {
				AddnewListener();
			} 
		}
	}
}
