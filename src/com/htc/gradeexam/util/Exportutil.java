package com.htc.gradeexam.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.htc.gradeexam.manager.StudentManager;
import com.htc.gradeexam.manager.StudentManagerImpl;
import com.htc.gradeexam.model.Student;

/**
 * �����ļ�
 * 
 * @author �Ƶ���
 *
 */
public class Exportutil {

	/**
	 * ������excel
	 * 
	 * @param studentList
	 */
	
	private static StudentManager studentManager = new StudentManagerImpl();
	
	public static void ExportToExcel() {
		// ����һ��������
		HSSFWorkbook wb = new HSSFWorkbook();
		// ����һ��sheet��
		HSSFSheet sheet = wb.createSheet();
		sheet.setDefaultColumnWidth((int) 15);
		// ����һ����ʽ
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// ������ͷ
		HSSFRow row = null;
		row = sheet.createRow(0);
		HSSFCell cell = null;
		cell = row.createCell(0);
		cell.setCellValue("ѧ�����");
		cell.setCellStyle(style);

		cell = row.createCell(1);
		cell.setCellValue("ѧ������");
		cell.setCellStyle(style);

		cell = row.createCell(2);
		cell.setCellValue("�Ա�");
		cell.setCellStyle(style);

		cell = row.createCell(3);
		cell.setCellValue("����");
		cell.setCellStyle(style);

		cell = row.createCell(4);
		cell.setCellValue("��������");
		cell.setCellStyle(style);

		cell = row.createCell(5);
		cell.setCellValue("��ϵ�绰");
		cell.setCellStyle(style);

		cell = row.createCell(6);
		cell.setCellValue("��ͥ��ַ");
		cell.setCellStyle(style);

		cell = row.createCell(7);
		cell.setCellValue("�༶����");
		cell.setCellStyle(style);
		List<Student> studentList = studentManager.findStudentList();
		for (int i = 0; i < studentList.size(); i++) {
			row = sheet.createRow(i + 1);
			row.createCell(0).setCellValue(studentList.get(i).getStudentId());
			row.createCell(1).setCellValue(studentList.get(i).getStudentName());
			row.createCell(2).setCellValue(studentList.get(i).getSex());
			row.createCell(3)
					.setCellValue((int) ((System.currentTimeMillis() - studentList.get(i).getBirthday().getTime())/1000/60/60/24/365));
			row.createCell(4).setCellValue(DateUtil.format(studentList.get(i).getBirthday()));
			row.createCell(5).setCellValue(studentList.get(i).getContactTel());
			row.createCell(6).setCellValue(studentList.get(i).getAddress());
			row.createCell(7).setCellValue(studentList.get(i).getClasses().getClassName());
		}
		File filePath = new File("D:\\student");
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		String fileName = new SimpleDateFormat("yyyy��MM��dd��HHʱmm��ss��").format(new Date()) + ".xls";
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(new File(filePath, fileName));
			wb.write(outputStream);
			JOptionPane.showMessageDialog(null, "�����ɹ���");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "����ʧ�ܣ�");
			e.printStackTrace();
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void export() {
		List<Student> studentList = studentManager.findStudentList();
		StringBuffer sb = new StringBuffer();
		for (Iterator<Student> iterator = studentList.iterator(); iterator.hasNext();) {
			Student student = (Student) iterator.next();
			int age = (int)((System.currentTimeMillis() - student.getBirthday().getTime()) / 1000 / 60 / 60 / 24 / 365);
			sb.append("ѧ�����:").append(student.getStudentId())
				.append(",ѧ������:").append(student.getStudentName())
					.append(",�Ա�:").append(student.getSex())
					.append(",��������:").append(DateUtil.format(student.getBirthday()))
					.append(",��ϵ�绰:").append(student.getContactTel())
					.append(",��ͥסַ:").append(student.getAddress())
					.append(",����:").append(age)
					.append(",�༶���� ").append(student.getClasses().getClassName())
					.append("\n");
		}

		File filePath = new File("D:\\student");
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		String fileName = new SimpleDateFormat("yyyy��MM��dd��HHʱmm��ss��").format(new Date()) + ".txt";
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(new File(filePath, fileName)));
			bw.write(sb.toString());
			System.out.println("����ѧ����Ϣ�ɹ�!�ļ�λ�ã�"+ filePath.getAbsolutePath()+"\\"+ fileName);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
