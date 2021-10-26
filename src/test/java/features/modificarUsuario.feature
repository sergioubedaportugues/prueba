#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@tag
Feature: Paciente modifica sus datos

  @tag1
  Scenario: Paciente modificar telefono
    Given en la vista paciente "login","password", "nombre", "apellidos", "telefono", "dni" y "rol"
    When "telefono", "nuevoTelefono" son distintos
    Then se ha modificado el dato

  @tag2
  Scenario: Paciente modificar contrasena
    Given en la vista paciente "login","password", "nombre", "apellidos", "telefono", "dni" y "rol"
    When "contrasena", "nuevaContrasena" son distintos
    Then se ha modificado el dato
