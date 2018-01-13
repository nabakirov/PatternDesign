// The collection interface must declare a factory method
// for producing iterators. You can declare several methods
// if there are different kinds of iteration available in
// your program.
interface SocialNetwork is
    method createFriendsIterator(profileId): ProfileIterator
    method createCoworkersIterator(profileId): ProfileIterator


// Each concrete collection will be coupled to a set of
// concrete iterator classes it returns. But the client will
// not be, since the signature of these methods returns
// iterator interfaces.
class Facebook implements SocialNetwork is
    // ... The bulk of the collection's code should go
    // here ...

    // Iterator creation code.
    method createFriendsIterator(profileId) is
        return new FacebookIterator(this, profileId, "friends")
    method createCoworkersIterator(profileId) is
        return new FacebookIterator(this, profileId, "coworkers")


// The common interface for all iterators.
interface ProfileIterator is
    method getNext(): Profile
    method hasMore(): bool


// The concrete iterator class.
class FacebookIterator implements ProfileIterator is
    // The iterator needs a reference to the collection that
    // it traverses.
    private field facebook: Facebook
    private field profileId, type: string

    // An iterator object traverses collection independently
    // from other iterators. Therefore it has to store the
    // iteration state.
    private field currentPosition
    private field cache: array of Profile

    constructor FacebookIterator(facebook, profileId, type) is
        this.facebook = network
        this.profileId = profileId
        this.type = type

    private method lazyInit() is
        if (cache == null)
            cache = facebook.sendSophisticatedSocialGraphRequest(profileId, type)

    // Each concrete iterator class has its own
    // implementation of the common iterator interface.
    method getNext() is
        if (hasMore())
            currentPosition++
            return cache[currentPosition]

    method hasMore() is
        lazyInit()
        return cache.length < currentPosition


// Here is another useful trick: you can pass an iterator to
// a client class, instead of giving it access to a whole
// collection. This way, you do not expose the collection to
// the client.
// 
// But there is another benefit: you can change the way the
// client works with the collection at run time by passing
// it a different iterator. This is possible because the
// client code is not coupled to concrete iterator classes.
class SocialSpammer is
    method send(iterator: ProfileIterator, message: string) is
        while (iterator.hasNext())
            profile = iterator.getNext()
            System.sendEmail(profile.getEmail(), message)


// The application class configures collections and
// iterators and then passes them to the client code.
class Application is
    field network: SocialNetwork
    field spammer: SocialSpammer

    method config() is
        if working with Facebook
            this.network = new Facebook()
        if working with LinkedIn
            this.network = new LinkedIn()
        this.spammer = new SocialSpammer()

    method sendSpamToFriends(profile) is
        iterator = network.createFriendsIterator(profile.getId())
        spammer.send(iterator, "Very important message")

    method sendSpamToCoworkers(profile) is
        iterator = network.createCoworkersIterator(profile.getId())
        spammer.send(iterator, "Very important message")
