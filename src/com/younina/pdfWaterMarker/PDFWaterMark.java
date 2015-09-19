package com.younina.pdfWaterMarker;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JTextArea;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import javax.swing.JTextField;
import javax.swing.JCheckBox;

public class PDFWaterMark {
	private JFrame frmPdfbyYounian;
	private JLabel label;
	private JTextArea scripts;
	private JTextField distance;
	private JCheckBox checkBox;
	private JTextField fontsize;
	private JLabel label_3;
	private JTextField topacity;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PDFWaterMark window = new PDFWaterMark();
					window.frmPdfbyYounian.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static File checkExist(String filepath) {
		File file = new File(filepath);
		if (file.exists()) {// 判断文件目录的存在
			// System.out.println("文件夹存在！");
			if (file.isDirectory()) {// 判断文件的存在性
				// System.out.println("文件存在！");
			} else {
				// file.createNewFile();// 创建文件
				// System.out.println("文件不存在，创建文件成功！");
			}
		} else {
			// System.out.println("文件夹不存在！");
			File file2 = new File(file.getParent());
			file2.mkdirs();
			// System.out.println("创建文件夹成功！");
			if (file.isDirectory()) {
				// System.out.println("文件存在！");
			} else {
				// file.createNewFile();// 创建文件
				// System.out.println("文件不存在，创建文件成功！");
			}
		}
		return file;
	}

	public void waterMark(String inputFile, String outputFile,
			String waterMarkStr,int font,float opa,int dis,boolean repeat) {
		try {
			PdfReader reader = new PdfReader(inputFile);
			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(
					outputFile));

			int total = reader.getNumberOfPages() + 1;
			BaseFont base = BaseFont.createFont(
					"C:/WINDOWS/Fonts/SIMSUN.TTC,1", BaseFont.IDENTITY_H,
					BaseFont.EMBEDDED);

			Rectangle rect = (Rectangle) reader.getPageSize(1);
			int height = (int) rect.getHeight();
			waterMarkStr = waterMarkStr + " " + waterMarkStr + " "
					+ waterMarkStr;

			for (int i = 1; i < total; i++) {
				PdfContentByte over = stamper.getOverContent(i);
				over.beginText();
				over.setFontAndSize(base, font);
				over.setTextMatrix(30, 30);

				over.setColorFill(BaseColor.BLACK);

				PdfGState gs = new PdfGState();
				gs.setFillOpacity(opa);// 设置透明度
				over.setGState(gs);
				
				if(repeat){
					for (int j = -height; j < 2 * height; j += dis)
						over.showTextAligned(Element.ALIGN_LEFT, waterMarkStr, 0,
								j, 45);
				}else{
					over.showTextAligned(Element.ALIGN_LEFT, waterMarkStr, 0,
							(int)(0.12*height), 45);
				}

				
				over.endText();
			}
			stamper.close();
			reader.close();
			reader = null;

		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public PDFWaterMark() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmPdfbyYounian = new JFrame();
		frmPdfbyYounian.setTitle("PDF脚本水印程序  ———— BY Younian");
		frmPdfbyYounian.setResizable(false);
		frmPdfbyYounian.setBounds(100, 100, 725, 485);
		frmPdfbyYounian.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frmPdfbyYounian.getContentPane().setLayout(null);

		label = new JLabel(
				"输入脚本：( 例如：C:/1.pdf#C:/14.pdf#44444444444 版权所有 盗版必究& )");
		label.setBounds(10, 10, 422, 43);
		frmPdfbyYounian.getContentPane().add(label);

		scripts = new JTextArea();
		scripts.setText("C:/1.pdf#C:/temp/14.pdf#44444444444 版权所有 盗版必究&\r\nC:/1.pdf#C:/15.pdf#44444444444 版权所有 盗版必究&");
		
		JScrollPane scrollPane = new JScrollPane(scripts);
		scrollPane.setBounds(20, 63, 691, 306);
		frmPdfbyYounian.getContentPane().add(scrollPane);
		
		distance = new JTextField();
		distance.setText("100");
		distance.setBounds(146, 386, 66, 21);
		frmPdfbyYounian.getContentPane().add(distance);
		distance.setColumns(10);
		
		JLabel label_1 = new JLabel("间距：");
		label_1.setBounds(103, 389, 43, 15);
		frmPdfbyYounian.getContentPane().add(label_1);
		
		checkBox = new JCheckBox("多行重复");
		checkBox.setBounds(16, 385, 95, 23);
		frmPdfbyYounian.getContentPane().add(checkBox);
		
		JLabel label_2 = new JLabel("字号：");
		label_2.setBounds(103, 422, 43, 15);
		frmPdfbyYounian.getContentPane().add(label_2);
		
		fontsize = new JTextField();
		fontsize.setText("30");
		fontsize.setColumns(10);
		fontsize.setBounds(146, 416, 66, 21);
		frmPdfbyYounian.getContentPane().add(fontsize);
		
		label_3 = new JLabel("透明度：");
		label_3.setBounds(237, 389, 56, 15);
		frmPdfbyYounian.getContentPane().add(label_3);
		
		topacity = new JTextField();
		topacity.setText("0.2");
		topacity.setColumns(10);
		topacity.setBounds(291, 386, 66, 21);
		frmPdfbyYounian.getContentPane().add(topacity);
		
		JButton start = new JButton("开始");
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!scripts.getText().equals("")) {
					String scriptsStr = scripts.getText();
					scriptsStr = scriptsStr.replaceAll("\r|\n", "");
					String[] arr = scriptsStr.split("&");

					for (int i = 0; i < arr.length; i++) {
						if (!arr[i].equals("")) {
							String[] elements = arr[i].split("#");
							//System.out.println(arr[i]);
							checkExist(elements[1]);
							//System.out.println(elements[0]+"+"+ elements[1]+"+"+elements[2]);
							
							String strDis=distance.getText();
							int dis=Integer.parseInt(strDis);
							
							String strFont=fontsize.getText();
							int font=Integer.parseInt(strFont);
							
							String strOpa=topacity.getText();
							float opacity=Float.parseFloat(strOpa);
							
							if(checkBox.isSelected()){
								waterMark(elements[0], elements[1], elements[2],font,opacity,dis,true);
							}else waterMark(elements[0], elements[1], elements[2],font,opacity,0,false);
							
						}
					}

				}
			}
		});
		start.setBounds(618, 405, 93, 32);
		frmPdfbyYounian.getContentPane().add(start);
		
		
		
		
	}
}
