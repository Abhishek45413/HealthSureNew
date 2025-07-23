<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core" %>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html" %>

<!DOCTYPE html>
<html>
<f:view>
<head>
  <meta charset="UTF-8"/>
  <title>Provider Login - HealthSure</title>

  <!-- Tailwind & Font Awesome -->
  <link
    href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css"
    rel="stylesheet"/>
  <link
    href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"
    rel="stylesheet"/>

  <style>
    body {
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      background: linear-gradient(to bottom right, #eef2ff, #dbeafe);
      min-height: 100vh;
      display: flex;
      justify-content: center;
      align-items: center;
      padding: 1rem;
    }
    .form-card {
      background: white;
      padding: 2rem;
      border-radius: 1rem;
      box-shadow: 0 8px 30px rgba(0,0,0,0.1);
      width: 100%;
      max-width: 420px;
      animation: fadeIn 0.7s ease-out;
    }
    @keyframes fadeIn {
      from { opacity: 0; transform: translateY(20px); }
      to   { opacity: 1; transform: translateY(0); }
    }
    .field-label {
      font-weight: 600;
      color: #374151;
    }
    .form-input {
      width: 100%;
      padding: 0.5rem 0.75rem;
      border: 1px solid #ccc;
      border-radius: 0.5rem;
      font-size: 1rem;
      margin-top: 0.25rem;
      margin-bottom: 1rem;
    }
    .login-button {
      background-color: #2563eb;
      color: white;
      font-weight: 600;
      padding: 0.75rem;
      width: 100%;
      border-radius: 0.5rem;
      transition: background-color 0.3s ease;
    }
    .login-button:hover {
      background-color: #1e40af;
    }
    .link-text {
      color: #2563eb;
      font-size: 0.9rem;
      text-align: center;
      margin-top: 0.5rem;
      display: block;
    }
    .link-text:hover {
      text-decoration: underline;
    }
    .error-message {
      color: red;
      font-size: 0.875rem;
      margin-bottom: 1rem;
      text-align: center;
    }
  </style>
</head>
<body>
  <h:form prependId="false">
    <div class="form-card">
      <div class="text-center mb-6">
        <i class="fas fa-user-md text-blue-600 text-4xl mb-2"></i>
        <h2 class="text-xl font-bold text-gray-800">Provider Login</h2>
        <p class="text-sm text-gray-500">Welcome back! Please sign in to continue.</p>
      </div>

      <h:messages globalOnly="true" styleClass="error-message"/>

      <div>
        <h:outputLabel for="email" value="Email Address" styleClass="field-label"/>
        <h:inputText id="email" value="#{providerController.provider.email}"
                     required="true" requiredMessage="Email is required"
                     styleClass="form-input"/>
        <h:message for="email" styleClass="text-red-500 text-sm"/>
      </div>

      <div>
        <h:outputLabel for="password" value="Password" styleClass="field-label"/>
        <h:inputSecret id="password" value="#{providerController.provider.password}"
                       required="true" requiredMessage="Password is required"
                       styleClass="form-input"/>
        <h:message for="password" styleClass="text-red-500 text-sm"/>
      </div>

      <h:outputLink value="forgotPassword.jsf" styleClass="link-text">Forgot Password?</h:outputLink>

      <div class="mt-4">
        <h:commandButton value="Login"
                         action="#{providerController.login}"
                         styleClass="login-button"/>
      </div>

      <div class="mt-6 text-center text-sm">
        <span class="text-gray-600">Don't have an account?</span>
        <h:outputLink value="SignUp.jsf" styleClass="link-text">Sign Up</h:outputLink>
      </div>
    </div>
  </h:form>
</body>
</f:view>
</html>