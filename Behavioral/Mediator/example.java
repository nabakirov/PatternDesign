// The mediator interface.
interface Mediator is
    method notify(sender: Component, event: string)


// The concrete mediator class. All chaotic communications
// between concrete components were extracted to the
// mediator class.
class AuthenticationDialog implements Mediator is
    private field title: string
    private field loginOrRegister: Checkbox
    private field loginUsername, loginPassword: Textbox
    private field registrationUsername, registrationPassword
    private field registrationEmail: Textbox
    private field ok, cancel: Button

    constructor AuthenticationDialog() is
        Create all component objects.
        Link meditor with components via constructor params.

    // When something happens with a component, it notifies
    // the mediator. Upon receiving a notification, the
    // mediator may do something on its own or pass the
    // request to another component.
    method notify(sender, event) is
        if (sender == loginOrRegister and event == "check")
            if (loginOrRegister.checked)
                title = "Log in"
                Show login components
                Hide registration components.
            else
                title = "Register"
                Show registration components.
                Hide login components
        if (sender == ok and event == "click")
            if (loginOrRegister.checked)
                Try to find user using login credentials.
                if (!found)
                    Show errors over login fields.
            else
                Create account using registration fields.
                Log user in.
        // ...


// Components communicate with a mediator using the mediator
// interface. Thanks to that, you can use the same
// components in other contexts by linking them with a
// different mediator object.
class Component is
    field dialog: Mediator

    constructor Component(dialog) is
        this.dialog = dialog

    method click() is
        dialog.notify(this, "click")

    method keypress() is
        dialog.notify(this, "keypress")

// Concrete components do not talk to each other. They have
// only one communication channel, which is sending
// notifications to the mediator.
class Button extends Component is
    // ...

class Textbox extends Component is
    // ...

class Checkbox extends Component is
    method check() is
        dialog.notify(this, "check")
    // ...

