// The common interface for all components.
interface DataSource is
    method writeData(data)
    method readData():data

// The concrete component act as a base layer.
class FileDataSource implements DataSource is
    constructor FileDataSource(filename) { ... }

    method writeData(data) is
        Write data to file.

    method readData():data is
        Read data from file.

// The base decorator contains wrapping behavior.
class DataSourceDecorator implements DataSource is
    protected field wrappee: DataSource

    constructor DataSourceDecorator(source: DataSource) is
        wrappee = source

    method writeData(data) is
        wrappee.writeData(data)

    method readData():data is
        return wrappee.readData()

// Concrete decorators add to a result they get from a
// component they wrap.
class EncyptionDecorator extends DataSourceDecorator is
    method writeData(data) is
        Encrypt passed data.
        Pass encrypted data to wrappee's writeData method.

    method readData():data is
        Get encrypted data from wrappee's readData method.
        Decrypt and return the result.

// You can wrap objects in several layers of decorators.
class CompressionDecorator extends DataSourceDecorator is
    method writeData(data) is
        Compress passed data.
        Pass compressed data to wrappee's writeData method.

    method readData():data is
        Get compressed data from wrappee's readData method.
        Uncompress and return the result.


// Option 1. A simple example of decorator assembly.
class Application is
    method dumbUsageExample() is
        source = new FileDataSource('somefile.dat')
        source.writeData(salaryRecords)
        // The target file has been written with plain data.

        source = new CompressionDecorator(source)
        source.writeData(salaryRecords)
        // The file has been written with compressed data.

        source = new EncyptionDecorator(source)
        // The source variable is now containing this:
        // Encryption > Compression > FileDataSource
        source.writeData(salaryRecords)
        // The file has been written with compressed and
        // encrypted data.


// Option 2. The client code that uses an external data
// source. SalaryManager objects neither know nor care about
// data storage specifics. They work with pre-configured
// data source, received from the app configurator.
class SalaryManager is
    field source: DataSource

    constructor SalaryManager(source: DataSource) { ... }

    method load() is
        return source.readData()

    method save() is
        source.writeData(salaryRecords)
    // ...Other useful methods...


// The app is able to assemble different stacks of
// decorators at run time, depending on the configuration
// or environment.
class ApplicationConfigurator is
    method configurationExample() is
        source = new FileDataSource("salary.dat")
        if (enabledEncryption)
            source = new EncyptionDecorator(source)
        if (enabledCompression)
            source = new CompressionDecorator(source)

        logger = new SalaryLogger(source)
        salary = logger.load()
    // ...