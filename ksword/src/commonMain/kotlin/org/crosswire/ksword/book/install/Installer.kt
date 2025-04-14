package org.crosswire.ksword.book.install

import org.crosswire.ksword.book.BookList

/**
 * An interface that allows us to download from a specific source of Bible data.
 */
interface Installer : BookList {
//    /**
//     * Accessor for the URI
//     *
//     * @return the source URI
//     */
//    val installerDefinition: String
//
//    /**
//     * @param book
//     * The book meta-data to get a URI from.
//     * @return the remote URI for the BookMetaData
//     */
//    fun toRemoteURI(book: Book): String
//    /**
//     * Get a Book matching the name from the local cache. Null if none is found.
//     *
//     * @param book the book name
//     * @return the instantiated book
//     */
//    fun getBook(book: String): Book
//
//    /**
//     * Return true if the book is not installed or there is a newer version to
//     * install.
//     *
//     * @param book
//     * The book meta-data to check on.
//     * @return whether there is a newer version to install
//     */
//    fun getSize(book: Book): Int
//
//    /**
//     * Return true if the book is not installed or there is a newer version to
//     * install.
//     *
//     * @param book
//     * The book meta-data to check on.
//     * @return whether there is a newer version to install
//     */
//    fun isNewer(book: Book): Boolean

    /**
     * Re-fetch a list of names from the remote source.
     * ***It would make sense
     * if the user was warned about the implications of this action. If the user
     * lives in a country that persecutes Christians then this action might give
     * the game away.**
     */
    suspend fun refreshBookListFromServer()

    /**
     * Load the list of books from the local cache. If not done already. Or from remote source if not cached.
     */
    suspend fun loadBookList()

//    /**
//     * Download and install a book locally. The name should be one from an index
//     * list retrieved from getIndex() or reloadIndex()
//     *
//     * @param book
//     * The book to install
//     */
//    fun install(book: Book)
//
//    /** remove the cached book list to clear memory
//     */
//    fun close()
}
