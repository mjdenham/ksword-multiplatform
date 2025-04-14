package org.crosswire.ksword.book

/**
 * There are several lists of Books, the most important being the installed
 * Books, however there may be others like the available books or books from a
 * specific driver. This interface provides a common method of accessing all of
 * them.
 */
interface BookList {
    /**
     * Get a list of all the Books of all types.
     *
     * @return the desired list of books
     */
    suspend fun getBooks(): List<Book>

//    /**
//     * Get a filtered list of all the Books.
//     *
//     * @param filter the filter to apply to the list of books
//     * @return the desired list of books
//     * @see BookFilters
//     */
//    fun getBooks(filter: BookFilter?): List<Book?>?
//
//    /**
//     * Add a BibleListener from our list of listeners
//     *
//     * @param li interested listener
//     */
//    fun addBooksListener(li: BooksListener?)
//
//    /**
//     * Remove a BibleListener to our list of listeners
//     *
//     * @param li disinterested listener
//     */
//    fun removeBooksListener(li: BooksListener?)
}
