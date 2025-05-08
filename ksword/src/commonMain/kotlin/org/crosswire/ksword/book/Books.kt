/**
 * Distribution License:
 * KSword is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License, version 2.1 or later
 * as published by the Free Software Foundation. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 */
package org.crosswire.ksword.book

import org.crosswire.common.util.Log
import org.crosswire.ksword.book.sword.SwordBookDriver

/**
 * The Books class (along with Book) is the central point of contact between the
 * rest of the world and this set of packages.
 */
object Books : BookList {

    override suspend fun getBooks(): List<Book> {
        return books.toList()
    }

//    /* (non-Javadoc)
//     * @see org.crosswire.jsword.book.BookList#getBooks(org.crosswire.jsword.book.BookFilter)
//     */
//    @Synchronized
//    override fun getBooks(filter: BookFilter?): List<Book> {
//        return CollectionUtil.createList(BookFilterIterator(books, filter))
//    }

    /**
     * Search for the book by initials and name.
     * Looks for exact matches first, then searches case insensitive.
     * In all cases the whole initials or the whole name has to match.
     *
     * @param name The initials or name of the book to find
     * @return the book or null
     */
    fun getBook(name: String): Book? {
        bookByInitials[name]?.let {
            return it
        }

        bookByName[name]?.let {
            return it
        }

        // Check for case-insensitive initial and name matches
        for (b in books) {
            if (name.equals(b.initials, ignoreCase = true) || name.equals(b.name, ignoreCase = true)) {
                return b
            }
        }

        return null
    }

    /**
     * Add a Book to the current list of Books. This method should only be
     * called by BibleDrivers, it is not a method for general consumption.
     *
     * @param book the book to add to this book list
     */
    fun addBook(book: Book) {
        if (books.add(book)) {
            bookByInitials[book.initials] = book
            bookByName[book.name] = book
//            fireBooksChanged(instance, book, true)
        }
    }

    /**
     * Remove a Book from the current list of Books. This method should only be
     * called by BibleDrivers, it is not a method for general consumption,
     * because this method does not delete the installed book module files.
     *
     * @param book the book to be removed from this book list
     */
    internal fun removeBook(book: Book) {
        val removed = books.remove(book)
        if (removed) {
            bookByInitials.remove(book.initials)
            bookByName.remove(book.name)
        } else {
            throw Exception("Could not remove unregistered Book: ${book.name}")
        }
    }

    fun refresh() {
        val bookArray: List<Book> = driver.books
        bookArray.forEach { book ->
            addBook(book)
        }
    }

    /**
     * Register the driver, adding its books to the list. Any books that this
     * driver used, but not any more are removed. This can be called repeatedly
     * to re-register the driver.
     *
     * @param driver
     * The BookDriver to add
     * @throws BookException when an error occurs when performing this method
     */
    private fun registerDriver(driver: SwordBookDriver) {
        Log.d("begin registering driver: $driver")
        this.driver = driver

        refresh()

        Log.d("end registering driver: $driver")
    }

    private lateinit var driver: SwordBookDriver

    private val books: MutableSet<Book> = mutableSetOf()

    private val bookByInitials: MutableMap<String, Book> = mutableMapOf()

    private val bookByName: MutableMap<String, Book> = mutableMapOf()

    init {
        Log.d("Auto-registering SwordBookDriver start")
        registerDriver(SwordBookDriver())
        Log.d("Auto-registering completed")
    }
}
