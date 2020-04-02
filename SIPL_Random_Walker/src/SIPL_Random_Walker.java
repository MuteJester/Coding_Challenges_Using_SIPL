import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;


public class SIPL_Random_Walker {
	public static Color_Palette CSET = new Color_Palette();
	public static int canvas_width = 900;
	public static int canvas_height = 600;
	public static Pixel Background_Color = CSET.Black;
	public static Math_Toolbox tlb = new Math_Toolbox();


public static class Walker{
	public int pos_x,pos_y;
	int step_size;
	public static Random rand = new Random();
	public void Move(int direction,int amount) {
		switch(direction) {
		case 1:
			if(pos_y + amount > 5 && (pos_y + amount)  < canvas_height - 5) {
				pos_y +=amount;
			}
			break;
		case 2:
			if(pos_x + amount > 5 && (pos_x + amount)  < canvas_width - 5) {
				pos_x +=amount;
			}
			break;
		case 4:
			if(pos_x + amount > 5 && (pos_x + amount)  < canvas_width - 5 && pos_y + amount > 5 && (pos_y + amount)  < canvas_height - 5) {
				pos_x +=amount;
				pos_y +=-amount;
			}
			break;
		case 3:
			if(pos_x + amount > 5 && (pos_x + amount)  < canvas_width - 5 && pos_y - amount > 5 && (pos_y - amount)  < canvas_height - 5) {
				pos_x +=amount;
				pos_y +=amount;
			}
			break;
		
		}
	}
	public void Tik() {
		int step;
		boolean state =rand.nextBoolean();
		if(state == true) {
			step = 1;
		}else {
			step = -1;
		}
		this.Move(tlb.random_int_in_range(1, 5),step);
	}

}




	public static void main(String[] args) {

		Image Canvas = new Image();
		Canvas.Load_Blank_Canvas(canvas_height, canvas_width, Background_Color);
		SIPL_Window sw = new SIPL_Window(Canvas.IMG);
		sw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Walker walker = new Walker();
		Walker walker2 = new Walker();
		walker2.pos_x=canvas_width/3;
		walker2.pos_y=canvas_height/3;
		walker.pos_x=canvas_width/2;
		walker.pos_y=canvas_height/2;
		while(true) {
			Canvas.Set_Color(walker.pos_y, walker.pos_x, CSET.Cyan);
			Canvas.Set_Color(walker2.pos_y, walker.pos_x, CSET.Red);

			//Canvas.Draw_Circle(walker.pos_x, walker.pos_y, 2, CSET.Cyan,"Fill");
			walker.Tik();
			walker2.Tik();

			try {
				TimeUnit.MILLISECONDS.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			sw.Refresh_Frame(Canvas.IMG);
		}

	}

}
