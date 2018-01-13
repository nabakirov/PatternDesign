// Classes with compatible interfaces: RoundHole
// and RoundPeg.
class RoundHole is
    constructor RoundHole(radius) { ... }

    method getRadius is
        Return the radious of the hole.

    method fits(peg: RoundPeg) is
        return this.getRadius() >= peg.radius()

class RoundPeg is
    constructor RoundPeg(radius) { ... }

    method getRadius() is
        Return the peg radius.


// Obsolete incompatible class: SquarePeg.
class SquarePeg is
    constructor SquarePeg(width) { ... }

    method getWidth() is
        Return the square peg width.


// Adapter allows fitting square pegs into round holes.
class SquarePegAdapter extends RoundPeg is
    private field peg: SquarePeg

    constructor SquarePegAdapter(peg: SquarePeg) is
        this.peg = peg

    method getRadius() is
        return Math.sqrt(Math.pow((peg.getWidth()/2), 2) * 2)


// Somewhere in client code.
hole = new RoundHole(5)
rpeg = new RoundPeg(5)
hole.fits(rpeg) // true

small_sqpeg = new SquarePeg(2)
large_sqpeg = new SquarePeg(5)
hole.fits(small_sqpeg) // this will not compile (incompatible types)

small_sqpeg_adapter = new SquarePegAdapter(small_sqpeg)
large_sqpeg_adapter = new SquarePegAdapter(large_sqpeg)
hole.fits(small_sqpeg_adapter) // true
hole.fits(large_sqpeg_adapter) // false