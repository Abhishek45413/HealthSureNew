<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core" %>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html" %>

<!DOCTYPE html>
<f:view>
<html>
<head>
    <title>Add Doctor</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background: linear-gradient(135deg, #c3ecff, #e1f5fe);
            display: flex;
            justify-content: center;
            padding-top: 50px;
            min-height: 100vh;
        }

        .form-container {
            background-color: #ffffff;
            padding: 40px 50px;
            border-radius: 14px;
            box-shadow: 0 12px 24px rgba(0, 0, 0, 0.1);
            width: 600px;
        }

        h2 {
            text-align: center;
            color: #0d47a1;
            margin-bottom: 25px;
        }

        .form-group {
            margin-bottom: 18px;
        }

        label {
            display: block;
            margin-bottom: 6px;
            font-weight: 600;
            color: #444;
        }

        input[type="text"],
        input[type="email"],
        textarea,
        select {
            width: 100%;
            padding: 10px;
            border: 1px solid #bbb;
            border-radius: 6px;
            font-size: 15px;
            transition: 0.3s;
        }

        input:focus, select:focus, textarea:focus {
            border-color: #2196F3;
            outline: none;
        }

        .button-group {
            text-align: center;
            margin-top: 30px;
        }

        .submit-button {
            background-color: #0d47a1;
            color: white;
            padding: 12px 25px;
            border: none;
            border-radius: 6px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .submit-button:hover {
            background-color: #1565c0;
        }

        .messages {
            color: red;
            margin-bottom: 15px;
        }
    </style>
</head>

<body>
<div class="form-container">
    <h2>Register New Doctor</h2>
    <h:form>
        <h:messages globalOnly="true" styleClass="messages" />

        <!-- Provider ID -->
        <div class="form-group">
            <label for="providerId">Provider ID</label>
            <h:inputText id="providerId" value="#{doctorsController.doctor.providers.providerId}" />
        </div>

        <!-- Doctor Name -->
        <div class="form-group">
            <label for="doctorName">Doctor Name</label>
            <h:inputText id="doctorName" value="#{doctorsController.doctor.doctorName}" required="true" />
        </div>

        <!-- Qualification Dropdown -->
        <div class="form-group">
            <label for="qualification">Qualification</label>
            <h:selectOneMenu id="qualification" value="#{doctorsController.doctor.qualification}">
                <f:selectItem itemLabel="Select Qualification" itemValue="" />
                <f:selectItem itemLabel="MBBS" itemValue="MBBS" />
                <f:selectItem itemLabel="BDS" itemValue="BDS" />
                <f:selectItem itemLabel="BAMS" itemValue="BAMS" />
                <f:selectItem itemLabel="BHMS" itemValue="BHMS" />
                <f:selectItem itemLabel="BUMS" itemValue="BUMS" />
                <f:selectItem itemLabel="MD" itemValue="MD" />
                <f:selectItem itemLabel="MS" itemValue="MS" />
                <f:selectItem itemLabel="DNB" itemValue="DNB" />
            </h:selectOneMenu>
        </div>

        <!-- Specialization Dropdown -->
        <div class="form-group">
            <label for="specialization">Specialization</label>
            <h:selectOneMenu id="specialization" value="#{doctorsController.doctor.specialization}">
                <f:selectItem itemLabel="Select Specialization" itemValue="" />
                <f:selectItem itemLabel="Cardiologist" itemValue="Cardiologist" />
                <f:selectItem itemLabel="Dermatologist" itemValue="Dermatologist" />
                <f:selectItem itemLabel="Neurologist" itemValue="Neurologist" />
                <f:selectItem itemLabel="Oncologist" itemValue="Oncologist" />
                <f:selectItem itemLabel="Orthopedic" itemValue="Orthopedic" />
                <f:selectItem itemLabel="Psychiatrist" itemValue="Psychiatrist" />
                <f:selectItem itemLabel="General Surgeon" itemValue="General Surgeon" />
                <f:selectItem itemLabel="Neurosurgeon" itemValue="Neurosurgeon" />
            </h:selectOneMenu>
        </div>

        <!-- License No -->
        <div class="form-group">
            <label for="licenseNo">License Number</label>
            <h:inputText id="licenseNo" value="#{doctorsController.doctor.licenseNo}" required="true" />
        </div>

        <!-- Email -->
        <div class="form-group">
            <label for="email">Email</label>
            <h:inputText id="email" value="#{doctorsController.doctor.email}" required="true" />
        </div>

        <!-- Phone -->
        <div class="form-group">
            <label for="phoneNumber">Phone Number</label>
            <h:inputText id="phoneNumber" value="#{doctorsController.doctor.phoneNumber}" />
        </div>

        <!-- Address -->
        <div class="form-group">
            <label for="address">Address</label>
            <h:inputTextarea id="address" value="#{doctorsController.doctor.address}" rows="3" />
        </div>

        <!-- Gender Dropdown -->
        <div class="form-group">
            <label for="gender">Gender</label>
            <h:selectOneMenu id="gender" value="#{doctorsController.doctor.gender}" required="true">
                <f:selectItem itemLabel="Select Gender" itemValue="" />
                <f:selectItem itemLabel="Male" itemValue="MALE" />
                <f:selectItem itemLabel="Female" itemValue="FEMALE" />
                <f:selectItem itemLabel="Other" itemValue="OTHER" />
            </h:selectOneMenu>
        </div>

        <!-- Type Dropdown -->
        <div class="form-group">
            <label for="type">Doctor Type</label>
            <h:selectOneMenu id="type" value="#{doctorsController.doctor.type}">
                <f:selectItem itemLabel="Select Type" itemValue="" />
                <f:selectItem itemLabel="Standard" itemValue="STANDARD" />
                <f:selectItem itemLabel="Adhoc" itemValue="ADHOC" />
            </h:selectOneMenu>
        </div>

        <!-- Submit -->
        <div class="button-group">
            <h:commandButton value="Add Doctor" action="#{doctorsController.addDoctor}" styleClass="submit-button" />
        </div>
    </h:form>
</div>
</body>
</html>
</f:view>
