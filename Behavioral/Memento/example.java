// Originator class should have a special method, which
// captures originator's state inside a new memento object.
class Editor is
    private field text, curX, curY, selectionWidth

    method setText(text) is
        this.text = text

    method setCursor(x, y) is
        this.curX = curX
        this.curY = curY

    method setSelectionWidth(width) is
        this.selectionWidth = width

    method createSnapshot(): EditorState is
        // Memento is immutable object; that is why
        // originator passes its state to memento's
        // constructor parameters.
        return new Snapshot(this, text, curX, curY, selectionWidth)

// Memento stores past state of the editor.
class Snapshot is
    private field editor: Editor
    private field text, curX, curY, selectionWidth

    constructor Snapshot(editor, text, curX, curY, selectionWidth) is
        this.editor = editor
        this.text = text
        this.curX = curX
        this.curY = curY
        this.selectionWidth = selectionWidth

    // At some point, old editor state can be restored using
    // a memento object.
    method restore() is
        editor.setText(text)
        editor.setCursor(curX, curY)
        editor.setSelectionWidth(selectionWidth)

// Command object can act as a caretaker. In such case,
// command gets a memento just before it changes the
// originator's state. When undo is requested, it restores
// originator's state with a memento.
class Command is
    private field backup: Snapshot

    method makeBackup() is
        backup = editor.saveState()

    method undo() is
        if (backup != null)
            backup.restore()
    // ...
