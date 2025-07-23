<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core" %>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html" %>

<!DOCTYPE html>
<html>
<f:view>
  <head>
    <meta charset="UTF-8"/>
    <title>HealthSure Portal</title>
    <!-- Tailwind CSS -->
    <link
      href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css"
      rel="stylesheet"/>
  </head>

  <body class="bg-gradient-to-br from-purple-100 via-pink-100 to-yellow-100 min-h-screen flex flex-col">
    <!-- Header -->
    <header class="bg-white shadow-md">
      <div class="container mx-auto px-6 py-4 flex items-center justify-between">
        <h:outputLink value="HomePage" styleClass="text-2xl font-bold text-purple-700">
          HealthSure
        </h:outputLink>
        <nav class="hidden md:flex space-x-6">
          <h:form prependId="false" styleClass="flex space-x-6">
            <h:commandLink value="Home"      action="HomePage"   styleClass="text-gray-700 hover:text-purple-700 transition"/>
            <h:commandLink value="Sign Up"   action="SignUp"     styleClass="text-gray-700 hover:text-purple-700 transition"/>
            <h:commandLink value="Login"     action="Login"      styleClass="text-gray-700 hover:text-purple-700 transition"/>
            <h:commandLink value="Doctors"   action="AddDoctors" styleClass="text-gray-700 hover:text-purple-700 transition"/>
            <h:commandLink value="Contact Us"action="Contact"    styleClass="text-gray-700 hover:text-purple-700 transition"/>
          </h:form>
        </nav>
      </div>
      <!-- Mobile menu -->
      <div id="mobile-menu" class="hidden bg-white border-t border-gray-200">
        <h:form prependId="false" styleClass="flex flex-col px-6 py-4 space-y-3">
          <h:commandLink value="Home"       action="HomePage"   styleClass="text-gray-700 hover:text-purple-700 transition"/>
          <h:commandLink value="Sign Up"    action="SignUp"     styleClass="text-gray-700 hover:text-purple-700 transition"/>
          <h:commandLink value="Login"      action="Login"      styleClass="text-gray-700 hover:text-purple-700 transition"/>
          <h:commandLink value="Doctors"    action="AddDoctors" styleClass="text-gray-700 hover:text-purple-700 transition"/>
          <h:commandLink value="Contact Us" action="Contact"    styleClass="text-gray-700 hover:text-purple-700 transition"/>
        </h:form>
      </div>
    </header>

    <!-- Main Content -->
    <main class="flex-1 container mx-auto px-6 flex items-center justify-center">
      <div class="text-center space-y-6">
        <h1 class="text-4xl font-extrabold text-purple-700">Your Health, Our Priority</h1>
        <p class="text-gray-700 max-w-lg mx-auto">
          Explore the HealthSure portal to manage your profile, book appointments,
          and stay updated with the latest health information.
        </p>
        <div class="space-x-4">
          <h:commandLink value="Get Started"
                         action="SignUp"
                         styleClass="inline-block bg-purple-600 hover:bg-purple-700
                                     text-white font-semibold py-2 px-6 rounded-lg transition"/>
          <h:commandLink value="Learn More"
                         action="Contact"
                         styleClass="inline-block border border-purple-600
                                     text-purple-600 hover:bg-purple-50 font-semibold
                                     py-2 px-6 rounded-lg transition"/>
        </div>
      </div>
    </main>

    <!-- Footer -->
    <footer class="bg-white shadow-inner py-4">
      <div class="text-center text-gray-600 text-sm">
        © 2025 HealthSure. All rights reserved.
      </div>
    </footer>

    <!-- Mobile menu toggle script -->
    <script>
      const btn  = document.getElementById('mobile-menu-button');
      const menu = document.getElementById('mobile-menu');
      btn.addEventListener('click', () => menu.classList.toggle('hidden'));
    </script>
  </body>
</f:view>
</html>