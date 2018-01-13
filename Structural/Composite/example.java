// Common interface for all components.
interface Graphic is
    method move(x, y)
    method draw()

// Simple component.
class Dot implements Graphic is
    field x, y

    constructor Dot(x, y) { ... }

    method move(x, y) is
        this.x += x, this.y += y

    method draw() is
        Draw a dot at X and Y.

// Components can extend other components.
class Circle extends Dot is
    field radius

    constructor Circle(x, y, radius) { ... }

    method draw() is
        Draw a circle at X and Y and radius R.

// The composite component includes methods to add/remove
// child components. It attempts to delegate all operations
// defined in the component interface to its
// child components.
class CompoundGraphic implements Graphic is
    field children: array of Graphic

    method add(child: Graphic) is
        Add a child to the array of children.

    method remove(child: Graphic) is
        Remove a child from the array of children.

    method move(x, y) is
        For each child: child.move(x, y)

    method draw() is
        Go over all children and calculate their bounds.
        Draw a dotted box using calculated values.
        Draw each child.


// Application can operate uniformly with a specific
// components or with a whole group of components.
class ImageEditor is

    method load() is
        all = new CompoundGraphic()
        all.add(new Dot(1, 2))
        all.add(new Circle(5, 3, 10))
        // ...

    // Combine selected components into one
    // complex component.
    method groupSelected(components: array of Graphic) is
        group = new CompoundGraphic()
        group.add(components)
        all.remove(components)
        all.add(group)
        // All components will be drawn.
        all.draw()