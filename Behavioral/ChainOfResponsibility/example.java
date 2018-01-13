// Handler interface.
interface ComponentWithContextualHelp is
    method showHelp() is


// Base class for simple components.
abstract class Component implements ContextualHelp is
    field tooltipText: string

    // Container, which contains component, severs as a
    // following object in chain.
    protected field container: Container

    // Component shows tooltip if there is a help text
    // assigned to it. Otherwise it forwards the call to the
    // container if it exists.
    method showHelp() is
        if (tooltipText != null)
            Show tooltip.
        else
            container.showHelp()


// Containers can contain both simple components and other
// container as children. The chain relations are
// established here. The class inherits showHelp behavior
// from its parent.
abstract class Container extends Component is
    protected field children: array of Component

    method add(child) is
        children.add(child)
        child.container = this


// Primitive components may be fine with default
// help implementation...
class Button extends Component is
    // ...

// But complex components may override the default
// implementation. If a help can not be provided in a new
// way, the component can always call the base
// implementation (see Component class).
class Panel extends Container is
    field modalHelpText: string

    method showHelp() is
        if (modalHelpText != null)
            Show modal window with a help text.
        else
            super.showHelp()

// ...same as above...
class Dialog extends Container is
    field wikiPageURL: string

    method showHelp() is
        if (wikiPageURL != null)
            Open the wiki help page.
        else
            super.showHelp()


// Client code.
class Application is
    // Each application configures the chain differently.
    method createUI() is
        dialog = new Dialog("Budget Reports")
        dialog.wikiPage = "http://..."
        panel = new Panel(0, 0, 400, 800)
        panel.modalHelpText = "This panel does..."
        ok = new Button(250, 760, 50, 20, "OK")
        ok.tooltipText = "This is a OK button that..."
        cancel = new Button(320, 760, 50, 20, "Cancel")
        // ...
        panel.add(ok)
        panel.add(cancel)
        dialog.add(panel)

    // Imagine what happens here.
    method onF1KeyPress() is
        component = this.getComponentAtMouseCoords()
        component.showHelp()
