package com.example.Rest_API.service;

import com.example.Rest_API.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class serviceImpl {
    public List<User> listUser() {
        List<User> userList = new ArrayList<>();
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;

        try {
            // Establish connection to the database
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/nck", "root", "");

            // Prepare the call to the stored procedure
            String sql = "{CALL ListUser()}"; // Calling the stored procedure
            callableStatement = connection.prepareCall(sql);

            // Execute the stored procedure and get the result set
            resultSet = callableStatement.executeQuery();

            // Iterate through the result set and populate the user list
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setImage(resultSet.getString("image"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close all resources
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (callableStatement != null) {
                    callableStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return userList;
    }


    public User addUserProc(User user) {
        Connection connection = null;
        CallableStatement callableStatement = null;

        try {
            // Mở kết nối với cơ sở dữ liệu
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/nck", "root", "");

            // Câu lệnh gọi stored procedure
            String storedProc = "{CALL AddUser(?, ?, ?)}";

            // Tạo CallableStatement để gọi procedure
            callableStatement = connection.prepareCall(storedProc);
            callableStatement.setString(1, user.getName());   // p_name
            callableStatement.setString(2, user.getEmail());  // p_email
            callableStatement.setString(3, user.getImage());  // p_image

            // Thực thi câu lệnh stored procedure
            callableStatement.executeUpdate();

            System.out.println("Thêm người dùng thành công.");

            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối và CallableStatement
            try {
                if (callableStatement != null) {
                    callableStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;  // Trả về null nếu có lỗi
    }
//    public User addUser(User user) {
//
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        try {
//            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/nck","root","");
//
//
//            String sql = "INSERT INTO user_test (name, email, image) VALUES (?, ?, ?)";
//
//            // Tạo PreparedStatement
//            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1, user.getName());
//            preparedStatement.setString(2, user.getEmail());
//
//            preparedStatement.setString(3, user.getImage());
//
//
//            // Thực thi câu truy vấn
//            int rowsAffected = preparedStatement.executeUpdate();
//
//            // Kiểm tra số hàng bị ảnh hưởng
//            if (rowsAffected > 0) {
//                System.out.println("Thêm người dùng thành công.");
//                return user;
//            } else {
//                System.out.println("Không thể thêm người dùng.");
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            // Đóng kết nối và PreparedStatement
//            try {
//                if (preparedStatement != null) {
//                    preparedStatement.close();
//                }
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return  null;
//    }

    public User findUser(String email) {
        User user = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/nck","root","");


            String sql = "SELECT * FROM user_test WHERE email = ?";

            // Create a PreparedStatement
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);

            // Execute the query
            resultSet = preparedStatement.executeQuery();

            // Check if the user is found
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setImage(resultSet.getString("image"));
            }
            return  user;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            // Đóng kết nối và PreparedStatement
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return user; // Return the found user or null if not found
    }



}
