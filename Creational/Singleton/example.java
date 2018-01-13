class Database is
    private field instance: Database

    static method getInstance() is
        if (this.instance == null) then
            acquireThreadLock() and then
                // Ensure that instance has not yet been
                // initialized by other thread while this
                // one has been waiting for the
                // lock release.
                if (this.instance == null) then
                    this.instance = new Database()
        return this.instance

    private constructor Database() is
        // Some initialization code, such as the actual
        // connection to a database server.
        // ...

    public method query(sql) is
        // All database queries of an app will go through
        // this methods. Therefore, you can place a
        // throttling or caching logic here.
        // ...

class Application is
    method main() is
        Database foo = Database.getInstance()
        foo.query("SELECT ...")
        // ...
        Database bar = Database.getInstance()
        bar.query("SELECT ...")
        // The variable `bar` will contain the same object
        // as the variable `foo`.

