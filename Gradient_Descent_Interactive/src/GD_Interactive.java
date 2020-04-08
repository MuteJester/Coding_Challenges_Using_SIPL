import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

public class GD_Interactive {
	
	public static Math_Toolbox tlb = new Math_Toolbox();
	public static Color_Palette CSET = new Color_Palette();

	public SPlot plot = new SPlot();

	public double Leaning_Rate = 0.0001;
	public int Number_Of_Rows;
	public int number_of_iterations = 1000;


	static final class kl implements KeyListener {
		boolean state = false;
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				state = true;
				
			}
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	static final class ml implements MouseListener{
		public  int last_x=-1;
		public  int last_y=-1;
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			
			last_x = e.getX();
			last_y = e.getY();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	public GD_Interactive () {
		
		
		
		ArrayList<Point> points = new ArrayList<Point>();
		ArrayList<String> X_values = new ArrayList<String>();
		ArrayList<String> Y_values = new ArrayList<String>();
		Java_Brain brn = new Java_Brain();
		double Max_X = Double.MIN_VALUE,Max_Y = Double.MIN_VALUE;
		points.add(new Point(100,100,0));


		
		
		
		double y_val,tx,ty;

    	Image plot = this.plot.Get_Scatter_Plot(points, "X", "Y");
    	kl keyList = new kl();
    	ml mouseList = new ml();
    	SIPL_Window sw = new SIPL_Window(plot.IMG);
		sw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		sw.addKeyListener(keyList);
		sw.addMouseListener(mouseList);
		
		while(keyList.state == false) {			
			try {
				TimeUnit.MILLISECONDS.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(mouseList.last_x != -1 && mouseList.last_y != -1) {
				plot.Draw_Circle(mouseList.last_x, mouseList.last_y, 3, CSET.Royal_Blue,"Fill");
				tx = tlb.Remap((float)mouseList.last_x, 105, (float)720, 0, 100);
				ty = tlb.Remap((float)mouseList.last_y, (float)565 , (float)(80) ,0, 100);
				points.add(new Point(tx,ty,0));
				System.out.println("TX: " + tx +" TY: " + ty);
				X_values.add(String.format("%f",(double)tx));
				Y_values.add(String.format("%f",(double)ty));
				mouseList.last_x = -1;
				mouseList.last_y = -1;
				plot.Commint_Matrix_Changes();
				sw.Refresh_Frame(plot.IMG);
			
			}
			
			
		}
		
		int z =0;
		Point LE = new Point();
		
		for(int i = 0 ;i<points.size();i++) {
			if(points.get(i).x > Max_X) {
				Max_X = points.get(i).x;
			}
			if(points.get(i).y > Max_Y) {
				Max_Y = points.get(i).y;
			}
		}
		Max_X+=Max_X/4;
		Max_Y+=Max_Y/4;
		plot.Update_Pixel_Matrix();
		
		Image nolines = new Image(plot);
		while(z<number_of_iterations) {
			//calculating gradient decent and getting the equation
			LE = brn.Step_Gradient(LE.y, LE.x, Leaning_Rate, X_values, Y_values);
			System.out.println(LE);
			//line drawing and mapping
			for(double p =0 ;p<=100;p+=0.01) {
				y_val = LE.x * p + LE.y;
				tx = tlb.Remap((float)p, 0, (float)100, 105, 720);
				ty = tlb.Remap((float)y_val, (float)0 , (float)(Max_Y) ,565, 80);
				
				plot.Draw_Circle((int)tx, (int)ty, 1, CSET.Red,"Fill");

			
			}
			plot.Commint_Matrix_Changes();
			
			//next iter
			z++;

			//frame rate control
			try {
				TimeUnit.MILLISECONDS.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sw.Refresh_Frame(plot.IMG);
			
			//returning to original background
			for(int a = 0 ;a<plot.Image_Height;a++) {
				for(int b = 0; b<plot.Image_Width;b++) {
					plot.Pixel_Matrix[a][b] = new Pixel(nolines.Pixel_Matrix[a][b]);
				}
			}
		}

	}
	
}
