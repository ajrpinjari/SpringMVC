package com.jbk.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.jbk.dao.EmployeeDao;
import com.jbk.entity.Employee;

@Service
public class EmployeeService_impl implements EmployeeService {

	@Autowired
	private EmployeeDao dao;

	@Override
	public Employee saveEmployee(Employee employee) {
		String employeeId = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		employee.setEmployeeId(employeeId);
		String createdDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(createdDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		employee.setCreatedDate(date);
		Employee emp = dao.saveEmployee(employee);
		return emp;
	}

	@Override
	public Employee getEmployeeById(String EmployeeId) {
		Employee Employee = dao.getEmployeeById(EmployeeId);
		return Employee;
	}

	@Override
	public List<Employee> getAllEmployee() {
		List<Employee> list = dao.getAllEmployee();
		return list;
	}

	@Override
	public boolean deleteEmployeeById(String EmployeeId) {

		boolean b = dao.deleteEmployeeById(EmployeeId);
		return b;
	}

	@Override
	public Employee updateEmployee(Employee Employee) {
		Employee emp = dao.updateEmployee(Employee);
		return emp;
	}

	@Override
	public Employee login(Employee employee) {
		Employee emp = dao.login(employee);
		return emp;
	}

	@Override
	public void logout(String employeeId, HttpSession session) {
		dao.logout(employeeId);
		session.invalidate();

	}

	public List<Employee> readExcel(String filePath) {
		List<Employee> list = new ArrayList<>();

		try {
			FileInputStream fis = new FileInputStream(new File(filePath));

			Workbook workbook = new XSSFWorkbook(fis);

			Sheet sheet = workbook.getSheetAt(0);

			Iterator<Row> rows = sheet.rowIterator();
			Employee employee = null;
			while (rows.hasNext()) {
				employee = new Employee();
				String employeeId = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
				employee.setEmployeeId(employeeId);
				String createdDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
				Date date=null;
				try {
					date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(createdDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				employee.setCreatedDate(date);
				
				Row row = rows.next();

				Iterator<Cell> cells = row.cellIterator();
				while (cells.hasNext()) {
					Cell cell = cells.next();
					int colIndex = cell.getColumnIndex();
					if (cell.getCellType() == CellType.NUMERIC) {
						cell.setCellType(CellType.STRING);
					}
					switch (colIndex) {
					case 0:
						employee.setUsername(cell.getStringCellValue());
						break;
					case 1:
						employee.setPassword(cell.getStringCellValue());
						break;
					case 2:
						employee.setDepartment(cell.getStringCellValue());
						break;

					case 3:
						employee.setGender(cell.getStringCellValue());
						break;
					case 4:
						employee.setRole(cell.getStringCellValue());
						break;
					case 5:
						employee.setEmail(cell.getStringCellValue());
						break;

					case 6:
						Long salary = Long.parseLong(cell.getStringCellValue());
						employee.setSalary(salary);
						break;
					case 7:
						employee.setPhone(cell.getStringCellValue());
						break;
					case 8:
						employee.setQuestion(cell.getStringCellValue());
						break;

					case 9:
						employee.setAnswer(cell.getStringCellValue());
						break;

					default:
						break;
					}

				}
				list.add(employee);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;

	}

	@Override
	public String uploadsheet(CommonsMultipartFile file, HttpSession session) {
		String msg =null;
		String path = session.getServletContext().getRealPath("WEB-INF/uploaded");
		System.out.println("Path:-" + path);
		String fileName = file.getOriginalFilename();
		System.out.println("File Name : " + fileName);

		byte[] data = file.getBytes();
		try {

			FileOutputStream fos = new FileOutputStream(new File(path + File.separator + fileName));

			fos.write(data); // uploaded

			List<Employee> list = readExcel(path + File.separator + fileName);

			 msg = dao.saveEmployees(list);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return msg;
	}
	
	

}
