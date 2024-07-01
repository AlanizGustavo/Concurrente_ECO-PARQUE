# Programación Concurrente

## Trabajo Práctico Obligatorio FINAL

### PARQUE “ECO-PCS”

Se desea simular el funcionamiento del parque ecológico “ECO-PCS”, un acuario
natural, desde que los visitantes llegan al parque hasta que se van.
Al parque se puede acceder en forma particular o por tour, en el caso del tour, se
trasladan a través de colectivos folklóricos con una capacidad no mayor a 25 personas,
que llegan a un estacionamiento destinado para tal fin. Al momento de arribar al parque
se le entregarán pulseras a los visitantes que le permitirán el acceso al parque.
El ingreso al parque está indicado a través del paso de k molinetes. Una vez ingresado,
el visitante puede optar por ir al shop o disfrutar de las actividades del parque.
En el shop se pueden adquirir suvenires de distinta clase, los cuales se pueden abonar en
una de las dos cajas disponibles.
Dentro de las actividades principales, se encuentran:

- Nado con delfines: para realizarla se dispone de 4 piletas. Es necesario que el
  visitante elija un horario para realizar la actividad entre los horarios preestablecidos
  de la misma. Se conforman grupos de 10 personas por pileta. En cada pileta
  nadaran dos delfines y la actividad dura aproximadamente 45 minutos. La política
  del parque es que en cada horario puede haber solo 1 grupo incompleto (de las 4
  piletas)
- Disfruta de Snorkel ilimitado: existe la posibilidad de realizar snorkel en una
  laguna, para lo cual es necesario adquirir previamente el equipo de snorkel,
  salvavidas y patas de ranas, que deberán ser devueltos al momento de finalizar la
  actividad. En el ingreso a la actividad hay un stand donde dos asistentes entregan el
  equipo mencionado. La única limitación en cuanto a capacidad es dada por la
  cantidad de equipos completos (snorkel, salvavidas y patas de rana) existentes.
- Restaurante: en el pago del acceso al Parque se encuentra incluido el almuerzo y la
  merienda. Existen tres restaurantes, pero solamente se puede consumir un almuerzo
  y una merienda en cualquiera de ellos. Puede tomar el almuerzo en un restaurante y
  la merienda en otro. Los restaurantes tienen capacidad limitada. Las personas son
  atendidas en orden de llegada. Los restaurantes tienen habilitada una cola de espera.
- Faro-Mirador con vista a 40 m de altura y descenso en tobogán: Admira desde
  lo alto todo el esplendor de una maravilla natural y desciende en tobogán hasta una
  pileta. Para acceder al tobogán es necesario subir por una escalera caracol, que tiene
  capacidad para n personas. Al llegar a la cima nos encontraremos con dos toboganes
  para descender, la elección del tobogán es realizada por un administrador de cola
  que indica que persona de la fila va a un tobogán y cuál va al otro. Es importante
  destacar que una persona no se tira por el tobogán hasta que la anterior no haya
  llegado a la pileta, es decir, sobre cada tobogán siempre hay a lo sumo una persona.
- Carrera de gomones por el río: esta actividad permite que los visitantes bajen por
  el rio, que se encuentra rodeado de manglares, compitiendo entre ellos. Para ello es
  necesario llegar hasta el inicio de la actividad a través de bicicletas que se prestan
  en un stand de bicicletas, o a través de un tren interno que tiene una capacidad de 15
  personas como máximo. Al llegar al inicio del recorrido cada persona dispondrá de
  un bolso con llave, en donde podrá guardar todas las pertenencias que no quiera
  mojar. Los bolsos están identificados con un número al igual que la llave, los bolsos
  serán llevados en una camioneta, hasta el final del recorrido en donde podrán ser
  retirados por el visitante. Para bajar se utilizan gomones, individuales o con
  capacidad para 2 personas. La cantidad de gomones de cada tipo es limitada. Para
  habilitar una largada es necesario que haya h gomones listos para salir, no importa
  el tipo.
  El complejo se encuentra abierto para el ingreso de 09:00 a 17:00hs. Considere que las
  actividades cierran a las 18.00 hrs.

Debe resolverse utilizando los mecanismos de sincronización vistos en la materia y
provistos por el lenguaje: semáforos, monitores y locks (obligatoriamente), y al menos,
uno de los siguientes: CyclicBarrier, CountDownLatch, Exchanger., implementaciones
de BlockingQueue, etc.
