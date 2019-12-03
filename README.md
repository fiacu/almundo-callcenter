# AlMundo - Ejercicio Java - CallCenter
Ejercicio de evaluaci�n AlMundo.

##Consigna
Existe un call center donde hay 3 tipos de empleados: operador, supervisor y director. El proceso de la atenci�n de una llamada telef�nica en primera instancia debe ser atendida por un operador, si no hay ninguno libre debe ser atendida por un supervisor, y de no haber tampoco supervisores libres debe ser atendida por un director.

##Requerimientos
* Debe existir una clase Dispatcher encargada de manejar las llamadas, y debe contener el m�todo dispatchCall para que las asigne a los empleados disponibles.
* El m�todo dispatchCall puede invocarse por varios hilos al mismo tiempo.
* La clase Dispatcher debe tener la capacidad de poder procesar 10 llamadas al mismo tiempo (de modo concurrente).
* Cada llamada puede durar un tiempo aleatorio entre 5 y 10 segundos.
* Debe tener un test unitario donde lleguen 10 llamadas.

##Extras/Plus
* Dar alguna soluci�n sobre qu� pasa con una llamada cuando no hay ning�n empleado libre.
* Dar alguna soluci�n sobre qu� pasa con una llamada cuando entran m�s de 10 llamadas concurrentes.
* Agregar los tests unitarios que se crean convenientes. 
* Agregar documentaci�n de c�digo

------------------------------
#Resoluci�n

Realizado por [Facundo Mor�n](https://www.linkedin.com/in/facundomoran/)

bla-bla

##Modelo

##Extras

##Pruebas

## Herramientas
-- Maven v3.5.4
-- JUnit v4.12