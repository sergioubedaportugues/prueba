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
Feature: Administrador cambia datos centro

  @tag1
  Scenario: Modificar nombre
    Given los datos de "nombreCentro", "direccionCentro", "numeroVacunas" y "numeroSanitarios"
    When "nombreCentro", "nuevoNombreCentro" no coinciden
    Then se ha modificado la informacion del centro

  @tag2
  Scenario: Modificar direccion
    Given los datos de "nombreCentro", "direccionCentro", "numeroVacunas" y "numeroSanitarios"
    When "direccionCentro", "nuevaDireccionCentro" no coinciden
    Then se ha modificado la informacion del centro
    
  @tag3
  Scenario: Modificar numero de vacunas
    Given los datos de "nombreCentro", "direccionCentro", "numeroVacunas" y "numeroSanitarios"
    When "numeroVacunas", "numeroVacunas" no coinciden
    Then se ha modificado la informacion del centro

  @tag3
  Scenario: Modificar numero de sanitarios
    Given los datos de "nombreCentro", "direccionCentro", "numeroVacunas" y "numeroSanitarios"
    When "numeroSanitarios", "numeroSanitarios" no coinciden
    Then se ha modificado la informacion del centro