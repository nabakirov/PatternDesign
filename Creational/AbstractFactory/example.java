// This pattern assumes that you have several families of
// products, structured into separate class hierarchies
// (Button/Checkbox). All products of the same family must
// follow the common interface.
interface Button is
    method paint()

// All products families have the same
// varieties (macOS/Windows).
class WinButton implements Button is
    method paint() is
        Render a button in Windows style

class MacButton implements Button is
    method paint() is
        Render a button in macOS style


interface Checkbox is
    method paint()

class WinCheckbox implements Checkbox is
    method paint() is
        Render a checkbox in Windows style

class MacCheckbox implements Checkbox is
    method paint() is
        Render a checkbox in macOS style


// Abstract factory knows about all (abstract)
// product types.
interface GUIFactory is
    method createButton():Button
    method createCheckbox():Checkbox


// Each concrete factory extends basic factory and
// responsible for creating products of a single variety.
class WinFactory implements GUIFactory is
    method createButton():Button is
        return new WinButton()
    method createCheckbox():Checkbox is
        return new WinCheckbox()

// Although concrete factories create the concrete products,
// they still return them with the abstract type. This fact
// makes factories interchangeable.
class MacFactory implements GUIFactory is
    method createButton():Button is
        return new MacButton()
    method createCheckbox():Checkbox is
        return new MacCheckbox()


// Factory users do not care which concrete factory they use
// since they work with// factories and products through
// abstract interfaces.
class Application is
    private field button: Button
    constructor Application(factory: GUIFactory) is
        this.factory = factory
    method createUI() is
        this.button = factory.createButton()
    method paint() is
        button.paint()


// Application picks the factory type and creates it in run
// time (usually at initialization stage), depending on the
// configuration or environment variables.
class ApplicationConfigurator is
    method main() is
        Read the configuration file
        if (config.OS == "Windows") then
            Construct a WinFactory
            Construct an Application with WinFactory
        else
            Construct an MacFactory
            Construct an Application with MacFactory