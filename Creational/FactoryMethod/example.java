// The Factory Method pattern is applicable only when there
// is a products hierarchy.
interface Button is
    method render()
    method onClick(f)

class WindowsButton implements Button is
    method render(a, b) is
        Create and render a Windows looking button.
    method onClick(f) is
        Bind a native OS click event.

class HTMLButton implements Button is
    method render(a, b) is
        Return an HTML representation of a button.
    method onClick(f) is
        Bind a web browser click event.


// Base factory class. Note that the "factory" is merely a
// role for the class. It should have some core business
// logic which needs different products to be created.
class Dialog is
    method renderWindow() is
        Render other window controls.

        Button okButton = createButton()
        okButton.onClick(closeDialog)
        okButton.render()

    // Therefore we extract all product creation code to a
    // special factory method.
    abstract method createButton()


// Concrete factories extend that method to produce
// different kinds of products.
class WindowsDialog extends Dialog is
    method createButton() is
        return new WindowsButton()

class WebDialog extends Dialog is
    method createButton() is
        return new HTMLButton()


class ClientApplication is
    field dialog: Dialog

    // Application picks a factory type depending on
    // configuration or environment.
    method configure() is
        if (we are in windows environment) then
            dialog = new WindowsDialog()

        if (we are in web environment) then
            dialog = new WebDialog()

    // The client code should work with factories and
    // products through their abstract interfaces. This way
    // it will remain functional even if you add new product
    // types to the program.
    method main() is
        dialog.initialize()
        dialog.render()