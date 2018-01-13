// Some classes of a complex 3rd-party video conversion
// framework. We do not control that code, therefore can't
// simplify it.

class VideoFile
// ...

class OggCompressionCodec
// ...

class MPEG4CompressionCodec
// ...

class CodecFactory
// ...

class BitrateReader
// ...

class AudioMixer
// ...


// To defeat the complexity, we create a Facade class, which
// hides all of the framework's complexity behind a simple
// interface. It is a trade-off between functionality
// and simplicity.
class VideoConverter is
    method convert(filename, format):File is
        file = new VideoFile(filename)
        sourceCodec = new CodecFactory.extract(file)
        if (format == "mp4")
            distinationCodec = new MPEG4CompressionCodec()
        else
            distinationCodec = new OggCompressionCodec()
        buffer = BitrateReader.read(filename, sourceCodec)
        result = BitrateReader.convert(buffer, distinationCodec)
        result = (new AudioMixer()).fix(result)
        return new File(result)

// Application classes do not depend on a billion classes
// provided by the complex framework. Also, if you happen to
// decide to switch framework, you will only need to rewrite
// the facade class.
class Application is
    method main() is
        convertor = new VideoConverter()
        mp4 = convertor.convert("youtubevideo.ogg", "mp4")
        mp4.save()
