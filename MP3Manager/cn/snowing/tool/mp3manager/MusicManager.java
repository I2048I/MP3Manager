package cn.snowing.tool.mp3manager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.SwingConstants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JCheckBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MusicManager extends MP3Manager{

	static JPanel RBarPanel = new JPanel();
	static JPanel MusicManagerPanel = new JPanel();
	static JScrollPane scrollPane = new JScrollPane();
	static JPanel MusicManagerBarPanel = new JPanel();
	static JPanel MusicBarinBarPanel = new JPanel();
	static JPanel MusicBackPanel = new JPanel();
	static JTextField MusicPathShow;
	static JTextField MP3PathShow = new JTextField();
	static JTextField NowMusicShow = null;
	static JTextField NowMusicNumber;
	static JList<String> list = new JList<String>();
	static JLabel MusicSeclected = new JLabel("/ 0");

	/**
	 * @wbp.parser.entryPoint
	 */
	public static void ui() {
		panel.removeAll();
		panel.updateUI();

		RBarPanel.removeAll();
		RBarPanel.updateUI();
		MusicManagerPanel.removeAll();
		MusicManagerPanel.updateUI();

		MusicManagerBarPanel.removeAll();
		MusicManagerBarPanel.updateUI();
		MusicBarinBarPanel.removeAll();
		MusicBarinBarPanel.updateUI();
		MusicBackPanel.removeAll();
		MusicBackPanel.updateUI();
		MusicSeclected = new JLabel("/ 0");
		TotalMusic = 0;
		inMusicManager = true;

		RBarPanel.setBackground(Color.LIGHT_GRAY);
		panel.add(RBarPanel, BorderLayout.EAST);
		RBarPanel.setLayout(new GridLayout(0, 1, 0, 10));

		JButton GetListButton = new JButton("\u83B7\u53D6\u6B4C\u5355");
		RBarPanel.add(GetListButton);
		GetListButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				line = 0;
				NowMusic = "null";
				TotalMusic = 0;

				if(!MusicPath.equals("null")) {
					Process process;
					try {
						WriteGetMusicListBAT();
						String url = batPath;
						process = Runtime.getRuntime().exec(url);
						process.waitFor();
						Filer.delFile(url);
						if(!Filer.isEmpty(gmfPath))
						{
							line++;
							String Music = Filer.getLineString(gmfPath, line);
							NowMusic = Music;
							NowMusicShow.setText(NowMusic);
							TotalMusic = Filer.getLine(gmfPath);
							MusicSeclected.setText("/ "+TotalMusic);
							NowMusicNumber.setText(""+line);
						}
						else
						{
							JOptionPane.showMessageDialog(frame,"\u6587\u4ef6\u5939\u5185\u6ca1\u6709\u97f3\u4e50");
							Filer.delFile(gmfPath);
						}

					} catch (IOException | InterruptedException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					} 
					LoadProfile();
				}
				else {
					JOptionPane.showMessageDialog(frame,"\u672a\u9009\u62e9\u6b4c\u66f2\u76ee\u5f55");
				}
			}
		});
		JButton RewriteButton = new JButton("\u91cd\u5199bat\u6587\u4ef6");
		RBarPanel.add(RewriteButton);
		RewriteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String file = MusicPath+"\\#MusicCopy.bat";
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd-HHmmss");
				String time = ""+df.format(new Date());
				String ofile = MusicPath+"\\#MusicCopy_"+time+".bak";
				if(!Filer.isExists(file)) {
					JOptionPane.showMessageDialog(frame,"\u6587\u4ef6\u4e0d\u5b58\u5728");
				}
				else{
					Object[] options = {"\u76f4\u63a5\u8986\u76d6","\u5907\u4efd\u5e76\u8986\u76d6","\u53d6\u6d88"};
					int Choose = JOptionPane.showOptionDialog(frame,"\u5df2\u68c0\u6d4b\u5230\u5b58\u5728\u7684bat\u6587\u4ef6","Warning!", JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.WARNING_MESSAGE, null,options,options[2]);
					if(Choose==0) {
						Filer.delFile(file);
						Filer.createNewFile(file);
						JOptionPane.showMessageDialog(frame,"\u6210\u529f\u66ff\u6362\u6587\u4ef6");
					}
					if(Choose==1) {
						Filer.Copy(file, ofile, true);
						Filer.createNewFile(file);
						JOptionPane.showMessageDialog(frame,"\u5df2\u6210\u529f\u66ff\u6362\u5e76\u5907\u4efd\u6587\u4ef6("+ofile+")");
					}
				}
			}
		});
		JButton ActionButton = new JButton("\u6267\u884C\u547D\u4EE4");
		RBarPanel.add(ActionButton);
		ActionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Filer.isExists(MusicPath+"\\#MusicCopy.bat")) {
					Process process;
					try {
						process = Runtime.getRuntime().exec("cmd /c start "+MusicPath+"\\#MusicCopy.bat");
						process.waitFor();
					} catch (InterruptedException | IOException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
				}
				else {
					JOptionPane.showMessageDialog(frame,"\u6587\u4ef6\u4e0d\u5b58\u5728");
				}
			}
		});

		panel.add(MusicManagerPanel, BorderLayout.CENTER);
		MusicManagerPanel.setLayout(null);

		JLabel MusicPathText = new JLabel("\u6B4C\u5355\u76EE\u5F55:");
		MusicPathText.setBounds(10, 10, 55, 20);
		MusicManagerPanel.add(MusicPathText);

		MusicPathShow = new JTextField();
		MusicPathShow.setEditable(false);
		MusicPathShow.setText(MusicPath);
		MusicPathShow.setBounds(65, 8, 225, 24);
		MusicManagerPanel.add(MusicPathShow);
		MusicPathShow.setColumns(10);

		JButton MusicPathButton = new JButton("...");
		MusicPathButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fcDlg = new JFileChooser();
				fcDlg.setDialogTitle("请选择音乐文件夹...");
				fcDlg.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fcDlg.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					String filepath = fcDlg.getSelectedFile().getPath();
					MusicPath = filepath;
					MusicPathShow.setText(MusicPath);
					gmfPath = MusicPath+"\\#MusicName.gmf";
					batPath = MusicPath+"\\#MusicName.bat";
					WriteGetMusicListBAT();
				}
			}
		});
		MusicPathButton.setBounds(295, 10, 30, 20);
		MusicManagerPanel.add(MusicPathButton);

		JLabel MP3PathText = new JLabel("\u8BBE\u5907\u5730\u5740:");
		MP3PathText.setBounds(10, 40, 55, 20);
		MusicManagerPanel.add(MP3PathText);

		MP3PathShow.setText(MP3Path);
		MP3PathShow.setEditable(false);
		MP3PathShow.setColumns(10);
		MP3PathShow.setBounds(65, 38, 225, 24);
		MusicManagerPanel.add(MP3PathShow);

		JLabel NowMusicText = new JLabel("\u5F53\u524D\u6B4C\u66F2:");
		NowMusicText.setHorizontalAlignment(SwingConstants.CENTER);
		NowMusicText.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				NowMusicText.setForeground(Color.DARK_GRAY);
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
				NowMusicText.setText("\u8bd5\u542c\u266b");
				NowMusicText.setForeground(Color.GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				NowMusicText.setText("\u5F53\u524D\u6B4C\u66F2:");
				NowMusicText.setForeground(Color.BLACK);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				String Player = "C:\\Program Files\\Windows Media Player\\wmplayer.exe ";
				if(!Filer.isExists(Player))
				{
					JOptionPane.showMessageDialog(frame, "\u6ca1\u6709\u627e\u5230<Windows Media Player>,\u65e0\u6cd5\u8bd5\u542c\u6b4c\u66f2","\u8b66\u544a", JOptionPane.ERROR_MESSAGE);
				}
				else {
					if(!NowMusic.equals("")&&!NowMusic.equals("null")&&!NowMusic.equals(null))
					{
						String url = Player+"\""+MusicPath+"\\"+NowMusic+"\"";
						System.out.println(url);
						Process process;
						try {
							process = Runtime.getRuntime().exec(url);
							process.wait();
						} catch (IOException | InterruptedException e1) {
							// TODO 自动生成的 catch 块
							e1.printStackTrace();
						}
					}
					else {
						JOptionPane.showMessageDialog(frame, "\u6b4c\u90fd\u6ca1\u6709,\u600e\u4e48\u542c?");
					}
				}
				NowMusicText.setForeground(Color.BLACK);
			}
		});
		NowMusicText.setBounds(10, 70, 55, 20);
		MusicManagerPanel.add(NowMusicText);

		NowMusicShow = new JTextField();
		NowMusicShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}
		});
		NowMusicShow.setText("null");
		NowMusicShow.setEditable(false);
		NowMusicShow.setColumns(10);
		NowMusicShow.setBounds(65, 68, 200, 24);
		MusicManagerPanel.add(NowMusicShow);

		scrollPane.setBounds(10, 105, 125, 130);
		MusicManagerPanel.add(scrollPane);
		scrollPane.setViewportView(list);
		JButton AddKinds = new JButton("\u65B0\u589E\u7C7B\u578B");
		JButton Pass = new JButton("\u8DF3\u8FC7");
		AddKinds.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListModel<String> tempString = list.getModel();
				int length = list.getModel().getSize();
				String AddKeys[] = new String[length+1];
				String AddString = JOptionPane.showInputDialog(frame, "AddKinds Name");
				if(!AddString.equals("")&&!AddString.equals(null)) {
					for(int i=0;i<length;i++) {
						AddKeys[i] = tempString.getElementAt(i);
					}
					if(!MP3Path.equals(null)&&!MP3Path.equals("null")) {
						Filer.createNewFlode(MP3Path+"Music\\"+AddString);
						AddKeys[length] = AddString;
						list.setListData(AddKeys);
					}
					else {
						JOptionPane.showMessageDialog(frame, "MP3\u672a\u63d2\u5165!\u5199\u5165\u5931\u8d25!");
					}
				}
				else {
					JOptionPane.showMessageDialog(frame, "\u4e0d\u6dfb\u52a0\u5c31\u522b\u4e71\u70b9(\uffe3\u25bd\uffe3)");
				}
			}
		});
		AddKinds.setBounds(145, 105, 80, 25);
		MusicManagerPanel.add(AddKinds);
		JButton DelKinds = new JButton("\u5220\u9664\u7C7B\u578B");
		DelKinds.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!list.isSelectionEmpty()) {
					Object[] options = {"\u786e\u5b9a","\u53d6\u6d88"};
					int Choose = JOptionPane.showOptionDialog(frame,"\u771f\u7684\u8981\u5220\u9664\u5417?(\u6b64\u64cd\u4f5c\u4e0d\u53ef\u9006)","Warning!", JOptionPane.YES_NO_OPTION,
							JOptionPane.WARNING_MESSAGE, null,options,options[1]);
					if(Choose==0) {
						int seclected = list.getSelectedIndex();
						list.clearSelection();
						ListModel<String> tempString = list.getModel();
						int length = list.getModel().getSize();
						if(length==1) {
							String[] Empty = new String[0];
							list.setListData(Empty);
						}
						String DelString[] = new String[length-1];
						if(DelString.length!=0) {
							for(int i=0;i<length;i++) {
								if(i!=seclected) {
									if(i<seclected) {
										DelString[i] = tempString.getElementAt(i);
									}
									if(i>seclected) {
										DelString[i-1] = tempString.getElementAt(i);
									}
								}
							}
							list.setListData(DelString);
						}
					}
				}
				else {
					JOptionPane.showMessageDialog(frame, "\u6ca1\u6709\u9009\u7c7b\u522b!!!  \u5220\u4e2a\u9e21\u8116-_-");
				}
			}
		});

		DelKinds.setBounds(145, 140, 80, 25);
		MusicManagerPanel.add(DelKinds);
		JButton SecKinds = new JButton("\u786E\u8BA4\u7C7B\u578B");
		SecKinds.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!MusicPath.equals(null)&&!MusicPath.equals("")) {
					if(!list.getSelectedValue().equals(null)&&!list.getSelectedValue().equals("")) {
						String WritePath = MusicPath+"\\#MusicCopy.bat";
						Filer.createNewFile(WritePath);
						String getMusicName = NowMusic;
						String getKinds = list.getSelectedValue();
						String input = "copy \""+MusicPath+"\\"+getMusicName+"\" \""+MP3Path+"Music\\"+getKinds+"\\"+getMusicName+"\"";
						Filer.Saver(WritePath, input, true);
						line++;
						String Music = Filer.getLineString(MusicManager.gmfPath, MusicManager.line);
						NowMusic = Music;
						NowMusicShow.setText(NowMusic);
						NowMusicNumber.setText(""+line);
						SaveProfile();
					}
					else {
						JOptionPane.showMessageDialog(frame, "\u6ca1\u6709\u9009\u7c7b\u522b!!!  \u5220\u4e2a\u9e21\u8116-_-");
					}
				}
				else {
					JOptionPane.showMessageDialog(frame, "\u5f53\u524d\u65e0\u97f3\u4e50-_-");
				}
			}
		});

		SecKinds.setBounds(145, 175, 80, 25);
		MusicManagerPanel.add(SecKinds);

		Pass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!NowMusic.equals("null")) {
					System.out.println(line);
					line++;
					String Music = Filer.getLineString(MusicManager.gmfPath, MusicManager.line);
					NowMusic = Music;
					NowMusicShow.setText(NowMusic);
					NowMusicNumber.setText(""+line);
					SaveProfile();
				}
				else {
					JOptionPane.showMessageDialog(frame, "\u5f53\u524d\u65e0\u97f3\u4e50");
				}
			}
		});
		Pass.setBounds(145, 210, 80, 25);
		MusicManagerPanel.add(Pass);

		MusicSeclected.setHorizontalAlignment(SwingConstants.LEFT);
		MusicSeclected.setBounds(325, 68, 50, 24);
		MusicManagerPanel.add(MusicSeclected);

		NowMusicNumber = new JTextField();
		NowMusicNumber.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(!NowMusicNumber.getText().equals("")) {
					int length = NowMusicNumber.getText().length();
					if(length<5) {
						String Password = (length == 0 ? "0123456789" : "0123456789");
						if (Password.indexOf(e.getKeyChar())< 0) {
							e.consume();
						}

					}
					else {
						e.consume();
					}
				}
			}
		});
		NowMusicNumber.setHorizontalAlignment(SwingConstants.RIGHT);
		NowMusicNumber.setText("0");
		NowMusicNumber.setColumns(10);
		NowMusicNumber.setBounds(270, 68, 50, 24);
		MusicManagerPanel.add(NowMusicNumber);

		JCheckBox isOnlyCopyCheckbox = new JCheckBox("\u76F4\u63A5\u590D\u5236\u5230MP3");
		isOnlyCopyCheckbox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isOnlyCopyCheckbox.isSelected()) {
					isOnlyCopy = true;
				}
				else {
					isOnlyCopy = false;
				}
			}
		});
		isOnlyCopyCheckbox.setBounds(230, 105, 120, 24);
		MusicManagerPanel.add(isOnlyCopyCheckbox);


		MusicManagerBarPanel.setBackground(Color.LIGHT_GRAY);
		panel.add(MusicManagerBarPanel, BorderLayout.SOUTH);
		MusicManagerBarPanel.setLayout(new BorderLayout(0, 0));

		MusicBarinBarPanel.setBackground(Color.LIGHT_GRAY);
		FlowLayout fl_MusicBarinBarPanel = (FlowLayout) MusicBarinBarPanel.getLayout();
		fl_MusicBarinBarPanel.setAlignment(FlowLayout.RIGHT);
		MusicManagerBarPanel.add(MusicBarinBarPanel, BorderLayout.EAST);

		JButton SettingButton = new JButton("Set");
		MusicBarinBarPanel.add(SettingButton);

		JButton HelpButton = new JButton("Hlp");
		MusicBarinBarPanel.add(HelpButton);


		MusicBackPanel.setBackground(Color.LIGHT_GRAY);
		FlowLayout fl_MusicBackPanel = (FlowLayout) MusicBackPanel.getLayout();
		fl_MusicBackPanel.setAlignment(FlowLayout.LEFT);
		MusicManagerBarPanel.add(MusicBackPanel, BorderLayout.WEST);

		JButton BackButton = new JButton("Bak");
		BackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Filer.delFile(gmfPath);
				inMusicManager = false;
				MP3Manager.ui();
				line = 0;
				NowMusic = "null";
			}
		});
		MusicBackPanel.add(BackButton);
		frame.setVisible(true);
	}
	public static void WriteGetMusicListBAT() {
		String data = "dir \""+MusicPath+"\\*.mp3\" /B>\""+MusicPath+"\\#MusicName.gmf\"";
		File file = new File(batPath);
		try{
			FileWriter fileWritter = new FileWriter(file.getAbsolutePath(),false);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(data+"\r\n");
			bufferWritter.close();
		} catch(IOException e2) {
			e2.printStackTrace();
		}
	}
	public static void getMP3MusicFolder() {
		if(!MP3Path.equals("")&&!MP3Path.equals(null)&&!MP3Manager.MP3Path.equals("null")) {
			File file = new File(MP3Path+"\\Music");
			File[] fileList = file.listFiles();
			List<File> folder = new ArrayList<File>();
			for (int i = 0; i < fileList.length; i++) {
				if(fileList[i].isDirectory()) {
					folder.add(fileList[i]);
				}
			}
			int length = folder.size();
			String tempString[] = new String[length];
			for(int x=0;x<length;x++) {
				String temp = ""+folder.get(x);
				String[] temp2 = temp.split("\\\\");
				int at = temp2.length;
				//System.out.println(temp2);
				tempString[x] = temp2[at-1];
			}
			list.setListData(tempString);
		}
	}
	public static void LoadProfile() {
		String conf = MP3Path+"\\Profile.conf";
		if(!Filer.isExists(MP3Path)) {
			Filer.createNewFile(MP3Path);
		}
		else {
			if(!Filer.isEmpty(conf)) {
				MusicPath = Filer.getLineString(conf, 1);
				line = Integer.parseInt(Filer.getLineString(conf, 2));
				NowMusicNumber.setText(""+line);
				String tempArgs[] = new String[Filer.getLine(conf)-1];
				for(int i=2;i<Filer.getLine(conf);i++) {
					String temp = Filer.getLineString(conf, i);
					tempArgs[i] = temp;
				}
				list.setListData(tempArgs);
			}
		}
	}
	public static void SaveProfile() {
		String conf = MP3Path+"\\Profile.conf";
		if(!Filer.isExists(MP3Path)) {
			Filer.createNewFile(MP3Path);
		}
		else {
			if(!MusicPath.equals("")&&!MusicPath.equals("null")&&!MusicPath.equals(null)&&line!=0) {
				int length = list.getModel().getSize();
				Filer.Saver(conf, MusicPath, false);//MusicPath
				Filer.Saver(conf, ""+line, true);//line
				for(int i=0;i<length;i++) {
					ListModel<String> tempString = list.getModel();
					Filer.Saver(conf, ""+tempString.getElementAt(i), true);//list
				}
			}
		}
	}
}