<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<!DOCTYPE html>
<html>
<head>
    <title>Set New Password</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <style>
        @keyframes fadeIn {
            from { opacity: 0; }
            to { opacity: 1; }
        }
        .fade-in {
            animation: fadeIn 0.5s ease-in forwards;
        }
    </style>
    <script>
        function checkStrength(password) {
            let strengthBar = document.getElementById("strengthBar");
            let strengthText = document.getElementById("strengthText");
            let errorMsg = document.getElementById("passwordError");
            let strength = 0;

            const regex = /^(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[A-Z]).{8,}$/;

            if (password.length >= 8) strength++;
            if (/[0-9]/.test(password)) strength++;
            if (/[!@#$%^&*]/.test(password)) strength++;
            if (/[A-Z]/.test(password)) strength++;

            strengthBar.value = strength;
            const levels = ["Very Weak", "Weak", "Moderate", "Strong", "Very Strong"];
            const colors = ["#dc2626", "#f97316", "#facc15", "#4ade80", "#22c55e"];
            strengthText.innerText = `${emojis[strength]} Strength: ${levels[strength]}`;
            strengthText.style.color = colors[strength];

            if (!regex.test(password)) {
                errorMsg.innerText = "Password must be at least 8 characters and include a number, a special character, and an uppercase letter.";
                errorMsg.classList.add("fade-in");
            } else {
                errorMsg.innerText = "";
                errorMsg.classList.remove("fade-in");
            }

            checkConfirmMatch();
        }

        function checkConfirmMatch() {
            const password = document.getElementById("newPassword").value;
            const confirm = document.getElementById("confirmPassword").value;
            const confirmError = document.getElementById("confirmError");

            if (confirm && password !== confirm) {
                confirmError.innerText = "Passwords do not match.";
                confirmError.classList.add("fade-in");
            } else {
                confirmError.innerText = "";
                confirmError.classList.remove("fade-in");
            }
        }

        function toggleVisibility(id) {
            const input = document.getElementById(id);
            input.type = input.type === "password" ? "text" : "password";
        }

        function toggleInstructions() {
            const box = document.getElementById("instructionBox");
            box.classList.toggle("hidden");
        }
    </script>
</head>
<body class="bg-gradient-to-tr from-blue-100 via-indigo-200 to-purple-100 flex items-center justify-center min-h-screen">
    <f:view>
        <h:form styleClass="bg-white p-8 rounded shadow-md w-full max-w-md transition transform hover:scale-105 hover:shadow-2xl">
            <h2 class="text-2xl font-bold mb-6 text-center text-gray-800">Set Your New Password</h2>

            <!-- New Password Field -->
            <div class="mb-4 relative">
                <label for="newPassword" class="block text-gray-700 font-medium mb-2">New Password</label>
                <input type="password" id="newPassword" name="newPassword"
                       class="w-full px-4 py-2 border rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
                       onkeyup="checkStrength(this.value)" />
                <button type="button" onclick="toggleVisibility('newPassword')" 
                        class="absolute right-3 top-9 text-gray-500">
                    <i class="fas fa-eye">Show</i>
                </button>
                <p id="passwordError" class="text-red-500 text-sm mt-1"></p>

                <div class="mt-2">
                    <progress id="strengthBar" value="0" max="4" class="w-full h-2"></progress>
                    <p id="strengthText" class="text-sm mt-1 font-medium">Strength: </p>
                </div>
            </div>

            <!-- Password Instructions -->
            <div class="mt-4">
                <button type="button"
                        onclick="toggleInstructions()"
                        class="text-blue-600 hover:underline text-sm">
                    📘 Password Instructions
                </button>

                <div id="instructionBox" class="mt-2 hidden text-sm text-gray-700 bg-blue-50 border border-blue-300 p-3 rounded">
                    <ul class="list-disc pl-5">
                        <li>Minimum 8 characters</li>
                        <li>At least 1 uppercase letter (A–Z)</li>
                        <li>At least 1 number (0–9)</li>
                        <li>At least 1 special character (!@#$%^&*)</li>
                    </ul>
                </div>
            </div>

            <!-- Confirm Password Field -->
            <div class="mb-6 relative">
                <label for="confirmPassword" class="block text-gray-700 font-medium mb-2">Confirm Password</label>
                <input type="password" id="confirmPassword" name="confirmPassword"
                       class="w-full px-4 py-2 border rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
                       onkeyup="checkConfirmMatch()" />
                <button type="button" onclick="toggleVisibility('confirmPassword')" 
                        class="absolute right-3 top-9 text-gray-500">
                    <i class="fas fa-eye">Show</i>
                </button>
                <p id="confirmError" class="text-red-500 text-sm mt-1"></p>
            </div>

            <!-- Submit Button -->
            <div class="text-center">
                <h:commandButton value="Submit" action="Login.jsp"
                                 styleClass="bg-blue-600 text-white px-6 py-2 rounded hover:bg-blue-700 transition duration-300 ease-in-out transform hover:scale-105"/>
            </div>

            <!-- Global Message -->
            <h:messages globalOnly="true" styleClass="text-red-500 text-sm mt-4 text-center"/>
        </h:form>
    </f:view>
</body>
</html>