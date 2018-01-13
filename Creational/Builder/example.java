// Строитель может создавать различные продукты, используя
// один и тот же процесс строительства.
class Car is
    Can have GPS, trip computer and various numbers of seats.
    Can be a city car, a sports car, or a cabriolet.

class Manual is
    Textual representation of a car.


// Интерфейс строителя объявляет все возможные этапы и шаги
// конфигурации продукта.
interface Builder is
    method reset()
    method setSeats(...)
    method setEngine(...)
    method setTripComputer(...)
    method setGPS(...)

// Все конкретные строители реализуют общий
// интерфейс по-своему.
class CarBuilder implements Builder is
    private field car:Car
    method reset()
        Put a new Car instance into the "car" field.
    method setSeats(...) is
        Set the number of seats in car.
    method setEngine(...) is
        Install a given engine.
    method setTripComputer(...) is
        Install a trip computer.
    method setGPS(...) is
        Install a global positioning system.
    method getResult(): Car is
        Return the current car object.

// В отличие от других создающих паттернов, строители могут
// создавать совершенно разные продукты, не имеющие
// общего интерфейса.
class CarManualBuilder implements Builder is
    private field manual:Manual
    method reset()
        Put a new Manual instance into the "manual" field.
    method setSeats(...) is
        Document car seats features.
    method setEngine(...) is
        Add an engine instruction.
    method setTripComputer(...) is
        Add a trip computer instruction.
    method setGPS(...) is
        Add GPS instruction.
    method getResult(): Manual is
        Return the current manual object.


// Директор знает в какой последовательности заставлять
// работать строителя. Он работает с ним через общий
// интерфейс строителя. Из-за этого, он может не знать какой
// конкретно продукт сейчас строится.
class Director is
    method constructSportsCar(builder: Builder) is
        builder.reset()
        builder.setSeats(2)
        builder.setEngine(new SportEngine())
        builder.setTripComputer(true)
        builder.setGPS(true)


// Директор получает объект конкретного строителя от клиента
// (приложения). Приложение само знает какой строитель
// использовать, чтобы получить нужный продукт.
class Application is
    method makeCar is
        director = new Director()

        CarBuilder builder = new CarBuilder()
        director.constructSportsCar(builder)
        Car car = builder.getResult()

        CarManualBuilder builder = new CarManualBuilder()
        director.constructSportsCar(builder)

        // Готовый продукт возвращает строитель, так как
        // директор чаще всего не знает и не зависит от
        // конкретных классов строителей и продуктов.
        Manual manual = builder.getResult()
 Применимость

 Когда вы хотите избавиться от «телескопического конструктора».

 Допустим, у вас есть один конструктор с десятью опциональными параметрами. Его неудобно вызывать, поэтому вы создали ещё десять конструкторов с меньшим количеством параметров. Всё что они делают — это переадресуют вызов к главному конструктору, подавая какие-то значения по умолчанию в качестве опциональных параметров.

class Pizza {
    Pizza(int size) { ... }        
    Pizza(int size, boolean cheese) { ... }    
    Pizza(int size, boolean cheese, boolean pepperoni) { ... }    
    // ...

