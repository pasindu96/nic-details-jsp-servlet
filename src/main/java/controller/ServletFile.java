package controller;

import dto.NICDataDTO;
import service.NICService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/")
public class ServletFile extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        System.out.println(action);
        switch (action) {
            case "/":
                System.out.println("NIC Form");
                break;
            case "/search":
                getNICData(request,response);
                System.out.println("--- Search Get ---");
                break;
            default:
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        String action = request.getServletPath();
        switch (action) {
            case "/":
                System.out.println("NIC Form");
                break;
            case "/search":
                String user = request.getParameter("nic");

                System.out.println("--- Search POST ---" + user);
                break;
            default:
                break;
        }
    }
    void getNICData(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
        String nic = request.getParameter("nic");
        NICService nicService=new NICService();
        NICDataDTO dto=nicService.validateNIC(nic);
        request.setAttribute("gender", dto.getGender());
        request.setAttribute("dob", dto.getBirthDate());
        ServletContext sc = this.getServletContext();
        RequestDispatcher dispatcher = sc.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }
}
