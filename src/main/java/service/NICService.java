package service;

import dto.NICDataDTO;
import entity.NICData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NICService {
    //Error codes
    /*
    1 - input is empty
    2 - 10 digits or 12 digits
    3 - first two digits should starts with 19 | 20
    4 - 10th digit should be V|X
    5 - <366 | < 866
    6 - Only contain numbers
    10 - success
     */

    //Stores whether the given nic is older format or latest format
    private boolean newNICFormat = false;
    //Check the year is leap or not
    private boolean leap = false;

    //Define 2 arrays which contains the number of days and the months || Can use a 2D array
    private String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private int[] normalYear = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public NICDataDTO validateNIC(String nic) {

        String gender = "";
        String dob = "";

        //Check whether the input is empty
        if (nic.trim().isEmpty())
            //Empty input error
            return new NICDataDTO(1);

        //Check whether the input has 10 or 12 digits
        if (nic.length() == 12 || nic.length() == 10) {
            //Check the nic whether the new format or old format
            newNICFormat = nic.length() == 12;
            if (newNICFormat) {
                try {
                    //Check whether the input characters are numbers
                    Long.parseLong(nic.substring(0, 12));
                } catch (NumberFormatException e) {
                    //Doesn't contain only numbers error
                    return new NICDataDTO(6);
                }
                //New NIC format
                //Check the NIC starts with 19 or 20
                if (nic.substring(0, 2).equals("19") || nic.substring(0, 2).equals("20")) {
                    //Check the middle numbers are in valid range (character 5,6 and 7)
                    if ((Integer.parseInt(nic.substring(4, 7)) > 0 && Integer.parseInt(nic.substring(4, 7)) <= 366) || Integer.parseInt(nic.substring(4, 7)) > 500 || Integer.parseInt(nic.substring(4, 7)) <= 866) {
                        gender = Integer.parseInt(nic.substring(4, 7)) > 500 ? "Female" : "Male";
                        dob = nic.substring(0, 4) + "-" + setBirthday(nic);
//                        //Save the search result to the database
//                        saveNICDetails(nic,gender,dob);

//                        saveNICDetails(new NICData(nic, gender, dob));
                        //return the results of new NIC
                        return new NICDataDTO(gender, dob, 10);
                    } else {
                        //Invalid number range error (4,5,6 characters)
                        return new NICDataDTO(5);
                    }
                } else {
                    //First two digits not started with 19 | 20 error
                    return new NICDataDTO(3);
                }
            } else {
                //Old NIC format
                try {
                    //Check whether the first nine characters are numbers
                    Long.parseLong(nic.substring(0, 9));
                } catch (NumberFormatException e) {
                    //Doesn't contain only numbers error (0 to 9 digits in the NIC)
                    return new NICDataDTO(6);
                }
                //Checks the 10th digit is V | X
                if (nic.substring(9).equalsIgnoreCase("v") || nic.substring(9).equalsIgnoreCase("x")) {
                    int birthYear = 1900 + Integer.parseInt(nic.substring(0, 2));
                    //Check the middle numbers are in valid range (character 3,4 and 5)
                    if ((Integer.parseInt(nic.substring(2, 5)) > 0 && Integer.parseInt(nic.substring(2, 5)) <= 366) || (Integer.parseInt(nic.substring(2, 5)) > 500 && Integer.parseInt(nic.substring(2, 5)) <= 866)) {
                        gender = Integer.parseInt(nic.substring(2, 5)) > 500 ? "Female" : "Male";
                        dob = (birthYear) + "-" + setBirthday(nic);
//                        //Save the search result to the database
                        saveNICDetails(nic,gender,dob);
//                        saveNICDetails(new NICData(nic, gender, dob));

                        //Return the results of old NIC format
                        return new NICDataDTO(gender, dob, 10);
                    } else {
                        //Invalid number range error (4,5,6 characters)
                        return new NICDataDTO(5);
                    }
                } else {
                    //Last digit not equals V | X error
                    return new NICDataDTO(4);
                }
            }
        } else {
            //Does not contains 10 digits
            return new NICDataDTO(2);
        }
    }

    //A method to find the birth month and the date
    private String setBirthday(String id) {
        int total_dates = 0;
        if (newNICFormat) {
            total_dates = Integer.parseInt(id.substring(4, 7));
        } else {
            total_dates = Integer.parseInt(id.substring(2, 5));
        }
        if (total_dates > 500) {
            total_dates = (total_dates - 500);
        }

        int birth_month = 0, birth_date = 0;
        int days = total_dates;

        for (int i = 0; i < normalYear.length; i++) {
            if (days <= normalYear[i]) {
                birth_month = i + 1;
                birth_date = days;
                break;
            } else {
                days = days - normalYear[i];
            }
        }
        //Concat and return the birthday as MM-dd
        return months[birth_month - 1] + "-" + (birth_date < 10 ? "0" + birth_date : birth_date);
    }

    void saveNICDetails(String nic, String gender, String birthday) {
        String dbURL = "jdbc:mysql://localhost:3306/nicdata?useSSL=false";
        String username = "root";
        String password = "";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(dbURL, username, password);

            String sql = "INSERT INTO nicdata (nic, birth_day, gender) VALUES (?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(sql);


            statement.setString(1, nic);
            statement.setString(2, birthday);
            statement.setString(3, gender);
            boolean rslt = statement.execute();
            System.out.println(rslt);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
