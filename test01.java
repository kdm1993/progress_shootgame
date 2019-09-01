package shootgame;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextLayout;
import java.util.ArrayList;
import javax.swing.JFrame;

public class test01 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new test02().start();
		new Sub_Thread().sub_start();
	}

}
class test02 extends JFrame implements KeyListener, Runnable, MouseListener {
	
	boolean dual = true;
	static int player_1_x = 100 , player_1_y = 200;
	static int player_2_x = 100 , player_2_y = 400;
	static int player_1_hp = 107, player_2_hp = 107;
	static int player_1_mana = 109, player_2_mana = 109;
	static int player_1_damage = 1, player_2_damage = 1;
	static int player_1_as = 10, player_2_as = 10;
	static int player_1_pierce = 0, player_2_pierce = 0;
	static int player_1_score = 0, player_2_score = 0;
	static String player_1_char = "ironman", player_2_char = "warmachine";
	int map_x_1 = 0, map_x_2 = 1280;
	int f_width = 1280, f_height=710;
	static Toolkit tk = Toolkit.getDefaultToolkit();
	static Image player_1_char_img = tk.getImage("D://images//ironman_up.png");
	static Image player_2_char_img = tk.getImage("D://images//ironman_up.png");
	Image bullet = tk.getImage("D://images//bullet.png");
	Image gun_bullet = tk.getImage("D://images//bullet//bullet0.png");
	Image kill = tk.getImage("D://images//kill//kill0_0.png");
	Image map = tk.getImage("D://images//map.jpg");
	Image map2 = tk.getImage("D://images//map2.jpg");
	Image empty_bar = tk.getImage("D://images//hp//EmptyBar.png");
	Image hp_bar = tk.getImage("D://images//hp//RedBar.png");
	Image coin = tk.getImage("D://images//coin.gif");
	Image kill_bullet = tk.getImage("D://images//kill_bullet.png");
	static Image ironman_sub = tk.getImage("D://images//ironman_sub.png");
	static Image booster = tk.getImage("D://images//booster.gif");
	Image char_ui;
	Image beam;
	Image item_get = tk.getImage("D://images//item_get//item_get0.png");
	Image hit_explo;
	Image score;
	Image bullet_explo;
	Image buffImage = null;
	Graphics buffg = null;
	Thread th;
	static ArrayList<Bullet> p_1_bullet_list = new ArrayList<Bullet>();
	static ArrayList<Bullet> p_2_bullet_list = new ArrayList<Bullet>();
	static ArrayList<Kill> kill_list = new ArrayList<Kill>();
	static ArrayList<Kill_Bullet> kill_bullet_list = new ArrayList<Kill_Bullet>();
	static ArrayList<Explosion> explo_list = new ArrayList<Explosion>();
	static ArrayList<Bullet_Explosion> bullet_explo_list = new ArrayList<Bullet_Explosion>();
	static ArrayList<Coin_Effect> coin_list = new ArrayList<Coin_Effect>();
	static ArrayList<Hit_Explosion> hit_list = new ArrayList<Hit_Explosion>();
	static ArrayList<Item_Effect> item_list = new ArrayList<Item_Effect>();
	static ArrayList<Item_Get_Effect> item_get_list = new ArrayList<Item_Get_Effect>();
	static ArrayList<Ironman_sub> ironman_sub_list = new ArrayList<Ironman_sub>();
 	Image explosion;
	Bullet b;
	Charge_Effect ce;
	Bullet_Explosion be;
	Kill k;
	Kill_Bullet kb;
	Explosion e;
	Coin_Effect c;
	Hit_Explosion he;
	Item_Effect ie;
	Item_Get_Effect ige;
	GunShot_Effect ge1;
	GunShot_Effect ge2;
	Ironman_sub sub;
	static int bullet_count_1 = 0, bullet_count_2 = 0, bullet_count_3 = 0, kill_count = 0, right_button_count = 0, beam_count = 0, beam_img=0;
	static boolean player_1_left, player_1_right, player_1_up, player_1_down, player_1_left_button, player_1_left_button_count, player_1_right_button;
	static boolean player_2_left, player_2_right, player_2_up, player_2_down, player_2_left_button, player_2_left_button_count, player_2_right_button;
	static boolean beam_check;
	static boolean gun_check;
	static boolean right_beam;
	static int timer = 0, timer_count = 0;
	boolean test_count;

	public void start() {
		hp_bar = tk.getImage("D://images//hp//RedBar.png");
		player_1_char_img = tk.getImage("D://images//"+player_1_char+"_up.png");
		player_2_char_img = tk.getImage("D://images//"+player_2_char+"_up.png");
		Dimension dim = new Dimension(1280, 720);
		setPreferredSize(dim);
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addKeyListener(this);
		addMouseListener(this);
		setTitle("슈팅게임");
		setFocusable(true);
		setVisible(true);
		setResizable(false);
		th = new Thread(this);
		th.start();
		ge1 = new GunShot_Effect(player_2_x+player_2_char_img.getWidth(null), player_2_y);
		ge2 = new GunShot_Effect(player_2_x+player_2_char_img.getWidth(null), player_2_y);
	}
	public void paint(Graphics g) {
		buffImage = createImage(f_width, f_height);
		buffg = buffImage.getGraphics();
		update(g);
	}
	public void update(Graphics g) {
		Draw();
		Draw_P1_Bullet();
		Draw_P2_Bullet();
		Draw_Kill();
		Draw_Hit_Explosion();
		Draw_Kill_Bullet();
		Draw_Explosion();
		Draw_Bullet_Explosion();
		Draw_Beam();
		Draw_Charge_effect();
		Draw_Coin();
		Draw_Item();
		Draw_GunShot_effect();
		Draw_Item_effect();
		Draw_Ironman_sub();
		Draw_Game_UI();
		Draw_Score();
		Map_Move();
		g.drawImage(buffImage, 0, 0, this);
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while(true) {
				KeyProcess();
				TimerProcess();
				repaint();
				Thread.sleep(16);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void TimerProcess() {
		timer_count+=16;
		if(timer_count>=1000) {
			timer_count = 0;
			timer++;
			System.out.println("진행시간 : "+timer+"초");
		}
	}
	public void KeyProcess() {
		if(player_1_up == true && player_1_y>30) {
			player_1_y-=10;
		}
		if(player_1_down == true && player_1_y<580) {
			player_1_y+=10;
		}
		if(player_1_left == true && player_1_x>0) {
			player_1_x-=10;
		}
		if(player_1_right == true && player_1_x<1000){
			player_1_x+=10;
		}
		if(player_2_up == true && player_2_y>30) {
			player_2_y-=10;
		}
		if(player_2_down == true && player_2_y<580) {
			player_2_y+=10;
		}
		if(player_2_left == true && player_2_x>0) {
			player_2_x-=10;
		}
		if(player_2_right == true && player_2_x<1000){
			player_2_x+=10;
		}
	}
	public void Draw() {
		buffg.clearRect(0, 30, f_width, f_height);
		if(test_count == false) 
		{			
			bullet_explo_list.add(new Bullet_Explosion(-500, -500));
			be = bullet_explo_list.get(0);
			be.next_img();
			explo_list.add(new Explosion(-500, -500));
			e = explo_list.get(0);
			e.next_img();
			kill_list.add(new Kill(-500, -500));
			k = kill_list.get(0);
			k.next_img();
			item_get_list.add(new Item_Get_Effect(-500, -500));
			ige = item_get_list.get(0);
			ige.next_img();
			if(be.effect_num >= 37.0) {
				bullet_explo_list.remove(0);				
				bullet_explo_list.remove(0);
				item_get_list.remove(0);
				kill_list.remove(0);
				test_count = true;	
			}
		}
		Image warmachine_gun;
		warmachine_gun = tk.getImage("D://images//warmachine_gun.png");
		buffg.drawImage(map, map_x_1, 0, this);
		buffg.drawImage(map2, map_x_2, 0, this);
		if(player_1_char.equals("warmachine")) {			
			buffg.drawImage(warmachine_gun, player_1_x, player_1_y, this);
		} else if(player_2_char.equals("warmachine") && dual == true){
			buffg.drawImage(warmachine_gun, player_2_x+20, player_2_y, this);
		}
		buffg.drawImage(player_1_char_img, player_1_x, player_1_y, this);
		buffg.drawImage(player_2_char_img, player_2_x, player_2_y, this);
	}
	public void Draw_Score() { //점수 그리는 메소드
		
		int num_1 = player_1_score/1000;
		int num_2 = (player_1_score%1000)/100;
		int num_3 = (player_1_score%100)/10;
		int num_4 = player_1_score%10;
		
		score = tk.getImage("D://images//number//"+num_1+".png");
		buffg.drawImage(score, 300, 80, this);
		score = tk.getImage("D://images//number//"+num_2+".png");
		buffg.drawImage(score, 330, 80, this);
		score = tk.getImage("D://images//number//"+num_3+".png");
		buffg.drawImage(score, 360, 80, this);
		score = tk.getImage("D://images//number//"+num_4+".png");
		buffg.drawImage(score, 390, 80, this);
		
		num_1 = player_2_score/1000;
		num_2 = (player_2_score%1000)/100;
		num_3 = (player_2_score%100)/10;
		num_4 = player_2_score%10;
		
		score = tk.getImage("D://images//number//"+num_1+".png");
		buffg.drawImage(score, 940, 80, this);
		score = tk.getImage("D://images//number//"+num_2+".png");
		buffg.drawImage(score, 970, 80, this);
		score = tk.getImage("D://images//number//"+num_3+".png");
		buffg.drawImage(score, 1000, 80, this);
		score = tk.getImage("D://images//number//"+num_4+".png");
		buffg.drawImage(score, 1030, 80, this);
	}
	public void Draw_P1_Bullet() { //플레이어 1 미사일 그리는 메소드
		for(int i=0; i<p_1_bullet_list.size(); i++) {
			if(p_1_bullet_list.size() > 0) {
				b = (Bullet) p_1_bullet_list.get(i);
				if(player_1_char.equals("ironman")) {
					buffg.drawImage(bullet, b.pos.x, b.pos.y+20, this);
					b.move();					
				}
				if(player_1_char.equals("warmachine")) {
					if(b.bullet_type == 0) {
						gun_bullet = tk.getImage("D://images//bullet//bullet1.png");
						buffg.drawImage(gun_bullet, b.pos.x, b.pos.y+20, this);
						b.move4();
					} else if(b.bullet_type == 1) {
						gun_bullet = tk.getImage("D://images//bullet//bullet2.png");
						buffg.drawImage(gun_bullet, b.pos.x, b.pos.y+20, this);
						b.move2();					
					} else if(b.bullet_type == 2) {
						gun_bullet = tk.getImage("D://images//bullet//bullet3.png");
						buffg.drawImage(gun_bullet, b.pos.x, b.pos.y+20, this);
						b.move();
					} else if(b.bullet_type == 3) {
						gun_bullet = tk.getImage("D://images//bullet//bullet0.png");
						buffg.drawImage(gun_bullet, b.pos.x, b.pos.y+20, this);
						b.move3();
					} else if(b.bullet_type == 4) {
						gun_bullet = tk.getImage("D://images//bullet//bullet4.png");
						buffg.drawImage(gun_bullet, b.pos.x, b.pos.y+20, this);
						b.move5();
					}
				}
				if(b.pos.x > f_width) {
					p_1_bullet_list.remove(i);
				}
			}
		}
	}
	public void Draw_P2_Bullet() { //플레이어 2 미사일 그리는 메소드
		try {
			for(int i=0; i<p_2_bullet_list.size(); i++) {
				if(p_2_bullet_list.size() > 0) {
					b = (Bullet) p_2_bullet_list.get(i);
					if(player_2_char.equals("ironman")) {
						buffg.drawImage(bullet, b.pos.x, b.pos.y+20, this);
						b.move();					
					}
					if(player_2_char.equals("warmachine")) {
						if(b.bullet_type == 0) {
							gun_bullet = tk.getImage("D://images//bullet//bullet0.png");
							buffg.drawImage(gun_bullet, b.pos.x, b.pos.y+20, this);
							b.move4();
						} else if(b.bullet_type == 1) {
							gun_bullet = tk.getImage("D://images//bullet//bullet1.png");
							buffg.drawImage(gun_bullet, b.pos.x, b.pos.y+20, this);
							b.move2();					
						} else if(b.bullet_type == 2) {
							gun_bullet = tk.getImage("D://images//bullet//bullet2.png");
							buffg.drawImage(gun_bullet, b.pos.x, b.pos.y+20, this);
							b.move();
						} else if(b.bullet_type == 3) {
							gun_bullet = tk.getImage("D://images//bullet//bullet3.png");
							buffg.drawImage(gun_bullet, b.pos.x, b.pos.y+20, this);
							b.move3();
						} else if(b.bullet_type == 4) {
							gun_bullet = tk.getImage("D://images//bullet//bullet4.png");
							buffg.drawImage(gun_bullet, b.pos.x, b.pos.y+20, this);
							b.move5();
						}
					}
					if(b.pos.x > f_width) {
						p_2_bullet_list.remove(i);
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void Draw_Kill() { // 적 이미지 그리는 메소드 
		for(int i=0; i<kill_list.size(); i++) {
			if(kill_list.size() > 0) {
				k = (Kill) kill_list.get(i);
				kill = tk.getImage("D://images//kill//kill"+k.kill_num+"_"+(int)k.effect_num+".png");
				buffg.drawImage(kill, k.pos.x, k.pos.y, this);
				buffg.drawImage(empty_bar, k.pos.x, k.pos.y+kill.getHeight(null), this);
				buffg.drawImage(hp_bar, k.pos.x, k.pos.y+kill.getHeight(null),(int)k.width,17, this);
				k.move();
				if(k.cool == true) {
					k.effect_num+=0.1;
					if((int)k.effect_num == 3 && k.shoot == true) {
						kb = new Kill_Bullet(k.pos.x, k.pos.y+20);
						kill_bullet_list.add(kb);
						k.shoot = false;
					}
					if(k.effect_num >= 6.0) {
						k.effect_num = 0.0;
						k.cool = false;
						k.shoot = true;
						k.cooltime = timer;
					}
				}
				if(k.pos.x <= 0-kill.getWidth(this)) {
					kill_list.remove(i);
				}
			}
		}
	}
	public void Draw_Kill_Bullet() { //적 미사일 이미지 그리는 메소드
		for(int v=0; v<kill_bullet_list.size(); v++) {
			kb = (Kill_Bullet)kill_bullet_list.get(v);
			buffg.drawImage(kill_bullet, kb.pos.x, kb.pos.y, this);
			kb.move();
		}
	}
	public void Draw_Explosion()  { //폭발이펙트 출력메소드
		for(int i=0; i<explo_list.size(); i++) {
			e = (Explosion) explo_list.get(i);
			explosion = tk.getImage("D://images//explosion//explosion"+(int)e.effect_num+".png");
			buffg.drawImage(explosion, e.pos.x, e.pos.y, this);
			e.next_img();
			e.move();
			if(e.effect_num >= 19.0) {
				explo_list.remove(i);
			}
		}
	}
	public void Draw_Bullet_Explosion()  { //미사일 폭발이펙트 출력메소드
		for(int i=0; i<bullet_explo_list.size(); i++) {
			be = (Bullet_Explosion) bullet_explo_list.get(i);
			bullet_explo = tk.getImage("D://images//bullet_explo//bullet_explo"+(int)be.effect_num+".png");
			buffg.drawImage(bullet_explo, be.pos.x, be.pos.y, this);
			be.move();
			be.next_img();
			if(be.effect_num >= 37) {
				bullet_explo_list.remove(i);
			}
		}
	}
	public void Draw_Beam() { // 빔 이미지 그리는 메소드
		if(right_beam == true) {
			beam = tk.getImage("D://images//beam//beam"+beam_img+".png");
			buffg.drawImage(beam, player_1_x+30, player_1_y-5, this);
			if(beam_count < 100) {
				beam_img++;				
			} else if(beam_count > 100) {
				beam_img--;
			}
			beam_count++;
			if(beam_count < 100 && beam_img==9) {
				beam_img = 3;
			}
			if(beam_count > 100 && beam_img==0) {
				right_beam = false;
				player_1_right_button = false;
				beam_count = 0;
				beam_img = 0;
				player_1_char_img = tk.getImage("D://images//ironman_up.png");
			}
		}
	}
	public void Draw_Ironman_sub() { // 아이언맨 보조무기 그리는 메소드
		if(player_1_char.equals("ironman")) {
			for(int k=0; k<ironman_sub_list.size(); k++) {
				sub = ironman_sub_list.get(k);
				buffg.drawImage(ironman_sub, sub.pos.x, sub.pos.y, this);
				buffg.drawImage(booster, sub.pos.x-booster.getWidth(null), sub.pos.y+3, this);
				sub.move(k);
			}
		} else {
			
		}
	}
	public void Draw_Coin() {
		for(int i=0; i<coin_list.size(); i++) {
			c = (Coin_Effect) coin_list.get(i);
			buffg.drawImage(coin, c.pos.x, c.pos.y, this);
			c.move();
			if(c.pos.y > f_height || c.pos.x < 0) {
				coin_list.remove(i);
			}
		}
	}
	public void Draw_Item() {
		for(int i=0; i<item_list.size(); i++) {
			ie = (Item_Effect) item_list.get(i);
			buffg.drawImage(ie.item, ie.pos.x, ie.pos.y, this);
			ie.move();
			if(ie.pos.y > f_height || ie.pos.x < 0) {
				item_list.remove(i);
			}
		}
	}
	public void Draw_Item_effect() {
		for(int i=0; i<item_get_list.size(); i++) {
			ige = (Item_Get_Effect) item_get_list.get(i);
			item_get = tk.getImage("D://images//item_get//item_get"+(int)ige.effect_num+".png");
			buffg.drawImage(item_get, ige.pos.x, ige.pos.y, this);
			ige.move();
			ige.next_img();
			if(ige.effect_num >= 19.0) {
				item_get_list.remove(i);
			}
		}
	}
	public void Draw_Charge_effect() {
		if(player_1_right_button == true) {
			Image charge;
			charge = tk.getImage("D://images//charge//charge"+(int)ce.effect_num+".png");
			ce.move();
			if(right_beam == false) {
				buffg.drawImage(charge, ce.pos.x, ce.pos.y-15, this);				
			}
			ce.next_img();
			if(ce.effect_num >= 9.0) {
				ce.effect_num = 0.0;
			}			
		}
	}
	public void Draw_GunShot_effect() {
		if(player_2_left_button == true && ge1 != null) {
			Image gunshot;
			gunshot = tk.getImage("D://images//hit//bullet_hit"+(int)ge1.effect_num+".png");
			ge1.move();
			buffg.drawImage(gunshot, ge1.pos.x, ge1.pos.y-15, this);				
			ge1.next_img();
			if(ge1.effect_num >= 9.0) {
				ge1.effect_num = 0.0;
			}			
		}
		if(player_2_right_button == true && ge2 != null) {
			Image gunshot;
			gunshot = tk.getImage("D://images//hit//bullet_hit"+(int)ge2.effect_num+".png");
			ge2.move();
			buffg.drawImage(gunshot, ge2.pos.x, ge2.pos.y-25, this);				
			ge2.next_img();
			if(ge2.effect_num >= 9.0) {
				ge2.effect_num = 0.0;
			}	
		}
	}
	public void Draw_Hit_Explosion() {
		for(int i=0; i<hit_list.size(); i++) {
			he = (Hit_Explosion) hit_list.get(i);
			hit_explo = tk.getImage("D://images//hit//hit"+(int)he.effect_num+".png");
			buffg.drawImage(hit_explo, he.pos.x, he.pos.y, this);
			//he.move();
			he.next_img();
			if(he.effect_num >= 7.0) {
				hit_list.remove(i);
			}
		}
	}
	public void Draw_Game_UI() {
		char_ui = tk.getImage("D://images//ui//"+player_1_char+"_char_ui.png");
		buffg.drawImage(char_ui, 20, 40, this);
		char_ui = tk.getImage("D://images//ui//hp.png");
		buffg.drawImage(char_ui, 136, 96, player_1_hp, 9, this);
		char_ui = tk.getImage("D://images//ui//mana.png");
		buffg.drawImage(char_ui, 134, 110, this);
		if(dual == true) {
			int x = f_width/2;
			char_ui = tk.getImage("D://images//ui//"+player_2_char+"_char_ui.png");
			buffg.drawImage(char_ui, 20+x, 40, this);
			char_ui = tk.getImage("D://images//ui//hp.png");
			buffg.drawImage(char_ui, 136+x, 96, this);
			char_ui = tk.getImage("D://images//ui//mana.png");
			buffg.drawImage(char_ui, 134+x, 110, this);			
		}
	}
	public void Map_Move() { // 맵 이동 메소드
		map_x_1-=2;
		map_x_2-=2;
		if(map_x_1 == (f_width*-1)) {
			map_x_1 = f_width;
		}
		if(map_x_2 == (f_width*-1)) {
			map_x_2 = f_width;
		}
	}
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		switch(e.getKeyCode()) {
		case KeyEvent.VK_W:
			player_1_up = true;
			if(player_1_right_button == false && right_beam == false) {
				player_1_char_img = tk.getImage("D://images//"+player_1_char+"_up.png");
			}
			break;
		case KeyEvent.VK_A:
			player_1_left = true;
			break;
		case KeyEvent.VK_S:
			player_1_down = true;
			if(player_1_right_button == false && right_beam == false) {
				player_1_char_img = tk.getImage("D://images//"+player_1_char+"_down.png");
			}
			break;
		case KeyEvent.VK_D:
			player_1_right = true;
			break;
		case KeyEvent.VK_J:
			if(player_1_right_button == false && right_beam == false && player_1_char.equals("ironman")) {
				player_1_left_button = true;
			} else if(player_1_char.equals("warmachine")){
				player_1_left_button = true;
			}
			break;
		case KeyEvent.VK_K:
			if(player_1_left_button == false && right_beam == false && player_1_char.equals("ironman")) {
				ce = new Charge_Effect(player_1_x, player_1_y);
				player_1_right_button = true;
				player_1_char_img = tk.getImage("D://images//laser.gif");
			} else {
				player_1_right_button = true;
			}
			break;
		}
		if(dual == true) {
			switch(e.getKeyCode()) {
				case KeyEvent.VK_UP:
					player_2_up = true;
					if(player_2_right_button == false && right_beam == false) {
						player_2_char_img = tk.getImage("D://images//"+player_2_char+"_up.png");
					}
					break;
				case KeyEvent.VK_LEFT:
					player_2_left = true;
					break;
				case KeyEvent.VK_DOWN:
					player_2_down = true;
					if(player_2_right_button == false && right_beam == false) {
						player_2_char_img = tk.getImage("D://images//"+player_2_char+"_down.png");
					}
					break;
				case KeyEvent.VK_RIGHT:
					player_2_right = true;
					break;
				case KeyEvent.VK_NUMPAD5:
					if(player_2_right_button == false && right_beam == false && player_2_char.equals("ironman")) {
						player_2_left_button = true;
					} else if(player_2_char.equals("warmachine")){
						player_2_left_button = true;
					}
					break;
				case KeyEvent.VK_NUMPAD6:
					if(player_2_left_button == false && right_beam == false && player_2_char.equals("ironman")) {
						ce = new Charge_Effect(player_2_x, player_2_y);
						player_2_right_button = true;
						player_2_char_img = tk.getImage("D://images//laser.gif");
					} else if(player_2_char.equals("warmachine") && player_2_right_button == false){
						player_2_right_button = true;
					} else if(player_2_char.equals("warmachine") && player_2_right_button == true){
						player_2_right_button = false;
					}
					break;
			}
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		switch(e.getKeyCode()) {
		case KeyEvent.VK_W:
			player_1_up = false;
			if(player_1_char.equals("ironman")) {
				if(player_1_right_button == false && right_beam == false) {
					player_1_char_img = tk.getImage("D://images//"+player_1_char+"_down.png");
				}				
			} else {
				player_1_char_img = tk.getImage("D://images//"+player_1_char+"_down.png");
			}
			break;
		case KeyEvent.VK_A:
			player_1_left = false;
			break;
		case KeyEvent.VK_S:
			player_1_down = false;
			if(player_1_char.equals("ironman")) {
				if(player_1_right_button == false && right_beam == false) {
					player_1_char_img = tk.getImage("D://images//"+player_1_char+"_down.png");
				}				
			} else {
				player_1_char_img = tk.getImage("D://images//"+player_1_char+"_down.png");
			}
			break;
		case KeyEvent.VK_D:
			player_1_right = false;
			break;
		case KeyEvent.VK_J:
			player_1_left_button = false;
			break;
		case KeyEvent.VK_K:
			if(player_1_char.equals("ironman")) {
				if(right_button_count < 50 && right_beam==false) {
					player_1_char_img = tk.getImage("D://images//ironman_up.png");
					player_1_right_button = false;
				} else if(right_button_count >= 50){
					right_beam = true;
					right_button_count = 0;
					player_1_char_img = tk.getImage("D://images//ironman_laser.png");
				}
				break;				
			} else {
				
			}
		}
		if(dual == true) {
			switch(e.getKeyCode()) {
				case KeyEvent.VK_UP:
					player_2_up = false;
					if(player_2_char.equals("ironman")) {
						if(player_2_right_button == false && right_beam == false) {
							player_2_char_img = tk.getImage("D://images//"+player_2_char+"_up.png");
						}				
					} else {
						player_2_char_img = tk.getImage("D://images//"+player_2_char+"_up.png");
					}
					break;
				case KeyEvent.VK_LEFT:
					player_2_left = false;
					break;
				case KeyEvent.VK_DOWN:
					player_2_down = false;
					if(player_2_char.equals("ironman")) {
						if(player_2_right_button == false && right_beam == false) {
							player_2_char_img = tk.getImage("D://images//"+player_2_char+"_down.png");
						}				
					} else {
						player_2_char_img = tk.getImage("D://images//"+player_2_char+"_down.png");
					}
					break;
				case KeyEvent.VK_RIGHT:
					player_2_right = false;
					break;
				case KeyEvent.VK_NUMPAD5:
					if(player_2_right_button == false && right_beam == false && player_2_char.equals("ironman")) {
						player_2_left_button = false;
					} else if(player_2_char.equals("warmachine")) {
						player_2_left_button = false;
					}
					break;
				case KeyEvent.VK_NUMPAD6:
					if(player_2_left_button == false && right_beam == false && player_2_char.equals("ironman")) {
						ce = new Charge_Effect(player_2_x, player_2_y);
						player_2_right_button = false;
						player_2_char_img = tk.getImage("D://images//laser.gif");			
					}
					break;
			}
		}
	}
	@Override
	public void keyTyped(KeyEvent arg0) {

		
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
class Effect_Class {
	Point pos;
	double effect_num = 0;

	public void move() {
		pos.x -= 1;
	}
	public void next_img() {
		effect_num += 1;
	}
}
class Bullet extends Effect_Class {
	
	static int special = 0;
	int bullet_type = 0;
	
	Bullet(int x, int y) {
		pos = new Point(x, y);
	}
	Bullet(int x, int y, int t) {
		pos = new Point(x, y);
		bullet_type = t;
	}
	public void move() {
		pos.x += 30;
	}
	public void move2() {
		pos.x += 30;
		pos.y -= 5;
	}
	public void move3() {
		pos.x += 30;
		pos.y += 5;
	}
	public void move4() {
		pos.x += 30;
		pos.y -= 15;
	}
	public void move5() {
		pos.x += 30;
		pos.y += 15;
	}
}
class Kill extends Effect_Class {
	double max_hp = 0;
	double hp = 0;
	double width = 124.0;
	boolean cool;
	boolean shoot = true;
	int cooltime = test02.timer;
	int kill_num;
	
	Kill(int x, int y) {
		pos = new Point(x, y);
	}
	public void move() {
		pos.x -= 2;
		if(test02.timer - cooltime == 1) {
			cool = true;
		}
	}
	public void attack() {
		hp -= test02.player_1_damage;
		width = new test02().hp_bar.getWidth(null)*(hp/max_hp);
	}
}
class Kill_Bullet extends Effect_Class {

	boolean shoot = true;
	double shoot_x = 0, shoot_y = 0;
	double move_x=0, move_y=0;
	int damage = 1;
	
	Kill_Bullet(int x, int y) {
		pos = new Point(x, y);
		move_x = ((test02.player_1_x+test02.player_1_char_img.getWidth(null)/2)-pos.x)/4;
		move_y = ((test02.player_1_y+test02.player_1_char_img.getHeight(null)/2)-pos.y)/move_x;
		if(move_y > 4) {
			move_y = 4;
		}
		if(move_y < -4) {
			move_y = -4;
		}
		shoot_x = pos.x;
		shoot_y = pos.y;
	}
	public void move() {
		shoot_x -= 4;
		shoot_y += -move_y;
		pos.setLocation(shoot_x, shoot_y);
		pos.x = (int)shoot_x;
		pos.y = (int)shoot_y;
	}
}
class Explosion extends Effect_Class {
	Explosion(int x, int y) {
		pos = new Point(x, y);
	}
	public void next_img() {
		effect_num+=0.5;
	}
}
class Bullet_Explosion extends Effect_Class {
	Bullet_Explosion(int x, int y) {
		pos = new Point(x, y);
	}
}
class Coin_Effect extends Effect_Class {
	int coin_y = 0;
	
	Coin_Effect(int x, int y) {
		pos = new Point(x, y);
	}
	public void move() {
		if(coin_y < 30) {
			pos.y -= 2;	
			pos.x -= 7;
		} else {
			pos.y += 2;
			pos.x -= 3;
		}
		coin_y++;
	}
}
class Hit_Explosion extends Effect_Class {
	
	Hit_Explosion(int x, int y) {
		pos = new Point(x, y);
	}
	public void next_img() {
		effect_num+=0.1;
	}
}
class Item_Effect extends Effect_Class {
	
	int item_num;
	String item_name;
	double move_y = 5;
	boolean move;
	Image item;
	
	Item_Effect(int x, int y) {
		pos = new Point(x, y);
		item_num = (int)(Math.random()*8);
		if(item_num == 0 || item_num == 1 || item_num == 6) {
			item_name = "heart";
		} else if(item_num == 2 || item_num == 3 || item_num == 7) {
			item_name = "energy";
		} else if(item_num == 4) {
			item_name = "attackspeed";
		} else if(item_num == 5) {
			item_name = "pierce";
		}
		item = test02.tk.getImage("D://images//item//"+item_name+".gif");
	}
	public void move() {
		pos.x -= 2;
		if(move == false) {
			pos.y--;
			move_y-=0.2;
		} else if(move == true) {
			pos.y++;
			move_y+=0.2;
		}
		if(move_y <= 0.0) {
			move = true;
		} else if(move_y >= 10.0) {
			move = false;
		}
	}
}
class Item_Get_Effect extends Effect_Class {
	
	Item_Get_Effect(int x, int y) {
		pos = new Point(x, y);
	}
	public void next_img() {
		effect_num+=0.5;
	}
}
class Charge_Effect extends Effect_Class {
	
	Charge_Effect(int x, int y) {
		pos = new Point(x, y);
	}
	public void move() {
		pos.x = test02.player_1_x;
		pos.y = test02.player_1_y;
	}
	public void next_img() {
		if(test02.player_1_right_button == true && test02.right_beam==false) {
			if(test02.right_button_count < 50) {
				effect_num+=0.1;				
			}
			if(test02.right_button_count >= 50) {
				effect_num+=0.5;
			}
		}
	}
}
class GunShot_Effect extends Effect_Class {
	
	GunShot_Effect(int x, int y) {
		pos = new Point(x, y);
	}
	public void move() {
		if(test02.player_1_char.equals("warmachine")) {
			pos.x = test02.player_1_x+test02.player_1_char_img.getWidth(null);
			pos.y = test02.player_1_y;			
		} else {
			pos.x = test02.player_2_x+test02.player_2_char_img.getWidth(null);
			pos.y = test02.player_2_y+20;
		}
	}
	public void next_img() {
		effect_num++;
		if(effect_num >= 7.0) {
			effect_num = 0.0;
		}
	}
}
class Ironman_sub extends Effect_Class {
	
	Ironman_sub(int x, int y) {
		pos = new Point(x, y);
	}
	public void move(int y) {
		if(pos.x > test02.player_1_x-test02.ironman_sub.getWidth(null)) {
			pos.x -= 2;
		} else if(pos.x < test02.player_1_x-test02.ironman_sub.getWidth(null)) {
			pos.x += 2;
		}
		if(y == 0) {
			if(pos.y > test02.player_1_y) {
				pos.y -= 2;
			} else if(pos.y < test02.player_1_y) {
				pos.y += 2;
			}
		} else if(y == 1) {
			if(pos.y > test02.player_1_y+test02.player_1_char_img.getHeight(null)/4) {
				pos.y -= 2;
			} else if(pos.y < test02.player_1_y+test02.player_1_char_img.getHeight(null)/4) {
				pos.y += 2;
			}
		} else if(y == 2) {
			if(pos.y > test02.player_1_y+test02.player_1_char_img.getHeight(null)/2) {
				pos.y -= 2;
			} else if(pos.y < test02.player_1_y+test02.player_1_char_img.getHeight(null)/2) {
				pos.y += 2;
			}
		} else if(y == 3) {
			if(pos.y > test02.player_1_y+test02.player_1_char_img.getHeight(null)/4+test02.player_1_char_img.getHeight(null)/2) {
				pos.y -= 2;
			} else if(pos.y < test02.player_1_y+test02.player_1_char_img.getHeight(null)/4+test02.player_1_char_img.getHeight(null)/2) {
				pos.y += 2;
			}
		}
	}
}
class Sub_Thread extends Thread {
	
	test02 t = new test02();
	
	public void sub_start() {
		start();
	}
	@Override
	public void run() {
		while(true) {
			BulletProcess();
			KillProcess();
			LaserProcess();
			Beam_Check();
			Char_Check();
			Coin_Check();
			Bullet_Check();
			Bullet2_Check();
			Hit_Bullet_Check();
			Item_Check();
			try {
				Thread.sleep(16);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void BulletProcess() {
		t.bullet_count_1++;
		t.bullet_count_2++;
		t.bullet_count_3++;
		if(t.player_1_left_button == true && t.bullet_count_1>=t.player_1_as) {
			t.bullet_count_1 = 0;
			if(t.player_1_char.equals("ironman") && t.ironman_sub_list.size() != 0) {
				for(int k=0; k<t.ironman_sub_list.size(); k++) {
					t.sub = (Ironman_sub) t.ironman_sub_list.get(k);
					if(t.player_1_pierce > k) {
						t.b = new Bullet(t.sub.pos.x+t.ironman_sub.getWidth(null)+20, t.sub.pos.y-23);
						t.p_1_bullet_list.add(t.b);
					}
				}				
			} else if(t.player_1_char.equals("warmachine")) {
				t.b = new Bullet(t.player_1_x+t.player_1_char_img.getWidth(null), t.player_1_y);
				t.p_1_bullet_list.add(t.b);
				if(t.b.special > 0) {
					t.b = new Bullet(t.player_1_x+t.player_1_char_img.getWidth(null), t.player_1_y-10, 1);
					t.p_1_bullet_list.add(t.b);
				}
				if(t.b.special > 1) {
					t.b = new Bullet(t.player_1_x+t.player_1_char_img.getWidth(null), t.player_1_y-10, 3);
					t.p_1_bullet_list.add(t.b);
				}
				if(t.b.special > 2) {
					t.b = new Bullet(t.player_1_x+t.player_1_char_img.getWidth(null), t.player_1_y-10, 0);
					t.p_1_bullet_list.add(t.b);
				}
				if(t.b.special > 3) {
					t.b = new Bullet(t.player_1_x+t.player_1_char_img.getWidth(null), t.player_1_y-10, 4);
					t.p_1_bullet_list.add(t.b);
				}
			}
		}
		if(t.dual == true) {
			if(t.player_2_left_button == true && t.bullet_count_2>=t.player_2_as) {
				t.bullet_count_2 = 0;
				if(t.player_2_char.equals("ironman") && t.ironman_sub_list.size() != 0) {
					for(int k=0; k<t.ironman_sub_list.size(); k++) {
						t.sub = (Ironman_sub) t.ironman_sub_list.get(k);
						if(t.player_2_pierce > k) {
							t.b = new Bullet(t.sub.pos.x+t.ironman_sub.getWidth(null)+20, t.sub.pos.y-23);
							t.p_2_bullet_list.add(t.b);
						}
					}				
				} else if(t.player_2_char.equals("warmachine")) {
					t.b = new Bullet(t.player_2_x+t.player_2_char_img.getWidth(null), t.player_2_y-10, 2);
					t.p_2_bullet_list.add(t.b);
					if(t.b.special > 0) {
						t.b = new Bullet(t.player_2_x+t.player_2_char_img.getWidth(null), t.player_2_y-10, 1);
						t.p_2_bullet_list.add(t.b);
					}
					if(t.b.special > 1) {
						t.b = new Bullet(t.player_2_x+t.player_2_char_img.getWidth(null), t.player_2_y-10, 3);
						t.p_2_bullet_list.add(t.b);
					}
					if(t.b.special > 2) {
						t.b = new Bullet(t.player_2_x+t.player_2_char_img.getWidth(null), t.player_2_y-10, 0);
						t.p_2_bullet_list.add(t.b);
					}
					if(t.b.special > 3) {
						t.b = new Bullet(t.player_2_x+t.player_2_char_img.getWidth(null), t.player_2_y-10, 4);
						t.p_2_bullet_list.add(t.b);
					}
				}
			}
			if(t.player_2_right_button == true && t.bullet_count_3>=t.player_2_as) {
				t.b = new Bullet(t.player_2_x+t.player_2_char_img.getWidth(null), t.player_2_y-20, 2);
				t.p_2_bullet_list.add(t.b);
				t.bullet_count_3 = 0;
				if(t.b.special > 0) {
					t.b = new Bullet(t.player_2_x+t.player_2_char_img.getWidth(null), t.player_2_y-20, 1);
					t.p_2_bullet_list.add(t.b);
				}
				if(t.b.special > 1) {
					t.b = new Bullet(t.player_2_x+t.player_2_char_img.getWidth(null), t.player_2_y-20, 3);
					t.p_2_bullet_list.add(t.b);
				}
				if(t.b.special > 2) {
					t.b = new Bullet(t.player_2_x+t.player_2_char_img.getWidth(null), t.player_2_y-20, 0);
					t.p_2_bullet_list.add(t.b);
				}
				if(t.b.special > 3) {
					t.b = new Bullet(t.player_2_x+t.player_2_char_img.getWidth(null), t.player_2_y-10, 4);
					t.p_2_bullet_list.add(t.b);
				}
			}
		}
	}
	public void KillProcess() {
		//t.k = new Kill(t.f_width, (int)(Math.random()*((t.f_height-t.kill.getHeight(null)-100)))+30);
		if(t.timer_count == 16 && t.timer > 0) {
			if(t.timer==1) {
				KillProduce(t.f_width, 100, 3, 0);
				KillProduce(t.f_width+450, 100, 1, 1);
			}
			if(t.timer%5 == 0) {
				KillProduce(t.f_width, 100, 3 , 0);
				KillProduce(t.f_width+450, 100, 1, 1);
			} else if(t.timer%11 == 0) {
				KillProduce(t.f_width, 500, 3, 0);
				KillProduce(t.f_width+450, 100, 1, 1);
			}
		}
	}
	public void KillProduce(int x, int y, int num, int kill_num) {
		for(int p=0; p<num; p++) {
			t.k = new Kill(x+(p*150), y);
			t.kill_list.add(t.k);
			t.k.kill_num = kill_num;
			if(t.k.kill_num == 0) {
				t.k.max_hp = 5;
				t.k.hp = 5;
			} else if(t.k.kill_num == 1) {
				t.k.max_hp = 10;
				t.k.hp = 10;
			}
		}
	}
	public void LaserProcess() {
		if(t.player_1_right_button == true && t.right_beam==false) {
			t.right_button_count++;
			if(t.right_button_count >= 50) {
				t.player_1_char_img = t.tk.getImage("D://images//laser1.gif");
			}
		} else {
			t.right_button_count = 0;
		}
	}
	public void Hit_Bullet_Check() { //캐릭터와 적 미사일 충돌체크 메소드
		if(t.kill_bullet_list.size() != 0) {
			for(int i=0; i<t.kill_bullet_list.size(); i++) {
				if(i!=t.kill_bullet_list.size()) {
					t.kb = (Kill_Bullet) t.kill_bullet_list.get(i);
				}
				if(Crash(t.player_1_x+30, t.player_1_y+17, t.kb.pos.x, t.kb.pos.y, t.player_1_char_img, t.kill_bullet, -60, -90)) {
					if(t.kill_bullet_list.size() > 0) {
						t.kill_bullet_list.remove(i);
					}
					t.he = new Hit_Explosion(t.kb.pos.x-16, t.kb.pos.y-18);
					t.hit_list.add(t.he);
					t.player_1_hp -= t.kb.damage;
				}
				if(t.dual == true) {
					if(Crash(t.player_2_x+30, t.player_2_y+17, t.kb.pos.x, t.kb.pos.y, t.player_2_char_img, t.kill_bullet, -60, -90)) {
						if(t.kill_bullet_list.size() > 0) {
							t.kill_bullet_list.remove(i);
						}
						t.he = new Hit_Explosion(t.kb.pos.x-16, t.kb.pos.y-18);
						t.hit_list.add(t.he);
						t.player_2_hp -= t.kb.damage;
					}					
				}
			}
		}
	}
	public void Bullet_Check() { //플레이어 1 미사일과 적 이미지 충돌체크 메소드
		if(t.p_1_bullet_list.size() != 0) {
			for(int i=0; i<t.p_1_bullet_list.size(); i++) {
				for(int v=0; v<t.kill_list.size(); v++) {
					if(i!=t.p_1_bullet_list.size()) {
						t.b = (Bullet) t.p_1_bullet_list.get(i);
					}
					if(v!=t.kill_list.size()) {
						t.k = (Kill) t.kill_list.get(v);
					}
					if(Crash(t.b.pos.x, t.b.pos.y, t.k.pos.x, t.k.pos.y, t.bullet, t.kill)) {
						t.k.attack();
						if(t.p_1_bullet_list.size() > 0 && t.p_1_bullet_list.size()!=i) {
							t.p_1_bullet_list.remove(i);
						}
						t.be = new Bullet_Explosion(t.b.pos.x, t.b.pos.y);
						t.bullet_explo_list.add(t.be);
						if(t.k.hp == 0) {
							t.e = new Explosion(t.k.pos.x, t.k.pos.y-50);
							t.c = new Coin_Effect(t.k.pos.x, t.k.pos.y);
							t.player_1_score+=10;
							t.explo_list.add(t.e);
							t.coin_list.add(t.c);
							t.kill_list.remove(v);
							if(t.k.kill_num == 1) {
								item_drop();
							}
						}
					}
				}
			}
		}
	}
	public void Bullet2_Check() { //플레이어 2 미사일과 적 이미지 충돌체크 메소드
		if(t.p_2_bullet_list.size() != 0) {
			for(int i=0; i<t.p_2_bullet_list.size(); i++) {
				for(int v=0; v<t.kill_list.size(); v++) {
					if(i!=t.p_2_bullet_list.size()) {
						t.b = (Bullet) t.p_2_bullet_list.get(i);
					}
					if(v!=t.kill_list.size()) {
						t.k = (Kill) t.kill_list.get(v);
					}
					if(Crash(t.b.pos.x, t.b.pos.y, t.k.pos.x, t.k.pos.y, t.bullet, t.kill)) {
						t.k.attack();
						if(t.p_2_bullet_list.size() > 0 && t.p_2_bullet_list.size()!=i) {
							t.p_2_bullet_list.remove(i);
						}
						t.be = new Bullet_Explosion(t.b.pos.x, t.b.pos.y);
						t.bullet_explo_list.add(t.be);
						if(t.k.hp == 0) {
							t.e = new Explosion(t.k.pos.x, t.k.pos.y-50);
							t.c = new Coin_Effect(t.k.pos.x, t.k.pos.y);
							t.player_2_score += 10;
							t.explo_list.add(t.e);
							t.coin_list.add(t.c);
							t.kill_list.remove(v);
							if(t.k.kill_num == 1) {
								item_drop();
							}
						}
					}
				}
			}
		}
	}
	public void item_drop() {
		t.ie = new Item_Effect(t.k.pos.x+27, t.k.pos.y+20);
		t.item_list.add(t.ie);
	}
	public void Beam_Check() {
		if(t.right_beam == true) {
			try {
				for(int v=0; v<t.kill_list.size(); v++) {
					if(v!=t.kill_list.size()) {
						t.k = (Kill) t.kill_list.get(v);
					}
					exit_For:
						for(int y1=(this.t.player_1_y-5+17); y1<(this.t.player_1_y-5+28); y1++) {
							for(int y2=t.k.pos.y; y2<t.k.pos.y+t.kill.getHeight(null); y2++) {
								if(y1 == y2) {
									t.beam_check = true;
									break exit_For;
								}
							}
						}
					if(t.beam_check == true) {
						t.c = new Coin_Effect(t.k.pos.x, t.k.pos.y);
						t.e = new Explosion(t.k.pos.x, t.k.pos.y);
						t.player_1_score+=10;
						t.explo_list.add(t.e);
						t.kill_list.remove(v);
						t.coin_list.add(t.c);
						if(t.k.kill_num == 1) {
							item_drop();
						}
					}
					t.beam_check = false;
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	public void Char_Check() { //캐릭터와 적 충돌체크
		try {
			for(int v=0; v<t.kill_list.size(); v++) {
				if(v!=t.kill_list.size() && t.kill_list.size() != 0) {
					t.k = (Kill) t.kill_list.get(v);
				}
				if(Crash(t.player_1_x+30, t.player_1_y+17, t.k.pos.x, t.k.pos.y, t.player_1_char_img, t.kill, -60, -90)) {
					t.e = new Explosion(t.k.pos.x, t.k.pos.y);
					t.explo_list.add(t.e);
					if(t.kill_list.size() != 0) {
						t.kill_list.remove(v);						
					}
				}
				if(Crash(t.player_2_x+30, t.player_2_y+17, t.k.pos.x, t.k.pos.y, t.player_2_char_img, t.kill, -60, -90)) {
					t.e = new Explosion(t.k.pos.x, t.k.pos.y);
					t.explo_list.add(t.e);
					if(t.kill_list.size() != 0) {
						t.kill_list.remove(v);						
					}
				}
				t.beam_check = false;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void Coin_Check() { //캐릭터와 코인 충돌체크
		try {
			for(int v=0; v<t.coin_list.size(); v++) {
				if(v!=t.coin_list.size()) {
					t.c = (Coin_Effect) t.coin_list.get(v);
				}
				if(Crash(t.player_1_x, t.player_1_y, t.c.pos.x, t.c.pos.y, t.player_1_char_img, t.coin)) {
					t.coin_list.remove(v);
					t.player_1_score+=5;
				}
				if(Crash(t.player_2_x, t.player_2_y, t.c.pos.x, t.c.pos.y, t.player_2_char_img, t.coin)) {
					t.coin_list.remove(v);
					t.player_2_score+=5;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void Item_Check() { //캐릭터와 아이템 충돌체크
		try {
			for(int v=0; v<t.item_list.size(); v++) {
				if(v!=t.item_list.size()) {
					t.ie = (Item_Effect) t.item_list.get(v);
				}
				if(Crash(t.player_1_x, t.player_1_y, t.ie.pos.x, t.ie.pos.y, t.player_1_char_img, t.ie.item)) {
					t.item_list.remove(v);
					t.ige = new Item_Get_Effect(t.ie.pos.x-t.item_get.getWidth(null)/2+5, t.ie.pos.y-t.item_get.getHeight(null)/2+5);
					t.item_get_list.add(t.ige);
					if(t.player_1_char.equals("ironman") && t.player_1_pierce < 4 && t.ie.item_name.equals("pierce")) {
						if(t.player_1_pierce == 0) {
							t.sub = new Ironman_sub(t.player_1_x-t.ironman_sub.getWidth(null), t.player_1_y);							
						} else if(t.player_1_pierce == 1) {
							t.sub = new Ironman_sub(t.player_1_x-t.ironman_sub.getWidth(null), t.player_1_y+t.player_1_char_img.getHeight(null)/4);
						} else if(t.player_1_pierce == 2) {
							t.sub = new Ironman_sub(t.player_1_x-t.ironman_sub.getWidth(null), t.player_1_y+t.player_1_char_img.getHeight(null)/2);
						} else if(t.player_1_pierce == 3) {
							t.sub = new Ironman_sub(t.player_1_x-t.ironman_sub.getWidth(null), t.player_1_y+t.player_1_char_img.getHeight(null)/4+t.player_1_char_img.getHeight(null)/2);
						}
						t.ironman_sub_list.add(t.sub);
						t.player_1_pierce++;
					}
					if(t.player_1_char.equals("warmachine") && t.player_1_pierce < 4 && t.ie.item_name.equals("pierce")) {
						t.player_1_pierce++;
						t.b.special++;
					}
					if(t.ie.item_name.equals("attackspeed")) {
						t.player_1_as--;
					}
				}
				if(Crash(t.player_2_x, t.player_2_y, t.ie.pos.x, t.ie.pos.y, t.player_2_char_img, t.ie.item)) {
					t.item_list.remove(v);
					t.ige = new Item_Get_Effect(t.ie.pos.x-t.item_get.getWidth(null)/2+5, t.ie.pos.y-t.item_get.getHeight(null)/2+5);
					t.item_get_list.add(t.ige);
					if(t.player_2_char.equals("ironman") && t.player_2_pierce < 4 && t.ie.item_name.equals("pierce")) { 
						if(t.player_2_pierce == 0) {
							t.sub = new Ironman_sub(t.player_2_x-t.ironman_sub.getWidth(null), t.player_2_y);							
						} else if(t.player_2_pierce == 1) {
							t.sub = new Ironman_sub(t.player_2_x-t.ironman_sub.getWidth(null), t.player_2_y+t.player_1_char_img.getHeight(null)/4);
						} else if(t.player_2_pierce == 2) {
							t.sub = new Ironman_sub(t.player_2_x-t.ironman_sub.getWidth(null), t.player_2_y+t.player_1_char_img.getHeight(null)/2);
						} else if(t.player_2_pierce == 3) {
							t.sub = new Ironman_sub(t.player_2_x-t.ironman_sub.getWidth(null), t.player_2_y+t.player_1_char_img.getHeight(null)/4+t.player_1_char_img.getHeight(null)/2);
						}
						t.ironman_sub_list.add(t.sub);
						t.player_2_pierce++;
					}
					if(t.player_2_char.equals("warmachine") && t.player_2_pierce < 4 && t.ie.item_name.equals("pierce")) {
						t.player_2_pierce++;
						t.b.special++;
					}
					if(t.ie.item_name.equals("attackspeed")) {
						t.player_2_as--;
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	//충돌검사 메소드1 - 일반
	public boolean Crash(int x1, int y1, int x2, int y2, Image img1, Image img2) {
		
		boolean check = false;
		
		try {
			if((x1<x2+img2.getWidth(null)) &&
					(x1+img1.getWidth(null)>x2) &&
					(y1<y2+img2.getHeight(null)) &&
					(y1+img1.getHeight(null)>y2)) {
				check = true;
			}
			return check;			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return check;
	}
	//충돌검사 메소드2 - 캐릭터 히트박스 충돌용
	//캐릭터 히트박스(+30,+17,-60,-90)
	public boolean Crash(int x1, int y1, int x2, int y2, Image img1, Image img2, int minus_x, int minus_y) {
		
		boolean check = false;
		
		try {
			if((x1<x2+img2.getWidth(null)) &&
					(x1+img1.getWidth(null)+minus_x>x2) &&
					(y1<y2+img2.getHeight(null)) &&
					(y1+img1.getHeight(null)+minus_y>y2)) {
				check = true;
				return check;
			}			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return check;
	}
}