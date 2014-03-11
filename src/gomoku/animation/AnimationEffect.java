package gomoku.animation;

import gomoku.main.Gomoku;

import java.awt.Point;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;



public class AnimationEffect {
	static Point point1;
	static Point point2;
	static Point point3;
	static Point point4;
	static Point point5;
	static Point currentCenter;
	static Point targetPoint;
	public static JButton[] moveList;	
	/**
	 * 
	 * @author NorviNS
	 * version 2013.4.2
	 *
	 */	
	public static void simpleAnimation(int bgNum,int numInAll,JLabel aniLabel,ImageIcon imageToAnimate,String chooseLabelName[]){
		while(true){
			try {
				if(bgNum < numInAll-1){
					bgNum++;
				}
				else{
					bgNum = 0;
				}
				imageToAnimate = new ImageIcon(chooseLabelName[bgNum]);
				aniLabel.setIcon(imageToAnimate);
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}	
	/**
	 * 
	 * @author NorviNs
	 * version 4.3
	 * 
	 */
	public static void turnLeft(ArrayList<JButton> quickList){
		JButton tempButton;
		tempButton = quickList.get(0);
		quickList.set(0, quickList.get(1));
		quickList.set(1, quickList.get(2));
		quickList.set(2, quickList.get(3));
		quickList.set(3, quickList.get(4));
		quickList.set(4, tempButton);
	}
	public static void turnRight(ArrayList<JButton> quickList){
		JButton tempButton;
		tempButton = quickList.get(4);
		quickList.set(4, quickList.get(3));
		quickList.set(3, quickList.get(2));
		quickList.set(2, quickList.get(1));
		quickList.set(1, quickList.get(0));
		quickList.set(0, tempButton);
 	}
	public static void moveLeft(ArrayList<JButton> quickList,int x){
		quickList.get(4).setLocation((int) (10+x*33), (int) function0(10+x*33));
		quickList.get(0).setLocation((int) (100-x*4.5),(int) function(100-x*4.5));
		quickList.get(1).setLocation((int) (340-x*12), (int) function(340-x*12));
		quickList.get(2).setLocation((int) (580-x*12),(int) function(580-x*12));
		quickList.get(3).setLocation((int) (670-x*4.5),(int) function(670-x*4.5));
//		quickList.get(2).grabFocus();
	}	
	public static void moveRight(ArrayList<JButton> quickList,int x){
		
		quickList.get(0).setLocation((int) (670-x*33), (int) function0(670-x*33));
		quickList.get(1).setLocation((int) (10+x*4.5),(int) function(10+x*4.5));
		quickList.get(2).setLocation((int) (100+x*12), (int) function(100+x*12));
		quickList.get(3).setLocation((int) (340+x*12),(int) function(340+x*12));
		quickList.get(4).setLocation((int) (580+x*4.5),(int) function(580+x*4.5));
		
//		quickList.get(2).grabFocus();
		/*
		 * test code, please ignore it
		quickList.get(1).setLocation((int) (10), (int) function0(10));
		quickList.get(2).setLocation((int) (100),(int) function(100));
		quickList.get(3).setLocation((int) (225), (int) function(225));
		quickList.get(4).setLocation((int) (580),(int) function(580));
		quickList.get(0).setLocation((int) (670),(int) function(670));
		*/
		
	}	
	public static double function(double x){
		double y;
		y = -0.001364522417*(x-340)*(x-340)+528.5964912;
		return y;
	}
	public static double function0(double x){
		double y;
		y = 0.0009182736455*(x-340)*(x-340)+280;
		return y;
	}
}
