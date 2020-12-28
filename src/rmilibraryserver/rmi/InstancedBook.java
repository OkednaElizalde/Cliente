/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmilibraryserver.rmi;

/**
 *
 * @author Willy
 */
public class InstancedBook {

    private final LibraryBook onLibrary;
    private final Book book;
    private final Author[] authors;

    public InstancedBook(final LibraryBook onLibrary, final Book book, final Author... authors) {
        this.onLibrary = onLibrary;
        this.book = book;
        this.authors = authors;
    }

    public LibraryBook getOnLibrary() {
        return onLibrary;
    }

    public Book getBook() {
        return book;
    }

    public Author[] getAuthors() {
        return authors;
    }

}
