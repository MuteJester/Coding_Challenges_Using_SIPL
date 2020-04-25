import javax.swing.JFrame;

public class LangtonAnt_Dev {

	public static void main(String[] args) {
		Color_Palette CSET = new Color_Palette();
		Image Canvas = new Image();
		int image_w=600,image_h=600;
		Canvas.Load_Blank_Canvas(image_w, image_h, CSET.White_Smoke);
		SIPL_Window frame = new SIPL_Window(Canvas.IMG);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Math_Toolbox tlb = new Math_Toolbox();
		class Ant{
			int pos_x=0,pos_y=0;
			int looks_at = 0;
			int step_size = 3;
			public void step() {
				switch(looks_at) {
				case 0:
					pos_y+=step_size;
					if(pos_y > image_h-4) {
						pos_y = 3;
					}
					break;
				case 1:
					pos_y-=step_size;
					if(pos_y < 2) {
						pos_y = image_h-4;
					}
					break;
				case 2:
					pos_x-=step_size;
					if(pos_x < 2) {
						pos_x = image_w-4;
					}
					break;
				case 3:
					pos_x+=step_size;
					if(pos_x > image_w-4) {
						pos_x =3 ;
					}
					break;
					
				}
			}
			public void turn(int color) {
				switch(color) {
				case 1:
					switch(looks_at) {
					case 0:
						looks_at = 3;
						break;
					case 1:
						looks_at = 2;
						break;
					case 2:
						looks_at = 0;
						break;
					case 3:
						looks_at = 1;

						break;
					}
					break;
				case 2:
					switch(looks_at) {
					case 0:
						looks_at = 2;
						break;
					case 1:
						looks_at = 3;
						break;
					case 2:
						looks_at =1;
						break;
					case 3:
						looks_at =0;
						break;
					}
					break;
				}
			}
			
			
		}
		Ant ant = new Ant();
		ant.pos_x=image_w/2;
		ant.pos_y=image_h/2;
		Canvas.Update_Pixel_Matrix();
	
		while(true) {
			
			if(Canvas.Pixel_Matrix[ant.pos_y][ant.pos_x].r == CSET.White_Smoke.r &&
					Canvas.Pixel_Matrix[ant.pos_y][ant.pos_x].g == CSET.White_Smoke.g &&
					Canvas.Pixel_Matrix[ant.pos_y][ant.pos_x].b == CSET.White_Smoke.b) 
			{
				ant.turn(1);
				//Canvas.Set_Color(ant.pos_y, ant.pos_x,CSET.Black);
				Canvas.Draw_Square(ant.pos_x, ant.pos_y, 1, 1, CSET.Black,"Fill");
				ant.step();

			}
			else if(Canvas.Pixel_Matrix[ant.pos_y][ant.pos_x].r == CSET.Black.r &&
					Canvas.Pixel_Matrix[ant.pos_y][ant.pos_x].g == CSET.Black.g &&
					Canvas.Pixel_Matrix[ant.pos_y][ant.pos_x].b == CSET.Black.b) 
			{
				ant.turn(2);
				//Canvas.Set_Color(ant.pos_y, ant.pos_x,CSET.White_Smoke);
				Canvas.Draw_Square(ant.pos_x, ant.pos_y, 1, 1, CSET.White_Smoke,"Fill");
				ant.step();
			}
			Canvas.Update_Pixel_Matrix();
			frame.Refresh_Frame(Canvas.IMG,600,600);
		}
		
		
		

	}

}
