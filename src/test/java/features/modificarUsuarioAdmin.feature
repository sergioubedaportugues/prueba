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
Feature: Admin modifica datos usuario

  @tag1
  Scenario: Modificar nombre
    Given en la vista administrador "login","password", "nombre", "apellidos", "telefono", "dni" y "rol"
    When "nombre", "nuevoNombre" son distintos
    Then se ha modificado el dato

  @tag2
  Scenario: Modificar apellidos
    Given en la vista administrador "login","password", "nombre", "apellidos", "telefono", "dni" y "rol"
    When "apellidos", "nuevoApellidos" son distintos
    Then se ha modificado el dato
    
  @tag3
  Scenario: Modificar dni
    Given en la vista administrador "login","password", "nombre", "apellidos", "telefono", "dni" y "rol"
    When "dni", "nuevoDni" son distintos
    Then se ha modificado el dato
    
  @tag4
  Scenario: Modificar telefono
    Given en la vista administrador "login","password", "nombre", "apellidos", "telefono", "dni" y "rol"
    When "telefono", "nuevoTelefono" son distintos
    Then se ha modificado el dato
