package com.ace.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.ace.dto.student.StudentRequestDto;
import com.ace.dto.student.StudentResponseDto;

@Service("studentDao")
public class StudentDao {
	public static Connection con = null;
	static {
		try {
			con = JdbcConnection.getConnection();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public ArrayList<StudentResponseDto> selectAllStudents() {
		ArrayList<StudentResponseDto> list = new ArrayList<>();
		String sql = "select * from student";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				StudentResponseDto res = new StudentResponseDto();
				res.setId(rs.getString("id"));
				res.setName(rs.getString("name"));
				res.setDob(rs.getString("dob"));
				res.setGender(rs.getString("gender"));
				res.setPhone(rs.getString("phone"));
				res.setEducation(rs.getString("education"));
				list.add(res);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return list;
	}

	public int insertStudent(StudentRequestDto dto) {
		String sql = "insert into student values(?, ?, ?, ?, ?, ?)";
		int i = 0;
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, dto.getId());
			ps.setString(2, dto.getName());
			ps.setString(3, dto.getDob());
			ps.setString(4, dto.getGender());
			ps.setString(5, dto.getPhone());
			ps.setString(6, dto.getEducation());
			i = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
		return i;
	}

	public int deleteStudentById(String student_id) {
		String sql = "delete from student where id=?";
		int i = 0;
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, student_id);
			i = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
		return i;
	}

	public int insertStudentCourse(String course_id, String student_id) {
		String sql = "insert into student_course (student_id, course_id) values(?, ?)";
		int i = 0;
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, student_id);
			ps.setString(2, course_id);
			i = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
		return i;
	}

	public StudentResponseDto selectStudentById(String student_id) {
		String sql = "select * from student where id=?";
		StudentResponseDto res = new StudentResponseDto();
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, student_id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				res.setId(rs.getString("id"));
				res.setName(rs.getString("name"));
				res.setDob(rs.getString("dob"));
				res.setGender(rs.getString("gender"));
				res.setPhone(rs.getString("phone"));
				res.setEducation(rs.getString("education"));
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return res;
	}

	public int updateStudent(StudentRequestDto dto) {
		String sql = "update student set id=?, name=?, dob=?, gender=?, phone=?, education=? where id=?";
		int i = 0;
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, dto.getId());
			ps.setString(2, dto.getName());
			ps.setString(3, dto.getDob());
			ps.setString(4, dto.getGender());
			ps.setString(5, dto.getPhone());
			ps.setString(6, dto.getEducation());
			ps.setString(7, dto.getId());
			i = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
		return i;
	}

	public int updateStudentCourse(String course_id, String student_id) {
		String sql = "update student_course set course_id=? where student_id=?";
		int i = 0;
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, course_id);
			ps.setString(2, student_id);
			i = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
		return i;
	}

	public int deleteAttendCoursesByStudentId(String student_id) {
		String sql = "delete from student_course where student_id=?";
		int i = 0;
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, student_id);
			i = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
		return i;
	}

	public ArrayList<StudentResponseDto> selectStudentListByIdOrNameOrCourse(String id, String name, String course) {
		ArrayList<StudentResponseDto> list = new ArrayList<>();
		String sql = "select distinct student.id, student.name from student_course join student on student_course.student_id = student.id join course on student_course.course_id = course.id where student.id like ? or student.name like ? or course.name like ?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, "%" + id + "%");
			ps.setString(2, "%" + name + "%");
			ps.setString(3, "%" + course + "%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				StudentResponseDto res = new StudentResponseDto();
				res.setId(rs.getString("id"));
				res.setName(rs.getString("name"));
				list.add(res);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return list;
	}

}
