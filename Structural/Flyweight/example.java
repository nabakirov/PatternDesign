// This Flyweight class contains a portion of a state of a
// tree. These field store values that hardly unique for
// each particular tree. For instance, you will not find
// here the tree coordinates. But the texture and colors
// shared between many trees are here. Since this data is
// usually BIG, you would waste a lot of memory by keeping
// it in the each tree object. Instead, we can extract
// texture, color and other repeating data into a separate
// object, which can be referenced from lots of individual
// tree objects.
class TreeType is
    field name
    field color
    field texture
    constructor TreeType(name, color, texture) { ... }
    method draw(canvas, x, y) is
        Create a bitmap from type, color and texture.
        Draw bitmap on canvas at X and Y.

// Flyweight factory decides whether to re-use existing
// flyweight or to create a new object.
class TreeFactory is
    static field treeTypes: collection of tree types
    static method getTreeType(name, color, texture) is
        type = treeTypes.find(name, color, texture)
        if (type == null)
            type = new TreeType(name, color, texture)
            treeTypes.add(type)
        return type

// Contextual object contains extrinsic part of tree state.
// Application can create billions of these since they are
// pretty small: just two integer coordinates and one
// reference field.
class Tree is
    field x,y
    field type: TreeType
    constructor Tree(x, y, type) { ... }
    method draw(canvas) is
        type.draw(canvas, this.x, this.y)

// The Tree and the Forest classes are the Flyweight's
// clients. You can merge them if you do not plan to develop
// the Tree class any further.
class Forest is
    field trees: collection of Trees

    method plantTree(x, y, name, color, texture) is
        type = TreeFactory.getTreeType(name, color, texture)
        tree = new Tree(x, y, type)
        trees.add(tree)

    method draw(canvas) is
        foreach tree in trees
            tree.draw(canvas)