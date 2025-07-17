<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<!DOCTYPE html>
<f:view>
<html>
<head>
    <meta charset="UTF-8">
    <title>Provider OTP Verification</title>

    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #f0f2f5;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .form-container {
            background: #ffffff;
            padding: 30px 40px;
            border-radius: 15px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
            width: 400px;
        }

        .form-title {
            text-align: center;
            margin-bottom: 20px;
            color: #333333;
        }

        .form-label {
            font-weight: 500;
            color: #555555;
            display: block;
            margin-top: 10px;
        }

        .form-input {
            width: 100%;
            padding: 8px 10px;
            border-radius: 8px;
            border: 1px solid #cccccc;
            margin-top: 5px;
            font-size: 14px;
        }

        .submit-button {
            background-color: #4CAF50;
            color: white;
            padding: 10px 25px;
            border: none;
            border-radius: 8px;
            font-size: 15px;
            cursor: pointer;
            box-shadow: 0 3px 8px rgba(0, 0, 0, 0.2);
            margin-top: 20px;
            width: 100%;
        }

        .submit-button:hover {
            background-color: #45a049;
        }

        .form-group {
            margin-bottom: 15px;
            position: relative;
        }

        .resend-link {
            position: absolute;
            right: 0;
            top: 100%;
            margin-top: 5px;
            font-size: 13px;
            color: #008CBA;
            text-decoration: underline;
            cursor: pointer;
        }

        .resend-link:hover {
            color: #007bb5;
        }

        /* Message styling */
        .info-message {
            color: green;
            font-weight: 500;
            margin-bottom: 10px;
        }

        .warn-message {
            color: orange;
            font-weight: 500;
            margin-bottom: 10px;
        }

        .error-message {
            color: red;
            font-weight: 500;
            margin-bottom: 10px;
        }

        .fatal-message {
            color: darkred;
            font-weight: 500;
            margin-bottom: 10px;
        }

        #countdown-message {
            text-align: center;
            font-weight: 500;
            color: #4CAF50;
            margin-bottom: 10px;
            display: none;
        }
    </style>

   
</head>

<body>
    <h:form>
        <div class="form-container">
            <h2 class="form-title">Verify OTP</h2>

            <div class="form-group">
                <h:outputLabel for="email" value="Email ID:" styleClass="form-label" />
                <h:inputText id="email" value="#{providerController.provider.email}" styleClass="form-input" required="true" />
            </div>

            <div class="form-group">
                <h:outputLabel for="otp" value="OTP Code:" styleClass="form-label" />
                <h:inputText id="otp" value="#{providerController.otpCode}" styleClass="form-input" required="true" />
                <h:commandLink value="Resend Code" action="#{providerController.resendOtp}" styleClass="resend-link" immediate="true" />
            </div>
            
            <!-- JSF messages with severity-based styling -->
            <h:messages globalOnly="true" layout="list"
                infoClass="info-message"
                warnClass="warn-message"
                errorClass="error-message"
                fatalClass="fatal-message" />

            <div class="form-group">
                <h:commandButton 
                    value="Verify OTP" 
                    action="GeneratePassword.jsp" 
                    styleClass="submit-button">
                     
                </h:commandButton>
            </div>

            <!-- Trigger redirect only if OTP is verified -->
            <h:panelGroup rendered="#{providerController.otpVerified}">
                <script>
                    redirectToPasswordPage();
                </script>
            </h:panelGroup>
        </div>
    </h:form>
</body>
</html>
</f:view>