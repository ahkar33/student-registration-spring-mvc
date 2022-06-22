package com.ace.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.ace.dto.user.UserRequestDto;
import com.ace.dto.user.UserResponseDto;

@Service("userDao")
public class UserDao {
	public static Connection con = null;
	static {
		try {
			con = JdbcConnection.getConnection();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public boolean checkLogin(String email, String password) {
		String sql = "select * from user where binary `email`=? && binary `password`=?";
		try {
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, email);
			st.setString(2, password);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		return false;
	}

	public UserResponseDto selectUserByEmail(String email) {
		String sql = "select * from user where email=?";
		UserResponseDto res = new UserResponseDto();
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				res.setId(rs.getString("id"));
				res.setName(rs.getString("name"));
				res.setEmail(rs.getString("email"));
				res.setPassword(rs.getString("password"));
				res.setUserRole(rs.getString("role"));
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return res;
	}

	public UserResponseDto selectUserById(String id) {
		String sql = "select * from user where id=?";
		UserResponseDto res = new UserResponseDto();
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				res.setId(rs.getString("id"));
				res.setEmail(rs.getString("email"));
				res.setName(rs.getString("name"));
				res.setPassword(rs.getString("password"));
				res.setUserRole(rs.getString("role"));
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return res;
	}

	public ArrayList<UserResponseDto> selectAllUsers() {
		ArrayList<UserResponseDto> list = new ArrayList<>();
		String sql = "select * from user";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				UserResponseDto res = new UserResponseDto();
				res.setId(rs.getString("id"));
				res.setEmail(rs.getString("email"));
				res.setName(rs.getString("name"));
				res.setPassword(rs.getString("password"));
				res.setUserRole(rs.getString("role"));
				list.add(res);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return list;
	}

	public boolean checkEmailExists(String email) {

		String sql = "select * from user where email=?";
		try {
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, email);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		return false;
	}

	public int insertUser(UserRequestDto dto) {
		String sql = "insert into user values(?, ?, ?, ?, ?)";
		int i = 0;
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, dto.getId());
			ps.setString(2, dto.getEmail());
			ps.setString(3, dto.getName());
			ps.setString(4, dto.getPassword());
			ps.setString(5, dto.getUserRole());
			i = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
		return i;
	}

	public int deleteUserById(String id) {
		String sql = "delete from user where id=?";
		int i = 0;
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, id);
			i = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
		return i;
	}

	public int updateUser(UserRequestDto dto) {
		String sql = "update user set email=?, name=?, password=?, role=? where id=?";
		int i = 0;
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, dto.getEmail());
			ps.setString(2, dto.getName());
			ps.setString(3, dto.getPassword());
			ps.setString(4, dto.getUserRole());
			ps.setString(5, dto.getId());
			i = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
		return i;
	}

	public ArrayList<UserResponseDto> selectUserListById(String id) {
		ArrayList<UserResponseDto> list = new ArrayList<>();
		String sql = "select * from user where id like ?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, "%" + id + "%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				UserResponseDto res = new UserResponseDto();
				res.setId(rs.getString("id"));
				res.setEmail(rs.getString("email"));
				res.setName(rs.getString("name"));
				res.setPassword(rs.getString("password"));
				res.setUserRole(rs.getString("role"));
				list.add(res);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return list;
	}

	public ArrayList<UserResponseDto> selectUserListByName(String name) {
		ArrayList<UserResponseDto> list = new ArrayList<>();
		String sql = "select * from user where name like ?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, "%" + name + "%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				UserResponseDto res = new UserResponseDto();
				res.setId(rs.getString("id"));
				res.setEmail(rs.getString("email"));
				res.setName(rs.getString("name"));
				res.setPassword(rs.getString("password"));
				res.setUserRole(rs.getString("role"));
				list.add(res);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return list;
	}

	public ArrayList<UserResponseDto> selectUserListByIdOrName(String id, String name) {
		ArrayList<UserResponseDto> list = new ArrayList<>();
		String sql = "select * from user where id like ? or name like ?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, "%" + id + "%");
			ps.setString(2, "%" + name + "%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				UserResponseDto res = new UserResponseDto();
				res.setId(rs.getString("id"));
				res.setEmail(rs.getString("email"));
				res.setName(rs.getString("name"));
				res.setPassword(rs.getString("password"));
				res.setUserRole(rs.getString("role"));
				list.add(res);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return list;
	}
}
