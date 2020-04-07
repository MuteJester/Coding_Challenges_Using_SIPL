import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

class Snake implements KeyListener{
	public int X_Dir,Y_Dir;
	int length ;
	int step_size = 4;
	public ArrayList<Point> Body;
	Math_Toolbox tlb = new Math_Toolbox();
	Point Food_Location = new Point((double)tlb.random_int_in_range(35, 465),(double)tlb.random_int_in_range(35, 465),0);
	public int Score=0;
	public Snake() {
		Body = new ArrayList<Point>();
		X_Dir = Y_Dir=0;
		length =1;
		
	}
	
	public void Update() {
		
		if(Body.get(Body.size()-1).x >= Food_Location.x-5 && Body.get(Body.size()-1).x <= Food_Location.x+5 &&
				Body.get(Body.size()-1).y >= Food_Location.y-5 && Body.get(Body.size()-1).y <= Food_Location.y+5) {
			Score++;
			Food_Location = new Point((double)tlb.random_int_in_range(30, 465),(double)tlb.random_int_in_range(35, 465),0);
			Point Head = new Point(this.Body.get(this.Body.size()-1));
			this.Body.add(Head);
			this.length++;
		}
		if(Body.get(Body.size()-1).x < 14 || Body.get(Body.size()-1).x >465 || Body.get(Body.size()-1).y < 35 || Body.get(Body.size()-1).y >465) {
			JOptionPane.showMessageDialog(null, "You Lost!\n Score: " + Score);
			System.exit(0);
		}
		
		
		
		Point Head = new Point(this.Body.get(this.Body.size()-1));
	
		this.Body.remove(0);
		Head.x += X_Dir;
		Head.y += Y_Dir;
		this.Body.add(Head);
		
		

		
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
	    if(e.getKeyCode() ==  KeyEvent.VK_UP) {
	    	X_Dir =0;
	    	Y_Dir = -step_size;
	    }
	    else if(e.getKeyCode() ==  KeyEvent.VK_DOWN) {
	    	X_Dir =0;
	    	Y_Dir = step_size;
	    }
	    else if(e.getKeyCode() ==  KeyEvent.VK_LEFT) {
	    	X_Dir =-step_size;
	    	Y_Dir = 0;
	    }
	    else if(e.getKeyCode() ==  KeyEvent.VK_RIGHT) {
	    	X_Dir = step_size;
	    	Y_Dir = 0;
	    }
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	
}

public class Snake_Functionality  {
	Snake snake = new Snake();
	SIPL_Window frame;
	JLabel score_text = new JLabel("Score: ");
	Image Background = new Image();
	Color_Palette CSET = new Color_Palette();
	Snake_Functionality(){
		Math_Toolbox tlb = new Math_Toolbox();
		Background.Load_Blank_Canvas(500, 500, new Pixel(30,30,30));
		frame = new SIPL_Window(Background.IMG);
		score_text.setBounds(100, 100, 100, 100);
		frame.add(score_text);
		frame.setFocusable(true);
		frame.addKeyListener(snake);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Image temp;
		
		Background.Draw_Square(30, 8, 470, 470, CSET.Cyan, "Corners");
		snake.Body.add(new Point(250,250,0));
		//snake.Body.add(new Point(240,250,0));
		temp = new Image(Background);
		
		while(true) {
			
			
			for(int i = 0;i<snake.Body.size();i++) {
			temp.Draw_Square((int)snake.Body.get(i).x, (int)snake.Body.get(i).y, 5, 5, new Pixel(CSET.Lime),"Fill");
			}
			temp.Draw_Square((int)snake.Food_Location.x, (int)snake.Food_Location.y, 2, 2, new Pixel(CSET.Red),"Fill");


			snake.Update();
			
			try {
				TimeUnit.MILLISECONDS.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			frame.Refresh_Frame(temp.IMG);

			
			temp.Commint_Matrix_Changes();

			
		}

	
	}	
}
