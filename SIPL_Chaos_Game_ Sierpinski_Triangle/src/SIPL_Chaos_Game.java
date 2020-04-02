import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

public class SIPL_Chaos_Game {
	public static Color_Palette CSET = new Color_Palette();
	public static Math_Toolbox tlb = new Math_Toolbox();

	public static int Starting_Random_Values = 13000;
	public static int canvas_width = 600;
	public static int canvas_height = 600;
	public static Pixel Background_Color = CSET.Azure;
	
	public static void main(String[] args) {
		Image canvas = new Image();
		canvas.Load_Blank_Canvas(canvas_height, canvas_width, Background_Color);
		Point p1 = new Point((int)tlb.random_int_in_range(2, canvas_width-3),(int)tlb.random_int_in_range(1, canvas_height-1),0),
				p2= new Point((int)tlb.random_int_in_range(2, canvas_width-3),(int)tlb.random_int_in_range(1, canvas_height-1),0),
				p3= new Point((int)tlb.random_int_in_range(2, canvas_width-3),(int)tlb.random_int_in_range(1, canvas_height-1),0);
		SIPL_Window frame = new SIPL_Window(canvas.IMG);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Point move = new Point(canvas_width/2,canvas_height/2,0);
		//canvas.Draw_Circle((int)p1.x, (int)p1.y, 2, CSET.Dark_Red,"Fill");
		//canvas.Draw_Circle((int)p2.x, (int)p2.y, 2, CSET.Dark_Red,"Fill");
		//canvas.Draw_Circle((int)p3.x, (int)p3.y, 2, CSET.Dark_Red,"Fill");
		canvas.Set_Color((int)p1.y, (int)p1.x, CSET.Lime);
		canvas.Set_Color((int)p2.y, (int)p2.x, CSET.Lime);
		canvas.Set_Color((int)p3.y, (int)p3.x, CSET.Lime);

		int rand = 0;
		
		while(true) {
			
			//canvas.Draw_Circle((int)move.x, (int)move.y, 2, CSET.Dark_Red,"Fill");
			 rand = tlb.random_int_in_range(0, 3);
			 
			 switch(rand) {
			 case 0:
				 move = move.lerp(p1, 0.5);
					canvas.Set_Color((int)move.y, (int)move.x,  CSET.Lime);
				 break;
			 case 1:
				 move = move.lerp(p2, 0.5);
					canvas.Set_Color((int)move.y, (int)move.x,  CSET.Red);
				 break;
			 case 2:
				 move = move.lerp(p3, 0.5);
					canvas.Set_Color((int)move.y, (int)move.x,  CSET.Blue);

				 break;
			 }
			

			
			frame.Refresh_Frame(canvas.IMG);
		}
	}

}
