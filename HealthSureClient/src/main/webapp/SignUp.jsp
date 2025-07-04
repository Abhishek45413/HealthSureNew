<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<!DOCTYPE html>
<f:view>
<html>
<head>
    <meta charset="UTF-8">
    <title>Provider SignUp</title>
   
</head>
<body>
    <h:form>
        <div class="form-container">
            <h2>Provider SignUp</h2>

            <h:panelGrid columns="2" cellpadding="5">
                
                <h:outputLabel value="Provider Name:" />
                <h:inputText value="#{providerController.provider.providerName}" required="true" />

                <h:outputLabel value="Hospital Name:" />
                <h:inputText value="#{providerController.provider.hospitalName}" />

                <h:outputLabel value="Email:" />
                <h:inputText value="#{providerController.provider.email}" required="true" />

                <h:outputLabel value="Password:" />
                <h:inputSecret value="#{providerController.provider.password}" required="true" />

                <h:outputLabel value="Address:" />
                <h:inputTextarea value="#{providerController.provider.address}" rows="3" cols="30" />

                <h:outputLabel value="City:" />
                <h:inputText value="#{providerController.provider.city}" />

                <h:outputLabel value="State:" />
                <h:inputText value="#{providerController.provider.state}" />

                <h:outputLabel value="Zipcode:" />
                <h:inputText value="#{providerController.provider.zipcode}" />
            </h:panelGrid>

            <div class="form-buttons">
                <h:commandButton value="Sign Up" action="#{providerController.register}" />
                
            </div>
        </div>
    </h:form>
</body>
</html>
</f:view>