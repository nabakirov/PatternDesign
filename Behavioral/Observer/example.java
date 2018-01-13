// Base publisher class. It should include the subscription
// management code and notification methods.
class EventManager is
    private field listeners: hash map of event types and listeners

    method subscribe(eventType, listener) is
        listeners.add(eventType, listener)

    method unsubscribe(eventType, listener) is
        listeners.remove(eventType, listener)

    method notify(eventType, data) is
        foreach listener in listeners.of(eventType)
            listener.update(data)

// Concrete publisher, which contains real business logic
// interesting for some subscribers. We could derive this
// class from a base publisher, but that is not always
// possible in real life, since the concrete publisher might
// already have a different parent class. In this case, you
// can patch the subscription logic in with composition,
// just like we did it here.
class Editor is
    private field events: EventManager
    private field file: File

    constructor Editor() is
        events = new EventManager()

    // Business logic methods can notify subscribers about
    // the changes.
    method openFile(path) is
        this.file = new File(path)
        events.notify("open", file.name)

    method saveFile() is
        file.write()
        events.notify("save", file.name)
    // ...


// Common subscribers interface. By the way, modern
// programming languages allow to simplify this code and use
// functions as subscribers.
interface EventListener is
    method update(filename)

// List of concrete listeners. They react to publisher
// updates by doing some useful work.
class LoggingListener is
    private field log: File
    private field message

    constructor LoggingListener(log_filename, message) is
        this.log = new File(log_filename)
        this.message = message

    method update(filename) is
        log.write(replace('%s',filename,message))

class EmailAlertsListener is
    private field email: string

    constructor EmailAlertsListener(email, message) is
        this.email = email
        this.message = message

    method update(filename) is
        system.email(email, replace('%s',filename,message))


// Application can configure publishers and subscribers even
// in run time.
class Application is
    method config() is
        editor = new TextEditor()

        logger = new LoggingListener(
            "/path/to/log.txt",
            "Someone has opened file: %s");
        editor.events.subscribe("open", logger)

        emailAlers = new EmailAlertsListener(
            "admin@example.com",
            "Someone has changed the file: %s")
        editor.events.subscribe("save", emailAlers)
