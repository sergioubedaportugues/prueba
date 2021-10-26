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
Feature: Iniciar sesion

  @tag1
  Scenario: Iniciar sesion satisfactoriamente Usuario
    Given acceso con "login" y "password" correctos 
    When los datos son correctos
    Then accedo a la pantalla principal

  @tag2
  Scenario: Acceder con contrasena erronea
    Given acceso con "login" y "password"
    When los datos son incorrectos
    Then se lanza una excepcion
