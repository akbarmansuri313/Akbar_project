<%@page import="in.co.rays.model.RoleModel"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="in.co.rays.bean.PatientBean"%>
<%@page import="in.co.rays.controller.PatientListCtl"%>
<%@page import="in.co.rays.util.HTMLUtility"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.util.DataUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<%@include file="Header.jsp"%>

	<jsp:useBean id="bean" class="in.co.rays.bean.PatientBean"
		scope="request"></jsp:useBean>

	<div align="center">
		<h1 align="center" style="margin-bottom: -15; color: navy;">Patient
			List</h1>

		<div style="height: 15px; margin-bottom: 12px">
			<h3>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h3>
			<h3>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h3>
		</div>

		<form action="<%=ORSView.PATIENT_LIST_CTL%>" method="post">
			<%
				int pageNo = ServletUtility.getPageNo(request);

				int pageSize = ServletUtility.getPageSize(request);

				int index = ((pageNo - 1) * pageSize) + 1;

				int nextListSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

				List<PatientBean> roleList = (List<PatientBean>) request.getAttribute("roleList");

				List<PatientBean> list = (List<PatientBean>) ServletUtility.getList(request);

				Iterator<PatientBean> it = list.iterator();

				if (list.size() != 0) {
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

			<table style="width: 100%">
				<tr>
					<td align="center"><label><b>First Name :</b></label> <input
						type="text" name="name" placeholder="Enter First Name"
						value="<%=ServletUtility.getParameter("firstName", request)%>">&emsp;

						 <label><b>Mobile No:</b></label> <input type="text" name="mobileNo"
						placeholder="Enter Mobile No"
						value="<%=ServletUtility.getParameter("MobileNo", request)%>">&emsp;
 
						<label><b>Disesase : </b></label> <%=HTMLUtility.getList("disease", String.valueOf(bean.getdate_of_visit()), roleList)%>&emsp;

					
						<input type="submit" name="operation"
						value="<%=PatientListCtl.OP_SEARCH%>"> &nbsp; <input
						type="submit" name="operation"
						value="<%=PatientListCtl.OP_RESET%>"></td>
				</tr>
			</table>
			<br>

			<table border="1" style="width: 100%; border: groove;">
				<tr style="background-color: #e1e6f1e3;">
					<th width="5%"><input type="checkbox" id="selectall" /></th>
					<th width="5%">S.No</th>
					<th width="15%">Name</th>
					<th width="15%">Mobile No</th>
					<th width="15%">Date Visit</th>
					<th width="13%">Disease</th>
					<th width="5%">Edit</th>
				</tr>

				<%
                    while (it.hasNext()) {
                        bean = (PatientBean) it.next();
                       

                        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                        String date = sdf.format(bean.getdate_of_visit());
                %>

				<tr>
					<td style="text-align: center;"><input type="checkbox"
						class="case" name="ids" value="<%=bean.getId()%>"></td>
					<td style="text-align: center;"><%=index++%></td>
					<td style="text-align: center; text-transform: capitalize;"><%=bean.getName()%></td>
					<td style="text-align: center; text-transform: capitalize;"><%=bean.getMobileNo()%></td>
					<td style="text-align: center; text-transform: lowercase;"><%=bean.getdate_of_visit()%></td>
					<td style="text-align: center;"><%=bean.getDisease()%></td>

					
					<td style="text-align: center;"><a
						href="PatientCtl?id=<%=bean.getId()%>">Edit</a></td>
				</tr>

				<%
                    }
                %>
			</table>

			<table style="width: 100%">
				<tr>
					<td style="width: 25%"><input type="submit" name="operation"
						value="<%=PatientListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>
					<td align="center" style="width: 25%"><input type="submit"
						name="operation" value="<%=PatientListCtl.OP_NEW%>"></td>
					<td align="center" style="width: 25%"><input type="submit"
						name="operation" value="<%=PatientListCtl.OP_DELETE%>"></td>
					<td style="width: 25%" align="right"><input type="submit"
						name="operation" value="<%=PatientListCtl.OP_NEXT%>"
						<%=nextListSize != 0 ? "" : "disabled"%>></td>
				</tr>
			</table>

			<%
				} else {
			%>

			<table>
				<tr>
					<td align="right"><input type="submit" name="operation"
						value="<%=PatientListCtl.OP_BACK%>"></td>
				</tr>
			</table>

			<%
				}
			%>
		</form>
		<br> <br> <br>
	</div>
	<%@ include file="Footer.jsp"%>

</body>
</html>