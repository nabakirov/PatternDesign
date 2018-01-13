// The interface of a remote service.
interface ThirdPartyYoutubeLib is
    method listVideos()
    method getVideoInfo(id)
    method downloadVideo(id)

// Concrete implementation of a service connector. Methods
// of this class can request various info from youtube.
// Request speed depends on a user's internet connection as
// wells as Youtube's. Application will slow down if a lot
// of requests will be fired at the same time, even if they
// are requesting the same info.
class ThirdPartyYoutubeClass is
    method listVideos() is
        Send API request to Youtube.

    method getVideoInfo(id) is
        Get a meta information about some video.

    method downloadVideo(id) is
        Download video file from Youtube.

// On the other hand, to save some bandwidth, we can cache
// request results and keep them for some time. But it may
// be impossible to put such code directly to the service
// class. For example, it could have been provided by third
// party library or/and defined as `final`. That is why we
// put the caching code to a new proxy class which
// implements the same interface as a service class. It is
// going to delegate to the service object only when the
// real requests have to be sent.
class CachedYoutubeClass implements ThirdPartyYoutubeLib is
    private field service: ThirdPartyYoutubeClass
    private field listCache, videoCache
    field needReset

    constructor CachedYoutubeClass(service: ThirdPartyYoutubeLib) is
        this.service = service

    method listVideos() is
        if (listCache == null || needReset)
            listCache = service.listVideos()
        return listCache

    method getVideoInfo(id) is
        if (videoCache == null || needReset)
            videoCache = service.getVideoInfo(id)
        return videoCache

    method downloadVideo(id) is
        if (!downloadExists(id) || needReset)
            service.downloadVideo(id)

// The GUI class, which used to work with a service object
// stays unchanged. But only as long as it works with the
// service object through an interface. We can safely pass
// here a proxy object instead of a real service object
// since both of them implement the same interface.
class YoutubeManager is
    protected field service: ThirdPartyYoutubeLib

    constructor YoutubeManager(service: ThirdPartyYoutubeLib) is
        this.service = service

    method renderVideoPage() is
        info = service.getVideoInfo()
        Render the video page.

    method renderListPanel() is
        list = service.listVideos()
        Render the list of video thumbnails.

    method reactOnUserInput() is
        renderVideoPage()
        renderListPanel()

// Application can configure proxies on the fly.
class Application is
    method init() is
        youtubeService = new ThirdPartyYoutubeClass()
        youtubeProxy = new CachedYoutubeClass(youtubeService)
        manager = new YoutubeManager(youtubeProxy)
        manager.reactOnUserInput()