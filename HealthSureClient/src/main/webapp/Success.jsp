 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html>
<f:view>
<html>
<head>
<meta charset="UTF-8">
<title>Registration Successful</title>
</head>
<body>
    <h:panelGroup layout="block" style="margin-top: 50px; text-align: center;">
        <h2>🎉 Registration Successful!</h2>
        <p>Thank you for signing up. Your provider account has been submitted for approval.</p>

        <h:commandLink value="Go to Login Page" action="login.jsf" />
    </h:panelGroup>
</body>
</html>
</f:view>