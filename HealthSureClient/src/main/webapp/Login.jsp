<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core" %>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html" %>

    
<!DOCTYPE html>
<f:view>
<html>
<head>
<meta charset="UTF-8">
<title>Provider Login</title>

</head>
<body>
      <h:form prependId="false">
    <h:panelGrid columns="2">
        <h:outputLabel value="Email:" />
        <h:inputText value="#{providerController.provider.email}" required="true" />

        <h:outputLabel value="Password:" />
        <h:inputSecret value="#{providerController.provider.password}" required="true" />
    </h:panelGrid>

    <h:commandButton value="Login" action="#{providerController.login}" />
</h:form>
</body>
</html>
</f:view>