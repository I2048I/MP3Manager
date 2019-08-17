package cn.snowing.tool.mp3manager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import java.awt.event.MouseAdapter;

public class MP3Manager {
	final static int VersionCore = 190817;
	final static String Version = "A1.0_"+VersionCore;
	static JFrame frame = new JFrame();
	static JPanel panel = new JPanel();
	static private Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static JPanel MP3StatusPanel = new JPanel();
	static JPanel BarPanel = new JPanel();
	static JPanel CenterPanel = new JPanel();
	static JLabel MP3Status = new JLabel("Disconnect");
	static int TotalMusic = 0;
	static String MusicPath = "null";
	static String NowMusic = "null";
	static String MP3Path = "null";
	static int line = 0;
	static int oldline = 0;
	static boolean isOnlyCopy = false;
	static Scanner scan = new Scanner(System.in);
	getMP3Deamon getMP3 = new getMP3Deamon();
	static boolean inMusicManager = false;
	
	static String gmfPath = MusicPath+"\\#MusicName.gmf";
	static String batPath = MusicPath+"\\#MusicName.bat";
	public static void main(String[] ages) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		new MP3Manager();
	}
	public void boot() {
		frame.getContentPane().add(panel);
		panel.setLayout(new BorderLayout());
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(MP3Manager.class.getResource("/png/icon_24x24.png")));
		//ui();
		
		frame.setTitle("MP3管理工具["+Version+"]");
		frame.setBounds(ScreenSize.width / 2 - 480 / 2, ScreenSize.height / 2 - 320 / 2, 480, 320);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void ui() {
		panel.removeAll();
		panel.updateUI();
		BarPanel.removeAll();
		BarPanel.updateUI();
		MP3StatusPanel.removeAll();
		MP3StatusPanel.updateUI();
		
		BarPanel.setBackground(Color.LIGHT_GRAY);
		FlowLayout flowLayout = (FlowLayout) BarPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		panel.add(BarPanel, BorderLayout.SOUTH);

		JButton SettingButton = new JButton("Set");
		BarPanel.add(SettingButton);

		JButton AboutButton = new JButton("Abo");
		AboutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JDialog dialog = new JDialog(frame,"About",true);
				dialog.setResizable(false);
				dialog.setBounds(ScreenSize.width / 2 - 320 / 2, ScreenSize.height / 2 - 180 / 2, 320, 180);
				JPanel About_GuiPanel = new JPanel();

				dialog.add(About_GuiPanel);
				About_GuiPanel.setLayout(new BorderLayout(0, 0));
				
				JPanel panel = new JPanel();
				About_GuiPanel.add(panel, BorderLayout.SOUTH);
				panel.setAlignmentX(FlowLayout.RIGHT);
				JButton sure = new JButton("\u786E\u5B9A");
				sure.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) 
					{
						dialog.dispose();
					}
				});
				panel.add(sure);	
				JPanel panel_1 = new JPanel();
				About_GuiPanel.add(panel_1, BorderLayout.CENTER);
				JLabel text_1 = new JLabel("MP3Manager "+Version);
				panel_1.add(text_1);
				JLabel text_2 = new JLabel("The Software Follow GPL-v3 OpenSources.");
				panel_1.add(text_2);
				JLabel text_3 = new JLabel("<html>GitWeb:<u>https://gitee.com/I2048I/MP3Manager/releases/</u></html>");
				panel_1.add(text_3);
				text_3.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent arg0) {
						text_3.setText("<html>GitWeb:<u><Font color=blue>https://gitee.com/I2048I/MP3Manager/releases/</font></u></html>");
					}
					@Override
					public void mouseExited(MouseEvent arg0) {
						text_3.setForeground(Color.BLACK);
						text_3.setText("<html>GitWeb:<u><Font color=black>https://gitee.com/I2048I/MP3Manager/releases/</font></u></html>");
					}
					@Override
					public void mouseClicked(MouseEvent arg0) {
						if(Desktop.isDesktopSupported()) {
							Desktop desktop = Desktop.getDesktop();
							URI url;
							try {
								url = new URI("https://gitee.com/I2048I/MP3Manager/releases/");
								desktop.browse(url);
							} catch (URISyntaxException | IOException e) {
								// TODO 自动生成的 catch 块
								e.printStackTrace();
							}
						}
					}
				});
				dialog.setVisible(true);
			}
		});
		BarPanel.add(AboutButton);

		panel.add(CenterPanel, BorderLayout.CENTER);

		JButton MusicManagerButton = new JButton("Music");
		MusicManagerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MusicManager.ui();
			}
		});
		MusicManagerButton.setBounds(30, 30, 120, 160);

		JButton EBookManagerButton = new JButton("EBook");
		EBookManagerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, "The model haven't craft...");
			}
		});
		EBookManagerButton.setBounds(170, 30, 120, 160);

		JButton PictureManagerButton = new JButton("Picture");
		PictureManagerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, "The model haven't craft...");
			}
		});
		PictureManagerButton.setBounds(310, 30, 120, 160);
		CenterPanel.setLayout(null);
		CenterPanel.add(MusicManagerButton);
		CenterPanel.add(EBookManagerButton);
		CenterPanel.add(PictureManagerButton);

		MP3StatusPanel.setBackground(Color.LIGHT_GRAY);
		FlowLayout fl_MP3StatusPanel = (FlowLayout) MP3StatusPanel.getLayout();
		fl_MP3StatusPanel.setAlignment(FlowLayout.LEFT);
		panel.add(MP3StatusPanel, BorderLayout.NORTH);

		JLabel MP3StatusText = new JLabel("MP3\u8FDE\u63A5\u72B6\u6001:");
		
		MP3StatusPanel.add(MP3StatusText);

		MP3StatusPanel.add(MP3Status);

		frame.setVisible(true);
	}
	public MP3Manager() {
		boot();
		ui();
		//MusicManager.ui();
		getMP3.start();
	}
}

class getMP3Deamon extends Thread {
	public void run() {
		while(true) {
			String Disk[] = {"D:\\","E:\\","F:\\","G:\\","H:\\","I:\\","J:\\","K:\\","L:\\","M:\\","N:\\","O:\\"};
			for (int i=0;i<Disk.length;i++) {
				File file = new File (Disk[i]+"MUSIC.LIB");
				if(!file.exists()){
					if(i>=Disk.length-1) {
						MP3Manager.MP3Path = "null";
						MP3Manager.MP3Status.setText("Disconnect");
						MusicManager.MP3PathShow.setText(MP3Manager.MP3Path);
					}
				}
				else{
					MP3Manager.MP3Path = Disk[i];
					MP3Manager.MP3Status.setText("Connected ("+MP3Manager.MP3Path+")");
					MusicManager.MP3PathShow.setText(MP3Manager.MP3Path);
					break;
				}
			}
			try {
				refreshNowMusic();
				MusicManager.getMP3MusicFolder();
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}
	public void refreshNowMusic() {
		if(MP3Manager.inMusicManager&&!MusicManager.NowMusicNumber.getText().equals(null)&&!MusicManager.NowMusicNumber.getText().equals("")&&!MusicManager.NowMusicNumber.getText().equals("null")) {
			int nownum = Integer.parseInt(MusicManager.NowMusicNumber.getText());
			if(!MP3Manager.MusicPath.equals("null")&&!(nownum>MP3Manager.TotalMusic)) {
				MP3Manager.line = nownum;
				String Music = Filer.getLineString(MusicManager.gmfPath, MusicManager.line);
				MP3Manager.NowMusic = Music;
				MusicManager.NowMusicShow.setText(MP3Manager.NowMusic);
			}
			else if(nownum>MP3Manager.TotalMusic) {
				if(MP3Manager.TotalMusic<=0) {
					MP3Manager.line = 0;
					MusicManager.NowMusicNumber.setText(""+MP3Manager.line);
					MusicManager.NowMusicShow.setText("null");
					JOptionPane.showMessageDialog(MP3Manager.frame, "\u6b4c\u66f2\u4e0d\u591f\u591a...\u4e0b\u591a\u70b9\u518d\u8f93\u8fd9\u4e48\u5927\u7684\u503c\u5566~");
				}
				else {
					MP3Manager.line = 1;
					MusicManager.NowMusicNumber.setText(""+MP3Manager.line);
					String Music = Filer.getLineString(MusicManager.gmfPath, MusicManager.line);
					MP3Manager.NowMusic = Music;
					MusicManager.NowMusicShow.setText(MP3Manager.NowMusic);
					JOptionPane.showMessageDialog(MP3Manager.frame, "\u6b4c\u66f2\u4e0d\u591f\u591a...\u4e0b\u591a\u70b9\u518d\u8f93\u8fd9\u4e48\u5927\u7684\u503c\u5566~");
				}
			}
		}
	}
}
