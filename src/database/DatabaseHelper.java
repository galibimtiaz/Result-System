package database;

import model.*;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseHelper {

    private static DatabaseHelper instance;

    private Connection c = null;
    private Statement stmt = null;

    private DatabaseHelper() {

        c = SQLiteConnection.Connector();

    }

    public static DatabaseHelper getInstance() {
        if (instance == null) {
            instance = new DatabaseHelper();
        }
        return instance;
    }


    public User LogIn(String username, String pass) {

        User user = new User();
        try {
            ResultSet rs = executeQuery("SELECT * FROM login WHERE username = '" + username + "'");
            if (rs.next()) {
                String password = rs.getString("password");
                int login_id = rs.getInt("id");
                if (password.equals(pass)) {
                    rs = executeQuery("SELECT * FROM users WHERE user_id = '" + login_id + "'");
                    if (rs.next()) {
                        user.setUserType(rs.getString("userType"));
                        user.setName(rs.getString("name"));
                        user.setId(rs.getInt("user_id"));
                        user.setDept(rs.getString("dept"));
                        user.setId_number(rs.getInt("id_number"));
                        commitSql("update session SET "+
                                "id ="+ user.getId()+", "+
                                "name ='"+ user.getName()+"', "+
                                "dept ='"+ user.getDept()+"', "+
                                "userType ='"+ user.getUserType()+"', "+
                                "id_number ="+ user.getId_number()+
                        " where id = "+getCurrentUser().getId());
                    }
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }





    public void dropTable(String name) {

        String users = "DROP TABLE " + name + ";";
        commitSql(users);

    }

    private ArrayList<String> getStrings(ResultSet rs,ArrayList<String> list){

        try {
            while (rs.next()) {
                list.add(rs.getString("semester"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private ArrayList<Integer> getInts(ResultSet rs,ArrayList<Integer> list){

        try {
            while (rs.next()) {
                list.add(rs.getInt("year"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    private User getUser(ResultSet rs){

        try {
            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String dept = rs.getString("dept");
                String type = rs.getString("userType");
                int id_num = rs.getInt("id_number");
                int user_id = rs.getInt("user_id");
                return new User(id,name,user_id,dept,type,id_num);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    private ArrayList<User> getUsers(ResultSet rs,ArrayList<User> users){

        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String dept = rs.getString("dept");
                String type = rs.getString("userType");
                int id_num = rs.getInt("id_number");
                int user_id = rs.getInt("user_id");
                users.add(new User(id,name,user_id,dept,type,id_num));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }


    public User getCurrentUser() {

        ResultSet rs = executeQuery("SELECT * FROM session");
        return getUser(rs);
    }


    public User getUserByIDNumber(int id_number) {

        ResultSet rs = executeQuery("SELECT * FROM users where id_number = " + id_number + " and userType = 'student'");
        return getUser(rs);
    }

    public ArrayList<User> getUsersByType(String user_type) {

        ArrayList<User> users = new ArrayList<>();
        ResultSet rs = executeQuery("SELECT * FROM users where userType = '"+ user_type +"'");

        return getUsers(rs,users);
    }

    public ArrayList<String> getSubjectSemesterByYear(int year,int id) {

        ArrayList<String> list = new ArrayList<>();
        ResultSet rs = executeQuery("SELECT DISTINCT semester FROM user_subjects WHERE year = "+year+" and id_number = "+id+" ORDER BY semester ASC");

        return getStrings(rs,list);
    }

    public ArrayList<String> getSemesterByYear(int year,int id) {

        ArrayList<String> list = new ArrayList<>();
        ResultSet rs = executeQuery("SELECT DISTINCT semester FROM results WHERE year = "+year+" and student_id = "+id+" ORDER BY semester ASC");

        return getStrings(rs,list);
    }


    public ArrayList<Integer> getYearsByUserId(int id) {

        ArrayList<Integer> list = new ArrayList<>();
        ResultSet rs = executeQuery("SELECT DISTINCT year FROM results WHERE student_id = "+id+" ORDER BY year ASC");

        return getInts(rs,list);
    }


    public ArrayList<Integer> getSubjectYearsByUserId(int id) {

        ArrayList<Integer> list = new ArrayList<>();
        ResultSet rs = executeQuery("SELECT DISTINCT year FROM user_subjects WHERE id_number = "+id+" ORDER BY year ASC");

        return getInts(rs,list);
    }


    public ArrayList<Department> getDepartments() {

        ArrayList<Department> list = new ArrayList<>();
        ResultSet rs = executeQuery(" SELECT * FROM departments");
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String about = rs.getString("about");
                list.add(new Department(id, name, about));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return list;
    }


    private ArrayList<Subject> getSubjects(ResultSet rs,ArrayList<Subject> list) {

        try {
            while (rs.next()) {
                int id;
                if(hasColumn(rs,"user_subjects_id")){
                    id = rs.getInt("user_subjects_id");
                }else {
                    id = rs.getInt("id");

                }
                String sub_code = rs.getString("sub_code");
                String name = rs.getString("name");
                String dept = rs.getString("dept");
                Double credit_hour = rs.getDouble("credit_hour");
                list.add(new Subject(id,sub_code,name,dept,credit_hour));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return list;
    }


    private ArrayList<Result> getResults(ResultSet rs,ArrayList<Result> list){

        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                int student_id = rs.getInt("student_id");
                String student_name = rs.getString("student_name");
                int year = rs.getInt("year");
                String semester = rs.getString("semester");
                String course_code = rs.getString("course_code");
                Double credit_hour = rs.getDouble("credit_hour");
                String subject_name = rs.getString("subject_name");
                String grade = rs.getString("grade");
                int total = rs.getInt("total");

                int class_test = rs.getInt("class_test");
                int assignment = rs.getInt("assignment");
                int attendance = rs.getInt("attendance");
                int mid = rs.getInt("mid");
                int _final = rs.getInt("final");
                Double cgpa = rs.getDouble("cgpa");
                list.add( new Result(id,student_id,student_name,year,semester,course_code,credit_hour,subject_name,grade,total,class_test,
                        assignment,attendance,mid,_final,cgpa));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }


    public ArrayList<Subject> getSubjectsByDepartment(String dep) {

        ArrayList<Subject> list = new ArrayList<>();
        ResultSet rs = executeQuery(" SELECT * FROM subjects where dept ='"+dep+"'");

        return getSubjects(rs,list);
    }



    public ArrayList<Subject> getSubjectsbyIdNumber(int id_number,String semester,int year) {

        ArrayList<Subject> list = new ArrayList<>();
        ResultSet rs = executeQuery("SELECT user_subjects.id as user_subjects_id ,subjects.id as subject_id ,sub_code,name,dept,credit_hour FROM subjects inner join user_subjects on subjects.id = user_subjects.subject_id where user_subjects.id_number  ="+id_number+" and user_subjects.semester ='"+semester+"' and user_subjects.year = "+year);

        return getSubjects(rs,list);
    }

    public ArrayList<Subject> getSubjectsbyIdYearsemester(int id_number, String semester, int year) {

        ArrayList<Subject> list = new ArrayList<>();
        ResultSet rs = executeQuery("SELECT user_subjects.id as user_subjects_id ,subjects.id as subject_id ,sub_code,name,dept,credit_hour FROM subjects inner join user_subjects on subjects.id = user_subjects.subject_id where user_subjects.id_number = "+id_number+" and  year = "+year+" and semester = '"+semester+"'");

        return getSubjects(rs,list);
    }


    public ArrayList<Result> getResultsBySubject(String subject_code,int resultYear) {

        ArrayList<Result> list = new ArrayList<>();
        ResultSet rs = executeQuery("SELECT * FROM results where course_code ='"+subject_code+"' and year = "+resultYear+";");


        return getResults(rs,list);
    }


    public ArrayList<Result> getResultsByYearSemester(String _semester,int resultYear, int _id) {

        ArrayList<Result> list = new ArrayList<>();
        ResultSet rs = executeQuery("SELECT * FROM results where semester ='"+_semester+"' and student_id = "+_id+" and year = "+resultYear+";");

        return getResults(rs,list);
    }



    public ArrayList<Result> getResultsByTeachersId(int teacherId) {

        ArrayList<Result> list = new ArrayList<>();
        ResultSet rs = executeQuery("SELECT * FROM results inner join users on results.student_id = users.id_number where teacher_id ="+teacherId+";");

        return getResults(rs,list);
    }


    public ArrayList<Result> getResultsByStudentsId(int studentsId) {

        ArrayList<Result> list = new ArrayList<>();
        ResultSet rs = executeQuery("SELECT * FROM results inner join users on results.student_id = users.id_number where teacher_id ="+studentsId+";");

        return getResults(rs,list);
    }

    public void addStudent(){

        String sql = "INSERT INTO user (id,name,about) " +
                "VALUES (null , 'CSE', 'the CSE department');";
        commitSql(sql);

    }


    public int addUser(User user) {

        String sql = "INSERT INTO users (id,name,dept,userType,id_number,user_id) " +
                "VALUES "+user.toSQL();

        return commitSql(sql);
    }

    public int addResult(Result result) {

        String sql = "INSERT INTO results (id,student_id,student_name,year,semester,course_code,credit_hour,subject_name,grade,total,class_test,assignment,attendance,mid,final,cgpa) " +
                "VALUES "+result.toSQL();

        return  commitSql(sql);
    }


    public int updateResult(Result result) {

        String sql = "UPDATE results SET credit_hour = "+ result.getCreditHour()+
                ", grade = '"+ result.getGrade() +"'"+
                ", cgpa = "+ result.getCgpa() +
                ", total = "+ result.getTotal() +
                ", class_test = "+ result.getClass_test() +
                ", assignment = "+ result.getAssignment() +
                ", attendance = "+ result.getAttendance() +
                ", mid = "+ result.getMid() +
                ", final = "+ result.get_final()+
                " WHERE id = "+result.getId();

        System.out.println(sql);
        return  commitSql(sql);
    }

    public int updateUser(User user) {

        String sql = "UPDATE users SET name = '"+ user.getName()+"'"+
                ", dept = '"+ user.getDept() +"'"+

                " WHERE id = "+user.getId();

        System.out.println(sql);
        return  commitSql(sql);
    }

    public int addUserSubjects(int userIDNumer,int subjectID,int year, String semester) {

        String sql = "INSERT INTO user_subjects (id,id_number,subject_id,year,semester) " +
                "VALUES "+"("+null+","+userIDNumer+","+subjectID+","+year+",'"+semester+"');";

        return  commitSql(sql);
    }


    public int removeUserSubjects(int id) {

        String sql = "DELETE FROM user_subjects WHERE id = "+id;

        return  commitSql(sql);
    }

    public int removeUserResult(String semester,String subject_code,int year) {

        String sql = "DELETE from results where semester= '"+semester+"' and course_code = '"+subject_code+"' and year = "+year;

        return  commitSql(sql);
    }


    public int addSubject(Subject subject) {

        String sql = "INSERT INTO subjects (id,sub_code,name,dept,credit_hour) " +
                "VALUES "+subject.toSQL();

        return  commitSql(sql);
    }

    public int addDepartment(Department department) {

        String sql = "INSERT INTO departments (id,name,about) " +
                "VALUES "+department.toSQL();

        return  commitSql(sql);
    }

    public int addLoginUser(String username, String password) {

        String sql = "INSERT INTO login (id,username,password) " +
                "VALUES (null , '" + username + "', '" + password + "');";

        return commitSql(sql);
    }


    public User newUser(String username, String password, User user) {

        user.setUser_id(addLoginUser(username, password));
        addUser(user);

        return user;

    }

    private int commitSql(String query) {
        int id = 0;
        try {
            System.out.println("Opened database successfully");
            stmt = c.createStatement();
            stmt.executeUpdate(query);
            id = stmt.getGeneratedKeys().getInt(1);
            System.out.println("Done " + query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    private ResultSet executeQuery(String query) {

        ResultSet rs = null;

        try {
            System.out.println("Opened database successfully");
            stmt = c.createStatement();
            rs = stmt.executeQuery(query);
            System.out.println("Done " + query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rs;
    }

    public static boolean hasColumn(ResultSet rs, String columnName) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columns = rsmd.getColumnCount();
        for (int x = 1; x <= columns; x++) {
            if (columnName.equals(rsmd.getColumnName(x))) {
                return true;
            }
        }
        return false;
    }


}
