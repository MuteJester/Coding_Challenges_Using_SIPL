
public class PF_QL_Game {
	int Field_Width;
	int Field_Height;
	int Field[][];
	int FieldC[][];
	int Pos_X;
	int Pos_Y;
	int number_of_steps = 0;
	boolean is_Alive=true;
	boolean Win=false;
	Image visual = new Image();

	
	public  PF_QL_Game(int F_Width,int F_Height) {
		this.Field_Height=F_Height;
		this.Field_Width = F_Width;
		this.Field = new int[F_Height][F_Width];
		visual.Load_Blank_Canvas(F_Height, F_Width, new Pixel(230,230,230));
		for(int i=0;i<F_Height;i++) {
			for(int j=0;j<F_Width;j++) {
				this.Field[i][j] =0;
			}
		}
	}
	public void Set_Hole_Position(int Pos_X,int Pos_Y) {
		if(Pos_X < 0 || Pos_X > this.Field_Width-1 || Pos_Y<0 || Pos_Y > this.Field_Height-1) {
			return;
		}
		this.Field[Pos_Y][Pos_X]=-1;
		this.FieldC=this.Field.clone();

	}
	public void Set_Target_Position(int Pos_X,int Pos_Y) {
		if(Pos_X < 0 || Pos_X > this.Field_Width-1 || Pos_Y<0 || Pos_Y > this.Field_Height-1) {
			return;
		}
		this.Field[Pos_Y][Pos_X]=1;
		this.FieldC=this.Field.clone();

	}
	public void Set_Start_Position(int Pos_X,int Pos_Y) {
		if(Pos_X < 0 || Pos_X > this.Field_Width-1 || Pos_Y<0 || Pos_Y > this.Field_Height-1) {
			return;
		}
		this.Field[Pos_Y][Pos_X]=5;
		this.FieldC=this.Field.clone();
	}
	public void Move(int Action) {
		switch(Action) {
		case 1:
			if(Pos_Y > 0) {
				this.Field[Pos_Y][Pos_X]=0;
				this.Pos_Y--;
				if(this.Field[Pos_Y][Pos_X] == 1) {
					this.Win=true;
				}
				else if(this.Field[Pos_Y][Pos_X] == -1) {
					this.is_Alive=false;
				}
				this.Field[Pos_Y][Pos_X]=5;
				this.number_of_steps++;

			}
			break;
		case 2:
			if(Pos_Y < this.Field_Height-1) {
				this.Field[Pos_Y][Pos_X]=0;
				this.Pos_Y++;
				if(this.Field[Pos_Y][Pos_X] == 1) {
					this.Win=true;
				}
				else if(this.Field[Pos_Y][Pos_X] == -1) {
					this.is_Alive=false;
				}
				this.Field[Pos_Y][Pos_X]=5;
				this.number_of_steps++;

			}
			break;
		case 3:
			if(Pos_X <this.Field_Width-1) {
				this.Field[Pos_Y][Pos_X]=0;
				this.Pos_X++;
				if(this.Field[Pos_Y][Pos_X] == 1) {
					this.Win=true;
				}
				else if(this.Field[Pos_Y][Pos_X] == -1) {
					this.is_Alive=false;
				}
				this.Field[Pos_Y][Pos_X]=5;
				this.number_of_steps++;


			}
			break;
		case 4:
			if(Pos_X > 0) {
				this.Field[Pos_Y][Pos_X]=0;
				this.Pos_X--;
				if(this.Field[Pos_Y][Pos_X] == 1) {
					this.Win=true;
				}
				else if(this.Field[Pos_Y][Pos_X] == -1) {
					this.is_Alive=false;
				}
				this.Field[Pos_Y][Pos_X]=5;
				this.number_of_steps++;

			}
		}
	}
	public void Show_In_Console() {
		for(int i=0;i<this.Field_Height;i++) {
			for(int j=0;j<this.Field_Width;j++) {
				switch (this.Field[i][j]) {
				case 0:
					System.out.print("_ ");
					break;
				case 1:
					System.out.print("T ");
					break;
				case -1:
					System.out.print("H ");
					break;
				case 5:
					System.out.print("X ");
					break;
				}
			
			}
			System.out.print("\n");
		}
		System.out.print("\n");

	}
	public void Reset_Game() {
		this.number_of_steps=0;
		this.Pos_X = 0;
		this.Pos_Y=0;
		this.is_Alive = true;
		this.Win=false;
		this.Field = this.FieldC.clone();
	}
	public Image get_Visual() {
	
		for(int i=0;i<this.Field_Height;i++) {
			for(int j=0;j<this.Field_Width;j++) {
				switch (this.Field[i][j]) {
				case 0:
					break;
				case 1:
					visual.Set_Color(i, j, new Pixel(0,255,0));
					break;
				case -1:
					visual.Set_Color(i, j, new Pixel(255,0,0));
					break;
				case 5:
					visual.Set_Color(i, j, new Pixel(0,0,255));
					break;
				}
			
			}
			System.out.print("\n");
		}
		System.out.print("\n");
		Image scale = new Image(visual);
		scale.Set_Scale(600, 600);
		return scale;
	}
}
