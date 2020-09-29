<%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 9/28/2020
  Time: 11:14 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    <%@include file="styles.css" %>
</style>
<script>
    <%@include file="script.js" %>
</script>
<html>

<head>
    <title>NIC DOB Finder</title>
    <link rel="icon" href="https://www.pngrepo.com/png/77000/180/id-card.png">
    <%--  <link rel="stylesheet" href="styles.css" />--%>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <link href="https://fonts.googleapis.com/css2?family=Barlow+Condensed&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Barlow+Condensed:wght@400;500&family=Rubik:wght@600&display=swap"
          rel="stylesheet">

</head>

<body>

<div class="card" style="width: 30rem; margin-top: 100px; border: 2px solid black;">
    <img src="https://www.shareicon.net/data/256x256/2016/09/01/822585_world_512x512.png" class="center" alt="..."
         width="100px">
    <h3 class="card-title"><b>Sri Lanka NIC Information</b></h3>
    <div class="card-body">
        <form action="search" method="get">

            <div class="row">
                <div class="col-5">
                    <label id="niclabel">Enter your NIC </label>
                </div>
                <div class="col-6">
                    <input type="text" id="nic" name="nic" maxLength="12" class="form-control"
                           placeholder="Enter your NIC here..."
                           style="font-weight: bold;" required/>
                </div>
            </div>
            <br>
            <div>
                <button id="btnSearch" type="submit">Search Details</button>
            </div>
        </form>

    </div>

    <span id="gender" class="data">${gender}</span>
    <br>
    <span id="dob" class="data">${dob}</span>
    <!-- A function to trigger the click event by pressing the enter key -->
    <script>
        let input = document.getElementById("nic");
        document.getElementById("nic").addEventListener("keyup", function (event) {
            if (event.keyCode === 13) {
                event.preventDefault();
                document.getElementById("btnSearch").click();
            }
        });
    </script>

</div>


<!-- View all records in the database -->
<%--  <div class="card" style="width: 50rem; margin-top: 40px;" id="tblDiv">--%>
<%--    <br>--%>
<%--    <h3 class="card-title"><b>Previous Records</b></h3>--%>
<%--    <div class="card-body">--%>
<%--      <div class="row">--%>
<%--        <table class="table table-striped" style="text-align: center; align-content: center;" id="tblRecords">--%>
<%--          <tr class="bg-info">--%>
<%--            <th>NIC</th>--%>
<%--            <th>Gender</th>--%>
<%--            <th>Birthday</th>--%>
<%--            <th>Edit</th>--%>
<%--            <th>Delete</th>--%>
<%--          </tr>--%>
<%--          <tbody id="myTable">--%>
<%--          <!-- Table body which is loaded dynamucally -->--%>
<%--          </tbody>--%>
<%--        </table>--%>
<%--      </div>--%>
<%--    </div>--%>
<%--  </div>--%>
</body>

</html>
