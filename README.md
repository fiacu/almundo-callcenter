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

Para resolver el ejercicios implemente un modelo simple de 4 entidades principales que representan el contexto de un call center.

La clase Dispacher se encargar� de gestionar las llamadas, tanto la asignaci�n como el control de la misma.

Las llamadas (Call) tienen un estado en funci�n de la acci�n que se realice sobre esta.

La clase CallCenter contiene la lista de empleados disponibles, los cuales se subscriben a una lista que es cosumidad por el dispatcher.

Cuando un empleado toma una llamada se desuscribe, al finalizar la llamada vuelve a subscribirse.

##Modelo

###Estados de una llamada
![Estados de una llamada](callState.png)

###Dominio
![Modelo](domain.png)

##Extras
1) No hay empleados libre
   La clase dispatcher es la encargada de controlar si existen empleados disponibles. Como se ejecuta en hilos independientes, en caso de no encontrar un empleado disponible de ning�n tipo el hilo queda en espera.

   Se cambia el estado de la tarea a Waiting, simulando una acci�n sobre la llamada que notifique al cliente que se encuentra en esa situacion.

   A medida que algun empleado se subscriba, se notifica al primer hilo que esta en espera y continua su ejecuci�n.

2) Se supera la cantidad de llamadas y es mayor a 10.
   Para este caso, supongo que el dispatcher no tiene capacidad de poner la llamada en espera, por lo tanto finaliza la misma (la rechaza). Esta accion rechaza la llamada.

Este control esta a cargo del dispatcher. La cantidad de llamada contabiliza en simultaneo las que estan en ejecuci�n y las que estan en espera.

##Ejecucion de pruebas
-- **ConcurrentTest.testTenConcurrentCalls()** : prueba que la soluci�n soporta 10 llamadas en simultaneo. 
-- **ConcurrentTest.testWaitingEmployee()** : prueba que cuando no hay empleados disponibles y una call debe aguardar a que otro empleado se libere.
-- **ConcurrentTest.testRejectedCalls()** : prueba que si hay mas de 10 llamadas, las siguientes se cancelan.

Ejecutar `$mvn clean install` para instalar el proyecto.

Ejecutar `$mvn test` para correr las pruebas unitarias.

## Herramientas
-- Maven v3.5.4
-- JUnit v4.12