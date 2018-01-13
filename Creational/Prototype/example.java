// Base prototype.
abstract class Shape is
    field X: int
    field Y: int
    field color: string

    // A fresh object is initialized with values from the
    // old object in the constructor.
    constructor Shape(source: Shape) is
        if (source != null) then
            this.X = source.X
            this.Y = source.Y
            this.color = source.color

    // Clone operation always returns one of the
    // Shape subclasses.
    abstract method clone(): Shape


// Concrete prototype. Cloning method creates a new object
// and passes itself to the constructor. Until constructor
// is finished, has a reference to a fresh clone. Therefore,
// nobody has access to a partly built clone. This helps to
// make the cloning result consistent.
class Rectangle extends Shape is
    field width: int
    field height: int

    constructor Rectangle(source: Rectangle) is
        // Parent constructor call is mandatory in order to
        // copy private fields defined in parent class.
        super(source)
        if (source != null) then
            this.width = source.width
            this.height = source.height

    method clone(): Shape is
        return new Rectangle(this)


class Circle extends Shape is
    field radius: int

    constructor Circle(source: Circle) is
        super(source)
        if (source != null) then
            this.radius = source.radius

    method clone(): Shape is
        return new Circle(this)


// Somewhere in client code.
class Application is
    field shapes: array of Shape

    constructor Application() is
        Circle circle = new Circle()
        circle.X = 10
        circle.Y = 20
        circle.radius = 15
        shapes.add(circle)

        Circle anotherCircle = circle.clone()
        shapes.add(anotherCircle)
        // anotherCircle is the exact copy of circle.

        Rectangle rectangle = new Rectangle()
        rectangle.width = 10
        rectangle.height = 20
        shapes.add(rectangle)

    method businessLogic() is
        // Prototype rocks because it allows producing a
        // copy of an object without knowing anything about
        // its type.
        Array shapesCopy = new Array of Shapes.

        // For instance, we do not know exact types of
        // elements in shapes array. All we know is that all
        // of them are Shapes. But thanks to the
        // polymorphism, when we call the `clone` method on
        // a shape, the program checks its real class and
        // runs the appropriate clone method, defined in
        // that class. That is why we get proper clones
        // instead of a set of simple Shape objects.
        foreach s in shapes do
            shapesCopy.add(s.clone())

        // The variable `shapesCopy` will contain exact
        // copies of the `shape` array's children.
