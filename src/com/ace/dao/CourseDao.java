package com.ace.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.ace.dto.course.CourseRequestDto;
import com.ace.dto.course.CourseResponseDto;

@Service("courseDao")
public class CourseDao {
	public static Connection con = null;
	static {
		try {
			con = JdbcConnection.getConnection();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public boolean checkCourseName(String name) {

		String sql = "select * from course where name=?";
		try {
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, name);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		return false;
	}

	public ArrayList<CourseResponseDto> selectAllCourses() {
		ArrayList<CourseResponseDto> list = new ArrayList<>();
		String sql = "select * from course";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				CourseResponseDto res = new CourseResponseDto();
				res.setId(rs.getString("id"));
				res.setName(rs.getString("name"));
				list.add(res);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return list;
	}

	public int insertCourse(CourseRequestDto dto) {
		String sql = "insert into course values(?, ?)";
		int i = 0;
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, dto.getId());
			ps.setString(2, dto.getName());
			i = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
		return i;
	}

	public ArrayList<CourseResponseDto> selectCoursesByStudentId(String student_id) {
		ArrayList<CourseResponseDto> list = new ArrayList<>();
		String sql = "select course.name, course.id from student_course join course on student_course.course_id = course.id where student_course.student_id = ? ";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, student_id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				CourseResponseDto res = new CourseResponseDto();
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
